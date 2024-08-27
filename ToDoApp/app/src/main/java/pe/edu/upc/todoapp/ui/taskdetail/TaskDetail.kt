package pe.edu.upc.todoapp.ui.taskdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TaskDetail(
    task: String? = null,
    onSaveTask: (String) -> Unit = {} ) {

    val description = remember {
        mutableStateOf( task?: "")
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onSaveTask(description.value)
            }) {
                Icon(Icons.Filled.Done, contentDescription = "Done")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = description.value,
                placeholder = {
                    Text(text = "New task")
                },
                onValueChange = { newValue ->
                    description.value = newValue
                })
        }
    }
}

@Preview
@Composable
fun TaskDetailPreview() {
    TaskDetail()
}