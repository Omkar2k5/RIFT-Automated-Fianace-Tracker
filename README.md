# Fin AI - Finance Tracking Using AI 🤖💰

[![Live Demo](https://img.shields.io/badge/Live-Demo-brightgreen)](https://fin-ai-red.vercel.app/)
[![Android APK](https://img.shields.io/badge/Android-APK-green)](https://github.com/Omkar2k5/RIFT-Automated-Fianace-Tracker/raw/main/Android%20APP.apk)
[![Next.js](https://img.shields.io/badge/Next.js-15.3-black)](https://nextjs.org/)
[![React](https://img.shields.io/badge/React-18.2-blue)](https://reactjs.org/)
[![Firebase](https://img.shields.io/badge/Firebase-11.5-orange)](https://firebase.google.com/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.0-blue)](https://www.typescriptlang.org/)

> **Enterprise-Grade Financial Management Platform** - A modern, AI-powered financial tracking and budgeting solution built with cutting-edge technologies.

🌐 **Live Web Application**: [https://fin-ai-red.vercel.app/](https://fin-ai-red.vercel.app/)

📱 **Android App Download**: [Android APP.apk](./Android%20APP.apk) (617 KB) - Available in the root folder

---

## 📋 Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

---

## 🎯 Overview

**Fin AI** (formerly FinanceBuddy) is a comprehensive financial management platform that combines the power of artificial intelligence with intuitive design to help users take complete control of their finances. The platform offers both a **web application** and an **Android mobile app**, providing seamless financial tracking across all devices.

Built with modern architecture, scalable design patterns, and industry best practices, Fin AI delivers a production-ready solution for personal and business financial management.

### What Makes This Project Exceptional

- ⚡ **Performance Optimized** - Lightning-fast loading times with optimized bundle sizes
- 🧑‍💻 **Professional Development** - Built with Next.js 15, TypeScript, and modern best practices
- 🔒 **Production Ready** - Comprehensive error handling, secure authentication, and deployment-ready configuration
- 🎨 **User-Centric Design** - Intuitive interface with accessibility compliance and mobile-first approach
- 🤖 **AI-Powered** - Smart financial insights and automated categorization

---

## ✨ Key Features

### 💳 Smart Expense Tracking
- Automatic transaction categorization
- Real-time expense monitoring
- Merchant-based expense breakdown
- Visual spending analytics with interactive charts

### 💰 Intelligent Budgeting
- Create custom budgets with intelligent alerts
- Dynamic budget tracking and insights
- Month-over-month comparison
- Budget vs. actual spending visualization

### 📊 Comprehensive Financial Reports
- Detailed financial analytics and visualizations
- Monthly overview with 6-month trend analysis
- Expense breakdown by merchant
- Exportable PDF reports with charts and insights
- Income vs. expense tracking

### 🎯 AI-Powered Insights
- Smart recommendations for financial optimization
- Predictive analytics for better decision-making
- Automated financial health scoring
- Personalized savings recommendations

### 📱 Multi-Platform Support
- **Web Application** - Responsive Next.js web app
- **Android App** - Native Android application
- Seamless data synchronization across devices
- Real-time updates with Firebase

### 🔐 Secure Authentication
- Firebase Authentication integration
- Google Sign-In support
- Secure user data management
- Protected routes and API endpoints

### 🌙 Modern UI/UX
- Dark mode support with theme toggle
- Neumorphism design elements
- Glassmorphism effects
- Smooth animations and transitions
- Mobile-responsive design

---

## 🛠️ Technology Stack

### Web Application

#### Frontend
- **Framework**: Next.js 15.3 (App Router)
- **Language**: TypeScript 5.0
- **UI Library**: React 18.2
- **Styling**: TailwindCSS 3.4
- **Component Library**: Radix UI + Shadcn/ui
- **Charts**: Recharts 2.12
- **Animations**: Framer Motion 12.5
- **Icons**: Lucide React, React Icons

#### Backend & Services
- **Authentication**: Firebase Auth 11.5
- **Database**: Firebase Realtime Database
- **Hosting**: Vercel
- **API Integration**: Binance API (for crypto portfolio)

#### Development Tools
- **Build Tool**: Next.js with Turbopack
- **Linting**: ESLint
- **Package Manager**: npm
- **Version Control**: Git

### Android Application

#### Core Technologies
- **Language**: Java
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Build System**: Gradle

#### Libraries & Dependencies
- **UI**: Material Design Components 1.11.0
- **Navigation**: AndroidX Navigation 2.7.7
- **Authentication**: Firebase Auth
- **Database**: Firebase Realtime Database
- **Google Services**: Play Services Auth 20.7.0
- **Multidex**: AndroidX Multidex 2.0.1

---

## 📁 Project Structure

```
RIFT/
├── Web Application/          # Next.js Web Application
│   ├── app/                 # Next.js App Router pages
│   │   ├── home/           # Landing page
│   │   ├── dashboard/      # Main dashboard
│   │   ├── expenses/       # Expense tracking
│   │   ├── income/         # Income management
│   │   ├── budgeting/      # Budget management
│   │   ├── reports/        # Financial reports
│   │   ├── portfolio/      # Investment portfolio
│   │   ├── transactions/   # Transaction management
│   │   ├── login/          # Authentication
│   │   └── signup/         # User registration
│   ├── components/         # Reusable React components
│   │   ├── ui/            # Shadcn/ui components
│   │   ├── dashboard-*.tsx # Dashboard components
│   │   ├── expense-*.tsx   # Expense components
│   │   ├── income-*.tsx    # Income components
│   │   └── financial-*.tsx # Financial analysis components
│   ├── lib/               # Utility functions and configurations
│   │   ├── firebase.ts    # Firebase configuration
│   │   └── firebase-auth.ts # Authentication utilities
│   ├── hooks/             # Custom React hooks
│   ├── types/             # TypeScript type definitions
│   ├── styles/            # Global styles
│   ├── public/            # Static assets
│   ├── package.json       # Dependencies and scripts
│   └── next.config.js     # Next.js configuration
│
├── Android App/             # Android Mobile Application
│   ├── app/                # Application module
│   │   ├── src/           # Source code
│   │   │   ├── main/      # Main application code
│   │   │   ├── test/      # Unit tests
│   │   │   └── androidTest/ # Instrumented tests
│   │   └── build.gradle   # App-level Gradle configuration
│   ├── gradle/            # Gradle wrapper
│   ├── build.gradle       # Project-level Gradle configuration
│   └── google-services.json # Firebase configuration
│
└── README.md              # This file
```

---

## 🚀 Getting Started

### Prerequisites

#### For Web Application
- **Node.js**: >= 18.0.0 < 23.0.0
- **npm**: Latest version
- **Firebase Account**: For authentication and database

#### For Android Application
- **Android Studio**: Latest version
- **JDK**: Version 17
- **Android SDK**: API Level 35
- **Firebase Account**: For authentication and database

---

## 💻 Installation

### Web Application Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Omkar2k5/RIFT-Automated-Fianace-Tracker.git
   cd RIFT/Web\ Application
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure Firebase**
   - Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Enable Authentication (Email/Password and Google Sign-In)
   - Enable Realtime Database
   - Copy your Firebase configuration
   - Create a `.env` file in the root directory:
     ```env
     NEXT_PUBLIC_FIREBASE_API_KEY=your_api_key
     NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN=your_auth_domain
     NEXT_PUBLIC_FIREBASE_PROJECT_ID=your_project_id
     NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET=your_storage_bucket
     NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID=your_sender_id
     NEXT_PUBLIC_FIREBASE_APP_ID=your_app_id
     ```

4. **Run the development server**
   ```bash
   npm run dev
   ```

5. **Open your browser**
   Navigate to [http://localhost:3000](http://localhost:3000)

### Android Application Setup

1. **Open Android Studio**
   - Open the `Android App` folder in Android Studio

2. **Configure Firebase**
   - Download `google-services.json` from your Firebase project
   - Place it in the `app/` directory

3. **Sync Gradle**
   - Let Android Studio sync the Gradle files

4. **Run the application**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio

---

## 📖 Usage

### Web Application

1. **Sign Up / Sign In**
   - Create a new account or sign in with Google
   - Access the dashboard after authentication

2. **Dashboard**
   - View your financial overview
   - Monitor total balance, income, and expenses
   - Analyze spending trends with interactive charts

3. **Track Expenses**
   - Add new expenses with merchant details
   - Categorize transactions automatically
   - View expense breakdown by merchant

4. **Manage Income**
   - Record income sources
   - Track income trends over time
   - Compare income vs. expenses

5. **Create Budgets**
   - Set custom budgets for different categories
   - Monitor budget utilization
   - Receive alerts when approaching limits

6. **Generate Reports**
   - Create comprehensive financial reports
   - Export reports as PDF
   - Analyze financial health scores

7. **Investment Portfolio**
   - Track cryptocurrency investments
   - Monitor portfolio performance
   - View real-time market data

### Android Application

1. **Download the App**
   - **Option 1**: Download the APK from the root folder: [`Android APP.apk`](./Android%20APP.apk) (617 KB)
   - **Option 2**: Download from [TeraBox](https://1024terabox.com/s/1GZTaEhB146Yg61iTKQEZag)
   - Install the APK on your Android device (Enable "Install from Unknown Sources" if needed)

2. **Features**
   - All web features available on mobile
   - SMS-based automatic transaction parsing
   - Real-time synchronization with web app
   - Offline support with local caching

---

## 📸 Screenshots

> Add screenshots of your application here to showcase the UI and features

---

## 🏗️ Build & Deployment

### Web Application

#### Development Build
```bash
npm run dev          # Start development server
npm run dev:turbo    # Start with Turbopack (faster)
```

#### Production Build
```bash
npm run build        # Create production build
npm run start        # Start production server
```

#### Deploy to Vercel
```bash
# The app is configured for automatic deployment to Vercel
# Simply push to your main branch and Vercel will deploy automatically
```

### Android Application

#### Debug Build
```bash
./gradlew assembleDebug
```

#### Release Build
```bash
./gradlew assembleRelease
```

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is developed for educational and demonstration purposes.

---

## 👨‍💻 Author

**Omkar Gondkar**

- GitHub: [@Omkar2k5](https://github.com/Omkar2k5)
- Project: [RIFT - Automated Finance Tracker](https://github.com/Omkar2k5/RIFT-Automated-Fianace-Tracker)

---

## 🙏 Acknowledgments

- Built with [Next.js](https://nextjs.org/)
- UI components from [Shadcn/ui](https://ui.shadcn.com/)
- Icons from [Lucide](https://lucide.dev/)
- Hosted on [Vercel](https://vercel.com/)
- Backend powered by [Firebase](https://firebase.google.com/)

---

## 📞 Support

For support, please open an issue in the GitHub repository or contact the author.

---

<div align="center">

**⭐ Star this repository if you find it helpful!**

Made with ❤️ by Omkar Gondkar

</div>
