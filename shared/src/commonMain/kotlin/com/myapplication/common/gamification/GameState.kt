package com.myapplication.common.gamification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object GameState {
    var stylePoints by mutableStateOf(0)
        private set
    var fashionLevel by mutableStateOf(1)
        private set
    var styleStreak by mutableStateOf(0)
        private set
    var streakMultiplier by mutableStateOf(1)
        private set

    var lastActionMessage by mutableStateOf<String?>(null)
        private set

    val title: String
        get() = when (fashionLevel) {
            1 -> "Novice Dresser"
            2 -> "Style Apprentice"
            3 -> "Trendsetter"
            4 -> "Fashion Icon"
            5 -> "Runway Model"
            else -> "Style Guru"
        }

    val unlockedBadges: List<String>
        get() {
            val badges = mutableListOf<String>()
            if (fashionLevel >= 2) badges.add("First Steps 👞")
            if (fashionLevel >= 3) badges.add("Wardrobe Explorer 🧥")
            if (fashionLevel >= 4) badges.add("Trendsetter 🌟")
            if (styleStreak >= 3) badges.add("On Fire 🔥")
            if (styleStreak >= 5) badges.add("Unstoppable 🚀")
            if (stylePoints >= 500) badges.add("Point Hoarder 💰")
            return badges
        }

    fun addAction(actionName: String, basePoints: Int) {
        val earnedPoints = basePoints * streakMultiplier
        stylePoints += earnedPoints

        styleStreak += 1
        streakMultiplier = 1 + (styleStreak / 3)

        val previousLevel = fashionLevel
        fashionLevel = (stylePoints / 100) + 1

        val levelUpText = if (fashionLevel > previousLevel) "\n🎉 Level Up! You are now a $title!" else ""

        lastActionMessage = "+$earnedPoints SP ($actionName)! Streak x$streakMultiplier.$levelUpText"
    }

    fun resetStreak() {
        if (styleStreak > 0) {
            styleStreak = 0
            streakMultiplier = 1
            lastActionMessage = "Streak reset."
        }
    }

    fun clearMessage() {
        lastActionMessage = null
    }
}
