package pe.edu.upc.authcompose.feature_auth.presentation.viewmodel

import pe.edu.upc.authcompose.feature_auth.domain.model.User

data class SingInState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)
