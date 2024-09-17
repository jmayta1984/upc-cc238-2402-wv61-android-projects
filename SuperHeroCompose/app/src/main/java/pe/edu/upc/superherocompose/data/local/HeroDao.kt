package pe.edu.upc.superherocompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HeroDao {
    @Insert
    suspend fun insert(heroEntity: HeroEntity)

    @Delete
    suspend fun delete(heroEntity: HeroEntity)

    @Query("select * from heroes")
    suspend fun fetchAll(): List<HeroEntity>

    @Query("select * from heroes where id= :id ")
    suspend fun fetchById(id: String): HeroEntity?
}