package pe.edu.upc.jokescompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pe.edu.upc.jokescompose.domain.Joke

@Dao
interface JokeDao {

    @Insert
    suspend fun insert(joke: Joke)

    @Delete
    suspend fun delete(joke: Joke)

    @Update
    suspend fun update(joke: Joke)

    @Query("select * from jokes")
    suspend fun fetchAll(): List<Joke>

    @Query("select * from jokes where description = :description")
    suspend fun fetchByDescription(description: String): Joke?

}