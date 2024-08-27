package pe.edu.upc.todoapp.ui.tasklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun TaskList(
    tasks: List<String> = emptyList(),
    onEditTask: (String) -> Unit = {},
    onAddTask: () -> Unit = {}
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAddTask()
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(tasks) { task ->
                Text(task, modifier = Modifier.clickable {
                    onEditTask(task)
                })
            }
        }
    }
}

@Preview
@Composable
fun TaskListPreview() {
    TaskList()
}