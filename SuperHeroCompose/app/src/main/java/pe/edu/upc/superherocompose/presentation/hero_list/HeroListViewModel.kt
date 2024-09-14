package pe.edu.upc.superherocompose.presentation.hero_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.superherocompose.common.Resource
import pe.edu.upc.superherocompose.data.repository.HeroRepository
import pe.edu.upc.superherocompose.domain.Hero

class HeroListViewModel(private val heroRepository: HeroRepository) : ViewModel() {

    private val _heroListState = mutableStateOf(HeroListState())
    val heroListState: State<HeroListState> get() = _heroListState

    private val _name = mutableStateOf("")
    val name: State<String> get() = _name

    fun searchHero() {
        _heroListState.value = HeroListState(isLoading = true)
        viewModelScope.launch {
            val result = heroRepository.searchHeroes(_name.value)
            if (result is Resource.Success) {
                _heroListState.value = HeroListState(heroes = result.data)
            } else {
                _heroListState.value =
                    HeroListState(message = result.message ?: "An error occurred")
            }

        }
    }

    fun onNameChanged(name: String) {
        _name.value = name
    }

    fun onToggleFavorite(hero: Hero) {
        hero.isFavorite = !hero.isFavorite
        val heroes = _heroListState.value.heroes
        _heroListState.value = HeroListState(heroes = emptyList())
        _heroListState.value = HeroListState(heroes = heroes?.toList())

    }

}