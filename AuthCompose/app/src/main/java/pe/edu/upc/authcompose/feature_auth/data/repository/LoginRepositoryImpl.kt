package pe.edu.upc.authcompose.feature_auth.data.repository

import pe.edu.upc.authcompose.feature_auth.data.remote.LoginService
import pe.edu.upc.authcompose.feature_auth.data.remote.dto.UserDto
import pe.edu.upc.authcompose.feature_auth.domain.repository.LoginRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepositoryImpl (private val loginService: LoginService): LoginRepository {
    override fun signIn(username: String, password: String, callback: (List<UserDto>) -> Unit) {
        loginService.signIn(username, password).enqueue(object : Callback<List<UserDto>> {
            override fun onResponse(call: Call<List<UserDto>>, response: Response<List<UserDto>>) {
                if (response.isSuccessful){
                    callback(response.body()?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<UserDto>>, response: Throwable) {
                TODO("Not yet implemented")
            }

        } )
    }

    override fun signUp(username: String, password: String) {

    }
}