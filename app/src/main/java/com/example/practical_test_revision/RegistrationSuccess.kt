package com.example.practical_test_revision

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationSuccess(
    navController: NavController,
    studentName: String,
    icNumber: Int,
    programme: String,
    citizen: Boolean
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Registration Successful") })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Registration successful!", style = MaterialTheme.typography.displayMedium)
            Text(
                "Student Name: $studentName \n Student IC Number: $icNumber \n Programme: $programme\n ${
                    if (citizen) {
                        "Citizen: Malaysian"
                    } else {
                        "Citizen: non-Malaysian"
                    }
                }"
            )
            Button(onClick = {navController.popBackStack()}){
                Text("Back")
            }
        }
    }
}