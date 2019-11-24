# Android Test Workshop
## Introduction
Welcome to the Test Automation Workshop in Android. In this workshop we will cover 4 parts of the test pyramid.
1. Unit tests with JUnit4 + MVP architecture
2. Integration tests in JVM with Robolectric
3. UI tests with Espresso
4. End to end tests with UIAutomator

## Setup
1. Download [Android Studio](https://developer.android.com/studio/index.html)
2. Use the command to clone the repository: `git clone https://github.com/phellipealexandre/AndroidTestWorkshop.git`
3. Switch the current branch to initialState: `git checkout initialState`
4. Create an emulator in AVD Manager in Android Studio or enable the developer configuration in a real device
5. Run the project on the emulator/device

## Description of the project
This project simulates a simple login feature using the [Json Placeholder API](https://jsonplaceholder.typicode.com/users). To check if everything is working fine, choose one of the users returned from the API
and fill the email and password fields with the corresponding email and a random password. You should see the user's name in the UserDetailsActivity.

## Disclaimer
The proposed solutions and code are not perfect, the idea of the workshop is to analyse the trade-offs between all testing solutions and refactor the code together. 

## Feedback
Any feedback is welcome, the idea is to improve this workshop. If you want to contribute or join me on its improvement, reach me out.
