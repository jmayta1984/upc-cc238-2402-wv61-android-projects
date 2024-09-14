package pe.edu.upc.superherocompose.presentation.hero_list

import pe.edu.upc.superherocompose.domain.Hero

data class HeroListState(
    val isLoading: Boolean = false,
    val heroes: List<Hero>? = null,
    val message: String = ""
)
