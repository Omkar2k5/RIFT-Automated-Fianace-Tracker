import { NextRequest, NextResponse } from 'next/server'
import crypto from 'crypto'
import { getDatabase, ref, get } from 'firebase/database'
import { initializeApp, getApps } from 'firebase/app'

// Initialize Firebase (if not already initialized)
const firebaseConfig = {
  apiKey: process.env.NEXT_PUBLIC_FIREBASE_API_KEY,
  authDomain: process.env.NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.NEXT_PUBLIC_FIREBASE_PROJECT_ID,
  storageBucket: process.env.NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.NEXT_PUBLIC_FIREBASE_APP_ID,
  databaseURL: process.env.NEXT_PUBLIC_FIREBASE_DATABASE_URL,
}

if (!getApps().length) {
  initializeApp(firebaseConfig)
}

export async function GET(request: NextRequest) {
  try {
    // Get the user ID from the Authorization header
    const authorization = request.headers.get('authorization')

    if (!authorization) {
      return NextResponse.json(
        { error: 'Not authenticated' },
        { status: 401 }
      )
    }

    // Get user's CoinDCX credentials from Firebase
    const db = getDatabase()
    const credentialsSnapshot = await get(ref(db, `users/${authorization}/coindcx_credentials`))

    if (!credentialsSnapshot.exists()) {
      return NextResponse.json(
        { error: 'CoinDCX credentials not found' },
        { status: 404 }
      )
    }

    const credentials = credentialsSnapshot.val()
    const API_KEY = credentials.apiKey
    const API_SECRET = credentials.apiSecret

    if (!API_KEY || !API_SECRET) {
      console.error('Missing API credentials:', { API_KEY: !!API_KEY, API_SECRET: !!API_SECRET })
      return NextResponse.json(
        { error: 'API credentials not configured' },
        { status: 401 }
      )
    }

    // CoinDCX API - Get user balances
    const timestamp = Date.now()
    const body = JSON.stringify({ timestamp })

    // Create signature for CoinDCX
    const signature = crypto
      .createHmac('sha256', API_SECRET)
      .update(body)
      .digest('hex')

    // Fetch balance from CoinDCX
    const balanceResponse = await fetch('https://api.coindcx.com/exchange/v1/users/balances', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-APIKEY': API_KEY,
        'X-AUTH-SIGNATURE': signature,
      },
      body: body
    })

    if (!balanceResponse.ok) {
      const error = await balanceResponse.json()
      console.error('CoinDCX Balance API Error:', error)
      return NextResponse.json(
        { error: error.message || 'Failed to fetch balance data' },
        { status: balanceResponse.status }
      )
    }

    const balanceData = await balanceResponse.json()

    // Fetch market ticker data from CoinDCX (public API, no auth needed)
    const marketResponse = await fetch('https://api.coindcx.com/exchange/ticker', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    })

    if (!marketResponse.ok) {
      const error = await marketResponse.json()
      console.error('CoinDCX Market API Error:', error)
      return NextResponse.json(
        { error: error.message || 'Failed to fetch market data' },
        { status: marketResponse.status }
      )
    }

    const marketData = await marketResponse.json()

    // Filter non-zero balances and process the data
    const nonZeroBalances = balanceData.filter(
      (balance: any) => parseFloat(balance.balance) > 0
    )

    // Process and combine the data
    const processedData = {
      balanceData: nonZeroBalances.map((balance: any) => ({
        currency: balance.currency,
        balance: balance.balance,
        available_balance: balance.balance,
        locked_balance: balance.locked_balance || '0'
      })),
      marketData: marketData.map((ticker: any) => ({
        symbol: ticker.market,
        last_price: parseFloat(ticker.last_price),
        change_24_hour: parseFloat(ticker.change_24_hour) || 0
      }))
    }

    return NextResponse.json(processedData)
  } catch (error) {
    console.error('Portfolio API Error:', error)
    return NextResponse.json(
      { error: error instanceof Error ? error.message : 'Failed to fetch portfolio data' },
      { status: 500 }
    )
  }
}