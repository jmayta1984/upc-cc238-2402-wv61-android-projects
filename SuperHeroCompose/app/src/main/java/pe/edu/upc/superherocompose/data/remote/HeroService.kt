package pe.edu.upc.superherocompose.data.remote

import pe.edu.upc.superherocompose.data.remote.dto.HeroesWrapperDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroService {

    @GET("f274286a22873ec9fc7a5782940f7ca2/search/{name}")
    suspend fun searchHeroes(@Path("name") name: String): Response<HeroesWrapperDto>

}