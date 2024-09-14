package pe.edu.upc.superherocompose.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.superherocompose.common.Resource
import pe.edu.upc.superherocompose.data.remote.HeroService
import pe.edu.upc.superherocompose.data.remote.dto.toHero
import pe.edu.upc.superherocompose.domain.Hero

class HeroRepository(private val heroService: HeroService) {
    suspend fun searchHeroes(name: String): Resource<List<Hero>> = withContext(Dispatchers.IO) {
        val response = heroService.searchHeroes(name)

        if (response.isSuccessful) {
            response.body()?.heroes?.let { heroesDto ->
                return@withContext Resource.Success(data = heroesDto.map { it.toHero() }.toList())
            }
            return@withContext Resource.Error(message = "Data not found")
        } else {
            return@withContext Resource.Error(message = "An error occurred")
        }
    }
}