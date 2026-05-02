package com.myapplication.common.navigation

sealed class AppScreen {
    object Splash : AppScreen()
    object Login : AppScreen()
    object Tutorial : AppScreen()
    object Main : AppScreen()
    object Leaderboard : AppScreen()
    data object Wardrobe : AppScreen()
    data object Shop : AppScreen()
    data object Runway : AppScreen()
    data class RunwayShowcase(val outfitDescription: String, val theme: String) : AppScreen()
    data object SocialFeed : AppScreen()
}
