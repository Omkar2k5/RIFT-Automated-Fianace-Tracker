import {
  getAuth,
  signInWithEmailAndPassword,
  createUserWithEmailAndPassword,
  updateProfile,
  signInWithPopup,
  GoogleAuthProvider,
  PhoneAuthProvider,
  signInWithPhoneNumber,
  RecaptchaVerifier,
  ConfirmationResult,
  setPersistence,
  browserLocalPersistence,
  signOut as firebaseSignOut,
  onAuthStateChanged as firebaseAuthStateChanged,
  type Auth
} from 'firebase/auth'
import { app } from './firebase'

// Lazy initialize auth only on client side
let auth: Auth | null = null
let googleProvider: GoogleAuthProvider | null = null

const getAuthInstance = () => {
  if (typeof window === 'undefined') {
    throw new Error('Firebase Auth can only be used on the client side')
  }
  if (!auth) {
    auth = getAuth(app)
    googleProvider = new GoogleAuthProvider()
    // Set persistence to LOCAL (survives browser restart)
    setPersistence(auth, browserLocalPersistence)
      .catch((error) => {
        console.error("Error setting persistence:", error)
      })
  }
  return auth
}

const getGoogleProvider = () => {
  if (!googleProvider) {
    getAuthInstance() // This will initialize both
  }
  return googleProvider!
}

// Email/Password Sign Up
export const signUpWithEmail = async (email: string, password: string, displayName: string) => {
  try {
    const result = await createUserWithEmailAndPassword(getAuthInstance(), email, password)

    // Update the user's display name
    if (result.user) {
      await updateProfile(result.user, {
        displayName: displayName
      })
    }

    return { user: result.user, error: null }
  } catch (error: any) {
    return {
      user: null,
      error: error.message || 'Failed to create account'
    }
  }
}

// Email/Password Sign In
export const signIn = async (email: string, password: string) => {
  try {
    const userCredential = await signInWithEmailAndPassword(getAuthInstance(), email, password)
    return userCredential.user
  } catch (error: any) {
    throw new Error(error.message)
  }
}

// Google Sign In
export const signInWithGoogle = async () => {
  try {
    const result = await signInWithPopup(getAuthInstance(), getGoogleProvider())
    return { user: result.user, error: null }
  } catch (error: any) {
    return {
      user: null,
      error: error.message || 'Failed to sign in with Google'
    }
  }
}

let confirmationResultInstance: ConfirmationResult | null = null

// Phone Number Sign In
export const initializePhoneAuth = () => {
  if (!(window as any).recaptchaVerifier) {
    (window as any).recaptchaVerifier = new RecaptchaVerifier(getAuthInstance(), 'recaptcha-container', {
      size: 'invisible',
      callback: () => { },
      'expired-callback': () => { }
    })
  }
  return (window as any).recaptchaVerifier
}

export const signInWithPhone = async (phoneNumber: string) => {
  try {
    const recaptchaVerifier = initializePhoneAuth()
    const result = await signInWithPhoneNumber(getAuthInstance(), phoneNumber, recaptchaVerifier)
    confirmationResultInstance = result
    return { confirmationResult: result, error: null }
  } catch (error: any) {
    return {
      confirmationResult: null,
      error: error.message || 'Failed to send verification code'
    }
  }
}

export const verifyPhoneCode = async (code: string) => {
  try {
    if (!confirmationResultInstance) {
      throw new Error('No confirmation result found')
    }
    const result = await confirmationResultInstance.confirm(code)
    return { user: result.user, error: null }
  } catch (error: any) {
    return {
      user: null,
      error: error.message || 'Failed to verify code'
    }
  }
}

// Sign Out
export const logOut = async () => {
  try {
    await firebaseSignOut(getAuthInstance())
  } catch (error: any) {
    throw new Error(error.message)
  }
}

// Get Current User
export const getCurrentUser = () => {
  return getAuthInstance().currentUser
}

// Auth State Observer
export const onAuthStateChanged = (callback: Parameters<typeof firebaseAuthStateChanged>[1]) =>
  firebaseAuthStateChanged(getAuthInstance(), callback) 