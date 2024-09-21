package pe.edu.upc.jokescompose.presentation.jokelist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.jokescompose.common.Resource
import pe.edu.upc.jokescompose.data.repository.JokeRepository
import pe.edu.upc.jokescompose.domain.Joke

class JokeListViewModel(private val repository: JokeRepository) : ViewModel() {
    private val _state = mutableStateOf(JokeListState())
    val state: State<JokeListState> get() = _state

    init {
        _state.value = JokeListState(isLoading = true)
        viewModelScope.launch {
            val result = repository.getJokes()
            if (result is Resource.Success) {
                _state.value = JokeListState(jokes = result.data)
            } else {
                _state.value = JokeListState(message = result.message ?: "An error occurred")
            }

        }
    }
}


data class JokeListState(
    val isLoading: Boolean = false,
    val jokes: List<Joke>? = null,
    val message: String = ""
)