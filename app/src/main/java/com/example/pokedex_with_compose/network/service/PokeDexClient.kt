package com.example.pokedex_with_compose.network.service

import PokedexService
import com.example.pokedex_with_compose.model.PokemonInfo
import com.example.pokedex_with_compose.network.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class PokedexClient @Inject constructor(
    private val pokedexService: PokedexService,
) {

    suspend fun fetchPokemonList(page: Int): PokemonResponse =
        pokedexService.fetchPokemonList(
            limit = PAGING_SIZE,
            offset = page * PAGING_SIZE,
        )

    suspend fun fetchPokemonInfo(name: String): PokemonInfo =
        pokedexService.fetchPokemonInfo(
            name = name,
        )

    companion object {
        private const val PAGING_SIZE = 20
    }
}