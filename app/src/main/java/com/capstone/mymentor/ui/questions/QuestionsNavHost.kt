package com.capstone.mymentor.ui.questions

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.capstone.mymentor.ui.questions.Destinations.QUESTIONS_ROUTE

object Destinations {
    const val QUESTIONS_ROUTE = "questions"
}

@Composable
fun QuestionsNavHost(
    navController: NavHostController = rememberNavController(),
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = QUESTIONS_ROUTE,
        ) {
        composable(QUESTIONS_ROUTE) {
            QuestionsRoute(
                onNavUp = navController::navigateUp,
                context = context
            )
        }
    }
}