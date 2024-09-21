package pe.edu.upc.jokescompose.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.jokescompose.common.Resource
import pe.edu.upc.jokescompose.data.repository.JokeRepository
import pe.edu.upc.jokescompose.domain.Joke

class JokeViewModel(private val repository: JokeRepository): ViewModel() {

    private val _state = mutableStateOf(JokeState())
    val state: State<JokeState> get() = _state

    private val _description = mutableStateOf("No jokes found")
    val description: State<String> get() = _description

    fun getJoke() {
        _state.value = JokeState(isLoading = true)
        viewModelScope.launch {
            val result = repository.getJoke()
            if (result is Resource.Success) {
                _state.value = JokeState(joke = result.data)
                _description.value = result.data?.description?:"No jokes found"
            } else {
                _state.value = JokeState(message = result.message?: "An error occurred")
            }

        }
    }
}

data class JokeState(
    val isLoading: Boolean = false,
    val joke: Joke? = null,
    val message: String = ""
)