package com.example.practical_test_revision.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.practical_test_revision.RegistrationPage
import com.example.practical_test_revision.RegistrationSuccess

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "registrationPage",
        modifier = modifier
    ) {
        composable("registrationPage") {
            RegistrationPage(navController = navController)
        }

        composable(
            // Each argument needs its own {placeholder} separated by "/"
            route = "successPage/{studentName}/{icNumber}/{programme}/{citizen}",
            arguments = listOf(
                navArgument("studentName") { type = NavType.StringType },
                navArgument("icNumber") { type = NavType.IntType },
                navArgument("programme") { type = NavType.StringType },
                navArgument("citizen") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            // Read the values back out of the back stack entry
            val args = backStackEntry.arguments

            RegistrationSuccess(
                navController = navController,
                studentName = args?.getString("studentName") ?: "",
                icNumber = args?.getInt("icNumber") ?: 0,
                programme = args?.getString("programme") ?: "",
                citizen = args?.getBoolean("citizen") ?: false
            )
        }
    }
}