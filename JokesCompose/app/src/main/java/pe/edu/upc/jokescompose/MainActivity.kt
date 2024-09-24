package pe.edu.upc.jokescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlinx.serialization.Serializable
import pe.edu.upc.jokescompose.common.Constants
import pe.edu.upc.jokescompose.common.TopLevelRoute
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

@Serializable
object Joke

@Serializable
object JokeList

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(JokeService::class.java)

        val appDatabase =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "jokes-db")
                .build()
        val dao = appDatabase.getJokeDao()

        val jokeViewModel = JokeViewModel(JokeRepository(service, dao))
        val jokeListViewModel = JokeListViewModel(JokeRepository(service, dao))

        val topLevelRoutes = listOf(
            TopLevelRoute("Search", Joke, Icons.Filled.Search),
            TopLevelRoute("Favorites", JokeList, Icons.Filled.Favorite)
        )

        super.onCreate(savedInstanceState)
        setContent {
            JokesComposeTheme {
                val navController = rememberNavController()

                val selectedRoute = remember {
                    mutableStateOf(0)
                }

                Scaffold(bottomBar = {
                    BottomNavigation(backgroundColor = MaterialTheme.colors.onPrimary) {
                        topLevelRoutes.forEachIndexed { index, topLevelRoute ->
                            BottomNavigationItem(
                                selected = (index == selectedRoute.value),
                                label = {
                                    Text(
                                        topLevelRoute.name,
                                        color = if (index == selectedRoute.value) MaterialTheme.colors.primary
                                        else Color.Gray
                                    )
                                },
                                icon = {
                                    Icon(
                                        topLevelRoute.icon,
                                        topLevelRoute.name,
                                        tint = if (index == selectedRoute.value) MaterialTheme.colors.primary
                                        else Color.Gray
                                    )
                                },
                                onClick = {
                                    navController.navigate(topLevelRoute.route)
                                    selectedRoute.value = index
                                },
                            )
                        }
                    }
                }

                ) { paddingValues ->


                    NavHost(
                        navController = navController,
                        startDestination = Joke,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable<Joke> { JokeScreen(jokeViewModel) }
                        composable<JokeList> { JokeListScreen(jokeListViewModel) }
                        // Add more destinations similarly.

                    }
                }
            }
        }
    }
}


