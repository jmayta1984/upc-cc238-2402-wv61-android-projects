package pe.edu.upc.jokescompose.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.jokescompose.common.Resource
import pe.edu.upc.jokescompose.data.remote.JokeDto
import pe.edu.upc.jokescompose.data.remote.JokeService
import pe.edu.upc.jokescompose.data.remote.toJoke
import pe.edu.upc.jokescompose.domain.Joke

class JokeRepository(private val service: JokeService) {

    suspend fun getJoke(): Resource<Joke> = withContext(Dispatchers.IO) {
        val response = service.getJoke()

        if (response.isSuccessful) {
            response.body()?.let { jokeDto: JokeDto ->
                return@withContext Resource.Success(data = jokeDto.toJoke())
            }
            return@withContext Resource.Error(message = response.message())
        }
        return@withContext Resource.Error(message = response.message())

    }
}