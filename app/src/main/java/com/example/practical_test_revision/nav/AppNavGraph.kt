package com.example.practical_test_revision.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost // <-remember to use this
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.practical_test_revision.RegistrationPage
import com.example.practical_test_revision.RegistrationSuccess

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController();

    NavHost(
        navController = navController,
        startDestination = "registrationPage",
        modifier = modifier
    ){
        composable("registrationPage"){
            RegistrationPage(navController = navController);
        }
        composable(
            "successPage/{studentName,icNumber,programme,citizen}",
            arguments = listOf(
                navArgument("studentName"){type = NavType.StringType},
                navArgument("icNumber"){type = NavType.IntType},
                navArgument("programme"){type = NavType.StringType},
                navArgument("citizen"){type = NavType.BoolType})
        ){
            RegistrationSuccess(navController = navController, studentName =,icNumber,programme,citizen )
        }
    }
}