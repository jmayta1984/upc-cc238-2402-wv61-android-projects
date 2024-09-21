package pe.edu.upc.jokescompose.data.remote

import pe.edu.upc.jokescompose.domain.Joke

data class JokeDto(
    val id: String,
    val joke: String,
    val status: Int
)

fun JokeDto.toJoke(): Joke = Joke(joke)
