package pe.edu.upc.jokescompose.presentation.joke

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.jokescompose.common.Resource
import pe.edu.upc.jokescompose.data.repository.JokeRepository
import pe.edu.upc.jokescompose.domain.Joke

class JokeViewModel(private val repository: JokeRepository) : ViewModel() {

    private val _state = mutableStateOf(JokeState())
    val state: State<JokeState> get() = _state

    init {
        _state.value = JokeState(message = "No jokes found")
        getJoke()
    }

    fun onScoreChanged(newScore: Int) {
        _state.value.joke?.let { joke ->
            val newJoke = Joke(joke.description, if (newScore == joke.score) 0 else newScore)
            _state.value = JokeState(joke = newJoke)
            viewModelScope.launch {
                if (newScore == joke.score) {
                    repository.deleteJoke(joke)
                    return@launch
                }
                if (joke.score == 0) {
                    repository.insertJoke(newJoke)
                    return@launch
                }
                repository.updateJoke(newJoke)

            }
        }
    }

    fun getJoke() {
        _state.value = JokeState(isLoading = true)
        viewModelScope.launch {
            val result = repository.getJoke()
            if (result is Resource.Success) {
                _state.value = JokeState(joke = result.data)
            } else {
                _state.value = JokeState(message = result.message ?: "An error occurred")
            }

        }
    }
}

data class JokeState(
    val isLoading: Boolean = false,
    val joke: Joke? = null,
    val message: String = ""
)