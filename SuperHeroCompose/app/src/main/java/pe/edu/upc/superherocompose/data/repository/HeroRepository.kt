package pe.edu.upc.superherocompose.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.superherocompose.common.Resource
import pe.edu.upc.superherocompose.data.local.HeroDao
import pe.edu.upc.superherocompose.data.local.HeroEntity
import pe.edu.upc.superherocompose.data.remote.HeroService
import pe.edu.upc.superherocompose.data.remote.dto.HeroDto
import pe.edu.upc.superherocompose.data.remote.dto.toHero
import pe.edu.upc.superherocompose.domain.Hero

class HeroRepository(
    private val heroService: HeroService,
    private val heroDao: HeroDao
) {
    private suspend fun isFavorite(heroDto: HeroDto): Boolean = withContext(Dispatchers.IO) {
        heroDao.fetchById(heroDto.id)?.let {
            return@withContext true
        }
        return@withContext false
    }

    suspend fun searchHeroes(name: String): Resource<List<Hero>> = withContext(Dispatchers.IO) {
        val response = heroService.searchHeroes(name)


        if (response.isSuccessful) {
            response.body()?.heroes?.let { heroesDto ->
                val heroes = mutableListOf<Hero>()

                heroesDto.forEach { heroDto: HeroDto ->
                    val hero = heroDto.toHero()
                    hero.isFavorite = isFavorite(heroDto)
                    heroes += hero
                }
                return@withContext Resource.Success(data = heroes.toList())
            }
            return@withContext Resource.Error(
                message = response.body()?.error ?: "An error occurred"
            )
        } else {
            return@withContext Resource.Error(message = response.message())
        }
    }

    suspend fun insertHero(hero: Hero) = withContext(Dispatchers.IO) {
        heroDao.insert(heroEntity = HeroEntity(hero.id, hero.name, hero.fullName, hero.url))
    }

    suspend fun deleteHero(hero: Hero) = withContext(Dispatchers.IO) {
        heroDao.delete(heroEntity = HeroEntity(hero.id, hero.name, hero.fullName, hero.url))

    }
}