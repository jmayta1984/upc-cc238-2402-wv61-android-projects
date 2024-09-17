package pe.edu.upc.superherocompose.data.remote.dto

import com.google.gson.annotations.SerializedName
import pe.edu.upc.superherocompose.domain.Hero

data class HeroDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("biography")
    val biography: Biography,
    val image: Poster
)

data class Poster(
    val url: String
)

data class Biography(
    @SerializedName("full-name")
    val fullName: String,
    @SerializedName("publisher")
    val publisher: String
)

fun HeroDto.toHero(): Hero {
    return Hero(id, name, biography.fullName, biography.publisher, image.url)
}