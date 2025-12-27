package com.example.smartfianacetracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            return;
        }

        try {
            SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            if (messages != null) {
                for (SmsMessage message : messages) {
                    processSmsMessage(message.getMessageBody());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing SMS", e);
        }
    }

    private void processSmsMessage(String messageBody) {
        Log.d(TAG, "Processing SMS: " + messageBody);

        if (!isFinancialMessage(messageBody)) {
            Log.d(TAG, "Not a financial message, skipping");
            return;
        }

        Transaction transaction = extractTransactionDetails(messageBody);
        if (transaction != null) {
            saveToFirebase(transaction);
        } else {
            Log.w(TAG, "Could not extract transaction details");
        }
    }

    private boolean isFinancialMessage(String message) {
        String[] keywords = {
            "debited", "credited", "spent", "received", "payment",
            "transferred", "transaction", "upi", "neft", "imps",
            "withdrawn", "deposited", "balance", "rs", "inr", "₹"
        };

        String lowerMessage = message.toLowerCase();
        for (String keyword : keywords) {
            if (lowerMessage.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private Transaction extractTransactionDetails(String message) {
        try {
            Log.d(TAG, "Extracting details from: " + message);
            
            // Determine transaction type with better detection
            String lowerMessage = message.toLowerCase();
            boolean isDebit = false;
            boolean isCredit = false;
            
            // Check for debit keywords
            if (lowerMessage.contains("debited") || 
                lowerMessage.contains("debit") ||
                lowerMessage.contains("spent") || 
                lowerMessage.contains("paid") ||
                lowerMessage.contains("sent rs") ||
                lowerMessage.contains("sent rs.")) {
                isDebit = true;
            }
            
            // Check for credit keywords (these override debit if both present)
            if (lowerMessage.contains("credited") || 
                lowerMessage.contains("credit") ||
                lowerMessage.contains("received rs") ||
                lowerMessage.contains("received rs.") ||
                lowerMessage.contains("received a payment")) {
                isCredit = true;
                isDebit = false; // Credit takes precedence
            }
            
            String type = isCredit ? "CREDIT" : "DEBIT";
            Log.d(TAG, "Transaction type: " + type);

            // Extract amount - improved pattern to handle various formats
            Pattern amountPattern = Pattern.compile(
                "(?i)(?:rs\\.?|inr|₹)\\s*([\\d,]+(?:\\.\\d{1,2})?)|" +  // Rs.100 or Rs 100
                "(?i)(?:debited|credited|sent|received)\\s+(?:by\\s+)?([\\d,]+(?:\\.\\d{1,2})?)" // debited 100
            );
            Matcher amountMatcher = amountPattern.matcher(message);
            double amount = 0.0;
            if (amountMatcher.find()) {
                String amountStr = amountMatcher.group(1);
                if (amountStr == null) {
                    amountStr = amountMatcher.group(2);
                }
                if (amountStr != null) {
                    amountStr = amountStr.replace(",", "").trim();
                    amount = Double.parseDouble(amountStr);
                }
            }

            if (amount == 0.0) {
                Log.w(TAG, "Could not extract amount from message");
                return null;
            }
            Log.d(TAG, "Amount: " + amount);

            // Extract account number - improved pattern
            Pattern accPattern = Pattern.compile(
                "(?i)(?:a/c|ac|acct|account)\\s*(?:no|number|#)?\\s*[.:]*\\s*(X\\d+|\\d{4,})"
            );
            Matcher accMatcher = accPattern.matcher(message);
            String accountNumber = "";
            if (accMatcher.find()) {
                accountNumber = accMatcher.group(1);
                Log.d(TAG, "Account: " + accountNumber);
            }

            // Extract UPI ID - improved pattern to handle various formats
            Pattern upiPattern = Pattern.compile(
                "([a-zA-Z0-9][a-zA-Z0-9._-]*@[a-zA-Z0-9][a-zA-Z0-9.-]*)"
            );
            Matcher upiMatcher = upiPattern.matcher(message);
            String upiId = "";
            if (upiMatcher.find()) {
                upiId = upiMatcher.group(1);
                Log.d(TAG, "UPI ID: " + upiId);
            }

            // Determine transaction mode
            String transactionMode = "OTHER";
            if (message.toUpperCase().contains("UPI")) {
                transactionMode = "UPI";
            } else if (message.toUpperCase().contains("NEFT")) {
                transactionMode = "NEFT";
            } else if (message.toUpperCase().contains("IMPS")) {
                transactionMode = "IMPS";
            } else if (message.toUpperCase().contains("RTGS")) {
                transactionMode = "RTGS";
            }

            // Extract merchant name - improved patterns for different banks
            String merchantName = "Unknown";
            
            // Pattern 1: "to/from [name] on" (Kotak style)
            Pattern merchantPattern1 = Pattern.compile(
                "(?i)(?:to|from)\\s+([a-zA-Z0-9@._\\s-]+?)\\s+(?:on|thru|via|ref)"
            );
            Matcher merchantMatcher1 = merchantPattern1.matcher(message);
            if (merchantMatcher1.find()) {
                merchantName = merchantMatcher1.group(1).trim();
                // Clean up if it's a UPI ID
                if (!merchantName.contains("@")) {
                    merchantName = merchantName.replaceAll("\\s+", " ");
                }
            }
            
            // Pattern 2: "trf to [name]" (SBI style)
            if (merchantName.equals("Unknown")) {
                Pattern merchantPattern2 = Pattern.compile(
                    "(?i)trf\\s+to\\s+([a-zA-Z\\s]+?)\\s+(?:Refno|ref)"
                );
                Matcher merchantMatcher2 = merchantPattern2.matcher(message);
                if (merchantMatcher2.find()) {
                    merchantName = merchantMatcher2.group(1).trim();
                }
            }
            
            // Pattern 3: "for UPI to [name]" (IPPB style)
            if (merchantName.equals("Unknown")) {
                Pattern merchantPattern3 = Pattern.compile(
                    "(?i)for\\s+UPI\\s+to\\s+([a-zA-Z\\s]+?)\\s+on"
                );
                Matcher merchantMatcher3 = merchantPattern3.matcher(message);
                if (merchantMatcher3.find()) {
                    merchantName = merchantMatcher3.group(1).trim();
                }
            }
            
            // Fallback: Use UPI ID if available
            if (merchantName.equals("Unknown") && !upiId.isEmpty()) {
                merchantName = upiId;
            }
            
            Log.d(TAG, "Merchant: " + merchantName);

            Transaction transaction = new Transaction(type, amount, accountNumber, merchantName, transactionMode, upiId);
            Log.d(TAG, "Successfully extracted transaction: " + type + " Rs." + amount);
            return transaction;
        } catch (Exception e) {
            Log.e(TAG, "Error extracting transaction details", e);
            return null;
        }
    }

    private void saveToFirebase(Transaction transaction) {
        try {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                Log.e(TAG, "No authenticated user, cannot save transaction");
                return;
            }

            String userId = currentUser.getUid();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String collectionName = transaction.getType().toLowerCase();

            DatabaseReference transactionRef = database.getReference("users")
                .child(userId)
                .child(collectionName)
                .push();

            Map<String, Object> transactionData = new HashMap<>();
            transactionData.put("type", transaction.getType());
            transactionData.put("amount", transaction.getAmount());
            transactionData.put("accountNumber", transaction.getAccountNumber());
            transactionData.put("merchantName", transaction.getMerchantName());
            transactionData.put("transactionMode", transaction.getTransactionMode());
            transactionData.put("upiId", transaction.getUpiId());
            transactionData.put("timestamp", transaction.getTimestamp());
            transactionData.put("createdAt", transaction.getCreatedAt());

            transactionRef.setValue(transactionData)
                .addOnSuccessListener(aVoid ->
                    Log.d(TAG, "Transaction saved: " + transaction.getType() + " Rs." + transaction.getAmount()))
                .addOnFailureListener(e ->
                    Log.e(TAG, "Failed to save transaction", e));
        } catch (Exception e) {
            Log.e(TAG, "Error saving to Firebase", e);
        }
    }
}
