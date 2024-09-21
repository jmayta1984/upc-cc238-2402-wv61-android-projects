package pe.edu.upc.jokescompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.jokescompose.domain.Joke

@Database(entities = [Joke::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getJokeDao(): JokeDao
}