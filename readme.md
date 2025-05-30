Gym Tracking System
This is a simple, responsive web application built with React and Firebase that allows users to track their gym workouts. Users can sign up, log in, add new workout entries, and view their workout history.

Features
User Authentication: Secure sign-up and sign-in functionality using Firebase Authentication (email/password).

Workout Logging: Easily add new workout entries including:

Exercise Name

Sets

Reps

Weight (in kg/lbs)

Workout History: View a chronological list of all your past workout entries.

Data Persistence: All workout data is stored securely in Google Cloud Firestore, linked to the authenticated user.

Responsive Design: Built with Tailwind CSS to ensure a great user experience across various devices (desktop, tablet, mobile).

Technologies Used
React: A JavaScript library for building user interfaces.

Firebase: A platform developed by Google for creating mobile and web applications.

Firebase Authentication: For user registration and login.

Cloud Firestore: A NoSQL cloud database for storing workout data.

Tailwind CSS: A utility-first CSS framework for rapid UI development.

Setup and Running Locally
This application is designed to run within a specific environment (like the Canvas environment where it was generated) that provides necessary Firebase configuration and authentication tokens. However, you can adapt it to run locally by setting up your own Firebase project.

1. Create a Firebase Project
Go to the Firebase Console.

Click "Add project" and follow the steps to create a new project.

Once the project is created, click the "Web" icon (</>) to add a web app to your project.

Register your app and copy the Firebase configuration object. It will look something like this:

const firebaseConfig = {
  apiKey: "YOUR_API_KEY",
  authDomain: "YOUR_AUTH_DOMAIN",
  projectId: "YOUR_PROJECT_ID",
  storageBucket: "YOUR_STORAGE_BUCKET",
  messagingSenderId: "YOUR_MESSAGING_SENDER_ID",
  appId: "YOUR_APP_ID"
};

2. Enable Firebase Services
In your Firebase project console:

Authentication:

Navigate to "Authentication" > "Get started".

Go to the "Sign-in method" tab and enable "Email/Password".

Firestore Database:

Navigate to "Firestore Database" > "Create database".

Choose "Start in production mode" (you will set up security rules shortly). Select a location for your database.

3. Set up Firestore Security Rules
To allow authenticated users to read and write their own data, set up the following security rules in your Firestore Database (under the "Rules" tab):

rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Public data (if you were to have any shared data)
    match /artifacts/{appId}/public/data/{collection}/{document} {
      allow read, write: if request.auth != null;
    }

    // Private user data
    match /artifacts/{appId}/users/{userId}/{collection}/{document} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
  }
}

After pasting the rules, click "Publish".

4. Integrate Firebase Config into the React App
The provided React code uses global variables __firebase_config, __app_id, and __initial_auth_token. To run this locally, you'll need to simulate these or directly embed your Firebase config.

Option A: Direct Embedding (for local development)

Replace the firebaseConfig parsing in FirebaseProvider with your actual Firebase config object:

// In FirebaseProvider component:
useEffect(() => {
  try {
    // Initialize Firebase app
    // const firebaseConfig = JSON.parse(typeof __firebase_config !== 'undefined' ? __firebase_config : '{}'); // Original line
    const firebaseConfig = { // Replace with your actual config
      apiKey: "YOUR_API_KEY",
      authDomain: "YOUR_AUTH_DOMAIN",
      projectId: "YOUR_PROJECT_ID",
      storageBucket: "YOUR_STORAGE_BUCKET",
      messagingSenderId: "YOUR_MESSAGING_SENDER_ID",
      appId: "YOUR_APP_ID" // This should match your Firebase project's web app ID
    };
    const firebaseApp = initializeApp(firebaseConfig);
    setApp(firebaseApp);
    setDb(getFirestore(firebaseApp));
    setAuth(getAuth(firebaseApp));
  } catch (error) {
    console.error("Failed to initialize Firebase:", error);
  }
}, []);

Also, for local testing without the __initial_auth_token (which is for custom token authentication in specific environments), you can simplify the signInWithToken logic:

// In FirebaseProvider component, within the onAuthStateChanged effect:
// Remove or comment out the signInWithToken function call and its definition
// if (typeof __initial_auth_token !== 'undefined') { ... } else { await signInAnonymously(auth); }
// The onAuthStateChanged listener will handle anonymous sign-in if no user is found.

And ensure that appId in WorkoutTracker uses your actual Firebase project ID:

// In WorkoutTracker component:
const appId = "YOUR_FIREBASE_PROJECT_ID"; // Replace with your Firebase project ID

Option B: Using Environment Variables (Recommended for larger projects)

For a more robust local setup, you would typically use environment variables (e.g., .env file with REACT_APP_FIREBASE_API_KEY, etc.) and load them into your React app.

5. Install Dependencies and Run
Save the React code as App.js (or similar) in a React project.

Ensure you have Node.js and npm/yarn installed.

Create a new React project (if you don't have one):

npx create-react-app gym-tracker
cd gym-tracker

Install Firebase:

npm install firebase
# or
yarn add firebase

Replace the content of src/App.js with the provided React code.

Start the development server:

npm start
# or
yarn start

This will open the application in your browser, usually at http://localhost:3000.

Usage
Sign Up/Sign In:

When you first open the app, you'll see the authentication screen.

If you're a new user, click "Sign Up" and enter your email and a strong password.

If you already have an account, ensure "Sign In" is selected and enter your credentials.

Add Workout:

Once signed in, you'll see the "Add New Workout" form.

Fill in the exercise name (e.g., "Squats"), sets (e.g., "4"), reps (e.g., "10"), and weight (e.g., "80.5").

Click the "Add Workout" button.

View History:

Your newly added workout will appear in the "Workout History" section on the right.

All your logged workouts will be displayed here, ordered by the most recent.

Sign Out:

Click the "Sign Out" button in the header to log out of your account.

Future Enhancements
Edit/Delete Workouts: Add functionality to modify or remove existing workout entries.

Progress Tracking: Implement charts or graphs to visualize progress over time for specific exercises.

Workout Templates: Allow users to create and save custom workout routines.

User Profiles: Add more user profile details.

Public/Shared Workouts: Implement functionality to share workouts with other users (requires adjusting Firestore rules for public data).

Input Validation: More robust client-side validation for form inputs.
