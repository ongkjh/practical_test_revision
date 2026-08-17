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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationPage(navController: NavController) {

    // ---- Form state ----
    var studentName by remember { mutableStateOf("") }
    var icNumber by remember { mutableStateOf("") }
    var citizen by remember { mutableStateOf(false) }

    // ---- Error state (null = no error) ----
    var nameError by remember { mutableStateOf<String?>(null) }
    var icError by remember { mutableStateOf<String?>(null) }

    // ---- Dropdown ----
    var dropdownExpanded by remember { mutableStateOf(false) }
    val programmeList = listOf("DFT", "DCS")
    var selectedProgramme by remember { mutableStateOf(programmeList.first()) }

    // Runs all the checks, fills in the error messages, returns true if the form is clean
    fun validate(): Boolean {
        nameError = when {
            studentName.isBlank() -> "Student name is required"
            studentName.trim().length < 3 -> "Name must be at least 3 characters"
            !studentName.all { it.isLetter() || it.isWhitespace() } ->
                "Name can only contain letters and spaces"
            else -> null
        }

        icError = when {
            icNumber.isBlank() -> "IC number is required"
            icNumber.length != 12 -> "IC number must be exactly 12 digits"
            else -> null
        }

        return nameError == null && icError == null
    }

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
            // ---- Student name ----
            OutlinedTextField(
                value = studentName,
                onValueChange = {
                    studentName = it
                    nameError = null   // clear the error as soon as they start fixing it
                },
                label = { Text("Student Name") },
                singleLine = true,
                isError = nameError != null,
                supportingText = {
                    nameError?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // ---- IC number ----
            OutlinedTextField(
                value = icNumber,
                onValueChange = { input ->
                    // block non-digits and anything past 12 characters at the source
                    if (input.all { it.isDigit() } && input.length <= 12) {
                        icNumber = input
                        icError = null
                    }
                },
                label = { Text("IC Number") },
                placeholder = { Text("e.g. 050312101234") },
                singleLine = true,
                isError = icError != null,
                supportingText = {
                    Text(icError ?: "${icNumber.length}/12 digits")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )


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

            // ---- Submit ----
            Button(
                onClick = {
                    if (validate()) {
                        val name = Uri.encode(studentName.trim())
                        val programme = Uri.encode(selectedProgramme)
                        navController.navigate(
                            "successPage/$name/$icNumber/$programme/$citizen"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }
        }
    }
}