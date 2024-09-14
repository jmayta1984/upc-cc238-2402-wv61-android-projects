package pe.edu.upc.superherocompose

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
import pe.edu.upc.superherocompose.common.Constants
import pe.edu.upc.superherocompose.data.remote.HeroService
import pe.edu.upc.superherocompose.data.repository.HeroRepository
import pe.edu.upc.superherocompose.presentation.hero_list.HeroListScreen
import pe.edu.upc.superherocompose.presentation.hero_list.HeroListViewModel
import pe.edu.upc.superherocompose.ui.theme.SuperHeroComposeTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val service = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HeroService::class.java)
    private val viewModel = HeroListViewModel(HeroRepository(service))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperHeroComposeTheme {
                HeroListScreen(viewModel)
            }
        }
    }
}
