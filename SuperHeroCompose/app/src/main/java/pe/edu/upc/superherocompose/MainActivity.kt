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
import androidx.room.Room
import pe.edu.upc.superherocompose.common.Constants
import pe.edu.upc.superherocompose.data.local.AppDatabase
import pe.edu.upc.superherocompose.data.local.HeroDao
import pe.edu.upc.superherocompose.data.remote.HeroService
import pe.edu.upc.superherocompose.data.repository.HeroRepository
import pe.edu.upc.superherocompose.presentation.hero_list.HeroListScreen
import pe.edu.upc.superherocompose.presentation.hero_list.HeroListViewModel
import pe.edu.upc.superherocompose.ui.theme.SuperHeroComposeTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Room.databaseBuilder(context = applicationContext, AppDatabase::class.java,"database-heroes").build()
        val dao = db.heroDao()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(HeroService::class.java)

        val viewModel = HeroListViewModel(HeroRepository(service, dao))
        setContent {
            SuperHeroComposeTheme {
                HeroListScreen(viewModel)
            }
        }
    }
}
