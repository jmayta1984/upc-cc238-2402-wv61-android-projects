package pe.edu.upc.authcompose.feature_auth.data.remote

import pe.edu.upc.authcompose.feature_auth.data.remote.dto.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {

    @GET("users")
    fun signIn(
        @Query("username")
        username: String,
        @Query("password")
        password: String
    ): Call<List<UserDto>>

}