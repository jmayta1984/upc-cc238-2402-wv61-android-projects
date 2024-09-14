package pe.edu.upc.superherocompose.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HeroesWrapperDto(
    @SerializedName("response")
    val response: String,
    @SerializedName("error")
    val error: String?,
    @SerializedName("results")
    val heroes: List<HeroDto>?
)