package pe.edu.upc.authcompose.feature_auth.domain.repository

import pe.edu.upc.authcompose.feature_auth.data.remote.dto.UserDto

interface LoginRepository {

    fun signIn(username: String, password: String,  callback: (List<UserDto>) -> Unit)

    fun signUp(username: String, password: String)
}