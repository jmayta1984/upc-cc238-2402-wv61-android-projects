package pe.edu.upc.jokescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import pe.edu.upc.jokescompose.common.Constants
import pe.edu.upc.jokescompose.data.local.AppDatabase
import pe.edu.upc.jokescompose.data.remote.JokeService
import pe.edu.upc.jokescompose.data.repository.JokeRepository
import pe.edu.upc.jokescompose.presentation.joke.JokeScreen
import pe.edu.upc.jokescompose.presentation.joke.JokeViewModel
import pe.edu.upc.jokescompose.presentation.jokelist.JokeListScreen
import pe.edu.upc.jokescompose.presentation.jokelist.JokeListViewModel
import pe.edu.upc.jokescompose.ui.theme.JokesComposeTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(JokeService::class.java)

        val appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "jokes-db")
            .build()
        val dao = appDatabase.getJokeDao()

        val viewModel = JokeListViewModel(JokeRepository(service, dao))
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JokesComposeTheme {
                JokeListScreen(viewModel)
            }
        }
    }
}
