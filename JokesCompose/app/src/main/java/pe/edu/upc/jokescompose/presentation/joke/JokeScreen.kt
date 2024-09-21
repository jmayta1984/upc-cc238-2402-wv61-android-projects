package pe.edu.upc.jokescompose.presentation.joke

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import pe.edu.upc.jokescompose.domain.Joke

@Composable
fun JokeScreen(viewModel: JokeViewModel) {
    val state = viewModel.state.value

    Scaffold { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(onClick = { viewModel.getJoke() }) {
                Text(text = "Get joke")
            }
            state.joke?.let { joke: Joke ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = joke.description)
                    Row {
                        for (i in 1..5) {
                            IconButton(onClick = {
                                viewModel.onScoreChanged(i)
                            }) {
                                Icon(
                                    Icons.Filled.Star,
                                    "score",
                                    tint = if (i <= joke.score) Color.Red else Color.Gray
                                )
                            }
                        }
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.message.isNotEmpty()) {
                Text(state.message)
            }
        }
    }

}