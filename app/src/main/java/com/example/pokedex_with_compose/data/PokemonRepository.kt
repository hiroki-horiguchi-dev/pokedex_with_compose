import com.example.pokedex_with_compose.model.PokemonInfo
import com.example.pokedex_with_compose.network.model.PokemonResponse

interface PokemonRepository {
    suspend fun fetchPokemonList(
        page: Int,
    ): PokemonResponse

    suspend fun fetchPokemonInfo(
        name: String
    ): PokemonInfo
}

class NetworkPokemonRepository(
    private val pokedexService: PokedexService
) : PokemonRepository {
    override suspend fun fetchPokemonList(page: Int): PokemonResponse = pokedexService.fetchPokemonList(page)
    override suspend fun fetchPokemonInfo(name: String): PokemonInfo = pokedexService.fetchPokemonInfo(name)
}