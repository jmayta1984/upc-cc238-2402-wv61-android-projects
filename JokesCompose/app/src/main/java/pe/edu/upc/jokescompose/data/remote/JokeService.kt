package pe.edu.upc.jokescompose.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokeService {
    @Headers("Accept: application/json")
    @GET("/")
    suspend fun getJoke(): Response<JokeDto>
}