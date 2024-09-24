package pe.edu.upc.appturismo.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PackageService {

    @GET("productossitiotipo.php")
    suspend fun getPackages(@Query("sitio") place: String, @Query("tipo") type: String): Response<List<PackageDto>>
}