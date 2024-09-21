package pe.edu.upc.jokescompose.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.jokescompose.common.Resource
import pe.edu.upc.jokescompose.data.local.JokeDao
import pe.edu.upc.jokescompose.data.remote.JokeDto
import pe.edu.upc.jokescompose.data.remote.JokeService
import pe.edu.upc.jokescompose.data.remote.toJoke
import pe.edu.upc.jokescompose.domain.Joke

class JokeRepository(
    private val service: JokeService,
    private val dao: JokeDao
) {

    suspend fun insertJoke(joke: Joke) = withContext(Dispatchers.IO){
        dao.insert(joke)
    }

    suspend fun deleteJoke(joke: Joke) = withContext(Dispatchers.IO){
        dao.delete(joke)
    }

    suspend fun updateJoke(joke: Joke) = withContext(Dispatchers.IO){
        dao.update(joke)
    }

    suspend fun getJokes(): Resource<List<Joke>> = withContext(Dispatchers.IO){
        try {
            val jokes = dao.fetchAll()
            return@withContext Resource.Success(data = jokes)

        } catch (e: Exception) {
            return@withContext Resource.Error(message = e.message?: "An exception occurred")
        }
    }

    suspend fun getJoke(): Resource<Joke> = withContext(Dispatchers.IO) {
        try {
            val response = service.getJoke()

            if (response.isSuccessful) {
                response.body()?.let { jokeDto: JokeDto ->
                    val joke = jokeDto.toJoke()

                    dao.fetchByDescription(joke.description)?.let { localJoke ->
                        joke.score = localJoke.score
                    }

                    return@withContext Resource.Success(data = joke)
                }
                return@withContext Resource.Error(message = response.message())
            }
            return@withContext Resource.Error(message = response.message())
        } catch (e: Exception) {
            return@withContext Resource.Error(message = e.message ?: "An exception occurred")

        }


    }
}