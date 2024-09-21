package pe.edu.upc.jokescompose.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class Joke(
    @PrimaryKey
    val description: String,
    @ColumnInfo("score")
    var score: Int = 0
)
