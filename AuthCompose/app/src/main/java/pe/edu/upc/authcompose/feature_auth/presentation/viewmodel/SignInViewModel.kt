package pe.edu.upc.authcompose.feature_auth.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pe.edu.upc.authcompose.core.Resource
import pe.edu.upc.authcompose.feature_auth.domain.model.User
import pe.edu.upc.authcompose.feature_auth.domain.use_case.SignInUseCase

class SignInViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _state = mutableStateOf(SingInState())
    val state: State<SingInState> get () = _state

    private val _usernameState = mutableStateOf("")
    val usernameState: State<String> get() = _usernameState

    private val _passwordState = mutableStateOf("")
    val passwordState: State<String> get() = _passwordState

    fun onUsernameChanged(username: String) {
        _usernameState.value = username
    }

    fun onPasswordChanged(password: String) {
        _passwordState.value = password
    }

    fun signIn() {

        signInUseCase.invoke(_usernameState.value, _passwordState.value) { result: Resource<User> ->
            when (result) {
                is Resource.Success -> {
                    _state.value = SingInState(user = result.data)
                }
                is Resource.Error -> {
                    _state.value = SingInState(error = result.message?:"An error occurred")
                }
                is Resource.Loading -> {
                    _state.value = SingInState(isLoading = true)
                }
            }
        }
    }






}