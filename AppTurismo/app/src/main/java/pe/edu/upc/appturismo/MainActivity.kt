package pe.edu.upc.appturismo

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
import pe.edu.upc.appturismo.common.Constants
import pe.edu.upc.appturismo.data.remote.PackageService
import pe.edu.upc.appturismo.data.repository.PackageRepository
import pe.edu.upc.appturismo.presentation.PackageListScreen
import pe.edu.upc.appturismo.presentation.PackageListViewModel
import pe.edu.upc.appturismo.ui.theme.AppTurismoTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val service = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PackageService::class.java)
        val repository = PackageRepository(service)
        val viewModel = PackageListViewModel(repository)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTurismoTheme {
                PackageListScreen(viewModel)

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTurismoTheme {
        Greeting("Android")
    }
}