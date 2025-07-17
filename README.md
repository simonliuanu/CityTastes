# CityTastes - Food Lovers' Social Media App

CityTastes is a food lovers' social media app that helps users discover new restaurants, share reviews, and connect with fellow food enthusiasts. The app provides convenient restaurant search capabilities, location-based discovery, and social features for the food community.

## Features

- **Restaurant Search**: Find restaurants by name with fuzzy search capabilities
- **GPS Integration**: Discover nearby restaurants using your current location
- **Reviews & Comments**: Read and share restaurant reviews with other users
- **Favorites Management**: Save and categorize your favorite restaurants
- **Social Chat**: Connect and discuss dining experiences with friends
- **Interactive Map**: View restaurant locations on an interactive map
- **Filtering & Sorting**: Filter results by price, rating, or restaurant type

## Screenshots

The app provides an intuitive interface for discovering and exploring restaurants in your area.

## Technical Details

- **Platform**: Android
- **API Level**: 34
- **Backend**: Firebase integration
- **Maps**: Google Maps Android API
- **Architecture**: MVC pattern with clean separation of concerns

## Design Patterns Used

- **Factory Pattern**: For creating comment objects
- **Singleton Pattern**: For user management and restaurant likes
- **Observer Pattern**: For real-time updates on restaurant interactions

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Run the app on an emulator or physical device

## Project Structure

```
SmartCity/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/smartcity/
│   │   │   │   ├── frontend/        # UI components
│   │   │   │   └── backend/         # Business logic
│   │   │   └── res/                 # Resources
│   │   └── assets/                  # App assets
│   └── build.gradle.kts
```

## Target Users

- Food enthusiasts
- Travelers looking for local dining options
- Anyone seeking to discover new restaurants
- Social users who enjoy sharing dining experiences

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
