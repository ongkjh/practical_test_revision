package com.example.practical_test_revision

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationPage(navController: NavController) {

    var studentName by remember { mutableStateOf("") }
    // Kept as String so the field can start empty instead of showing "0"
    var icNumber by remember { mutableStateOf("") }
    var citizen by remember { mutableStateOf(false) }

    // Dropdown menu
    var dropdownExpanded by remember { mutableStateOf(false) }
    val programmeList = listOf("DFT", "DCS")
    var selectedProgramme by remember { mutableStateOf(programmeList.first()) }

    val icValue = icNumber.toIntOrNull()
    val formValid = studentName.isNotBlank() && icValue != null

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Student Registration") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Student name
            OutlinedTextField(
                value = studentName,
                onValueChange = { studentName = it },
                label = { Text("Student Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // IC number
            OutlinedTextField(
                value = icNumber,
                onValueChange = { input ->
                    // only allow digits
                    if (input.all { it.isDigit() }) icNumber = input
                },
                label = { Text("IC Number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Programme dropdown
            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedProgramme,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Programme") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                )

                // This is a member of ExposedDropdownMenuBoxScope, so it must
                // stay inside the box - that is why it needs no separate import.
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

            // Citizenship checkbox
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

            Button(
                onClick = {

                    val name = Uri.encode(studentName.trim())
                    val programme = Uri.encode(selectedProgramme)
                    navController.navigate(
                        "successPage/$name/${icValue ?: 0}/$programme/$citizen"
                    )
                },
                enabled = formValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }
        }
    }
}