package pe.edu.upc.authcompose.feature_auth.domain.use_case

import pe.edu.upc.authcompose.core.Resource
import pe.edu.upc.authcompose.feature_auth.data.remote.dto.UserDto
import pe.edu.upc.authcompose.feature_auth.data.remote.dto.toUser
import pe.edu.upc.authcompose.feature_auth.domain.model.User
import pe.edu.upc.authcompose.feature_auth.domain.repository.LoginRepository

class SignInUseCase (private val loginRepository: LoginRepository) {

    operator fun invoke(username: String, password: String, callback: (Resource<User>) -> Unit) {
        callback(Resource.Loading())
        loginRepository.signIn(username, password) { usersDto: List<UserDto> ->
            if (usersDto.isNotEmpty()) {
                callback(Resource.Success(data = usersDto[0].toUser()))
            } else {
                callback(Resource.Error(message = "An error occurred"))
            }
        }
    }

}