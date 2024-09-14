package pe.edu.upc.superherocompose.data.remote.dto

import com.google.gson.annotations.SerializedName
import pe.edu.upc.superherocompose.domain.Hero

data class HeroDto (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("biography")
    val biography: Biography
)

data class Biography (
    @SerializedName("full-name")
    val fullName: String,
    @SerializedName("publisher")
    val publisher: String
)

fun HeroDto.toHero(): Hero {
    return Hero(name, biography.fullName, biography.publisher)
}