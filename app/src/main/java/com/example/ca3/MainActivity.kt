package com.example.ca3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ca3.ui.theme.Ca3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ca3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        EventScreen()
                    }
                }
            }
        }
    }
}

class EventViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val name = savedStateHandle.getStateFlow("name", "")
    val email = savedStateHandle.getStateFlow("email", "")

    fun updateName(value: String) {
        savedStateHandle["name"] = value
    }

    fun updateEmail(value: String) {
        savedStateHandle["email"] = value
    }

    fun submit(): String {
        val result = "Registration successful: ${name.value}"
        savedStateHandle["name"] = ""
        savedStateHandle["email"] = ""
        return result
    }
}

@Composable
fun EventScreen(viewModel: EventViewModel = viewModel()) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.updateName(it) },
            label = { Text("Name") }
        )

        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") }
        )

        Button(onClick = {
            Toast.makeText(
                context,
                viewModel.submit(),
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text("Submit")
        }
    }
}
