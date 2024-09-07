package pe.edu.upc.authcompose.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upc.authcompose.core.ui.theme.AuthComposeTheme
import pe.edu.upc.authcompose.feature_auth.data.remote.LoginService
import pe.edu.upc.authcompose.feature_auth.data.repository.LoginRepositoryImpl
import pe.edu.upc.authcompose.feature_auth.domain.use_case.SignInUseCase
import pe.edu.upc.authcompose.feature_auth.presentation.ui.LoginScreen
import pe.edu.upc.authcompose.feature_auth.presentation.viewmodel.SignInViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private val service = Retrofit.Builder()
        .baseUrl(ApiClient.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LoginService::class.java)

    private val repository = LoginRepositoryImpl(service)
    private val useCase = SignInUseCase(repository)
    private val viewModel =SignInViewModel(useCase)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuthComposeTheme {
               LoginScreen(viewModel )
            }
        }
    }
}
