package com.myapplication.common.navigation

sealed class AppScreen {
    object Login : AppScreen()
    object Tutorial : AppScreen()
    object Main : AppScreen()
}
