import com.example.pokedex_with_compose.model.PokemonInfo
import com.example.pokedex_with_compose.network.model.PokemonResponse

interface PokedexRepository {
    suspend fun fetchPokemonList(
        page: Int,
    ): PokemonResponse

    suspend fun fetchPokemonInfo(
        name: String
    ): PokemonInfo
}

class NetworkPokedexRepository(
    private val pokedexService: PokedexService
) : PokedexRepository {
    override suspend fun fetchPokemonList(page: Int): PokemonResponse = pokedexService.fetchPokemonList(page)
    override suspend fun fetchPokemonInfo(name: String): PokemonInfo = pokedexService.fetchPokemonInfo(name)
}