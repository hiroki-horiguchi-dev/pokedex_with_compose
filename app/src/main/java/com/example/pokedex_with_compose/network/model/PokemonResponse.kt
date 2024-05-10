package com.example.pokedex_with_compose.network.model

import com.example.pokedex_with_compose.model.Pokemon
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>,
)