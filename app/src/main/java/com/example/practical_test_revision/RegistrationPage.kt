package com.example.practical_test_revision

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationPage(navController: NavController) {
//    var studentName: String = "";
//    var icNumber: Int = 0;
//    var registerCourse: String = "";
//    var citizen: Boolean = false;

    var studentName by remember { mutableStateOf("") };
    var icNumber by remember { mutableIntStateOf(0) };
    var registerCourse by remember { mutableStateOf("") };
    var citizen by remember { mutableStateOf(false) };
    //dropdownmenu
    var dropdownExpanded by remember { mutableStateOf(false) }
    val programmeList = listOf("DFT","DCS");
    var selectedProgramme by remember { mutableStateOf(programmeList.first())}


    Scaffold(modifier = Modifier
        .fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Student Registration") })
        }) {innerPadding->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Text input
            OutlinedTextField(
                value = studentName,
                onValueChange = { studentName = it },
                label = { Text("Item name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            // Text input
            OutlinedTextField(
                value = icNumber.toString(),
                onValueChange = {icNumber = it.toIntOrNull() ?: 0},
                label = { Text("Item name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            //Register course
           ExposedDropdownMenuBox(
               expanded = dropdownExpanded,
               onExpandedChange = { dropdownExpanded = it }
           ) { OutlinedTextField(
               value = selectedProgramme,
               onValueChange = {},
               readOnly = true,
               label = { Text("Category") },
               trailingIcon = {
                   ExposedDropdownMenuDefaults.TrailingIcon(
                       expanded = dropdownExpanded
                   )
               },
               modifier = Modifier
                   .fillMaxWidth()
                   .menuAnchor()
           )
               ExposedDropdownMenu(
                   expanded = dropdownExpanded,
                   onDismissRequest = { dropdownExpanded = false }
               ) {
                   programmeList.forEach { prog ->
                       DropdownMenuItem(
                           text = { Text(prog) },
                           onClick = {
                               selectedProgramme = prog
                               dropdownExpanded = false
                           }
                       )
                   }
               }
           }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = citizen,
                        onClick = { citizen = !citizen },
                        role = Role.Checkbox
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = citizen, onCheckedChange = null)
                Spacer(Modifier.width(8.dp))
                Text("Malaysian")
            }

            Button(onClick = {}) { }

        }
    }
}