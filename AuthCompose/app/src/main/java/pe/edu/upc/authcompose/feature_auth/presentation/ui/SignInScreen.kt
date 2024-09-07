package pe.edu.upc.authcompose.feature_auth.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pe.edu.upc.authcompose.feature_auth.presentation.viewmodel.SignInViewModel

@Composable
fun LoginScreen(viewModel: SignInViewModel) {

    val state = viewModel.state.value
    Scaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            OutlinedTextField(value = viewModel.usernameState.value, onValueChange = { username ->
                viewModel.onUsernameChanged(username)
            })

            OutlinedTextField(value = viewModel.passwordState.value, onValueChange = { password ->
                viewModel.onPasswordChanged(password)
            })
            OutlinedButton(onClick = {
                viewModel.signIn()
            }) {
                Text(text = "Sign in")
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.error.isNotEmpty()){
                Text(state.error)
            }
            state.user?.let {
                Text("Welcome ${it.username}")
            }

        }
    }
}
