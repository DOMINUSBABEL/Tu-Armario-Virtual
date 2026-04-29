package com.myapplication.common.navigation

sealed class AppScreen {
    object Login : AppScreen()
    object Tutorial : AppScreen()
    object Main : AppScreen()
    object Leaderboard : AppScreen()
    object Wardrobe : AppScreen()
    object Shop : AppScreen()
    object Runway : AppScreen()
    data class RunwayShowcase(val outfitDescription: String, val theme: String) : AppScreen()
}
