import com.example.pokedex_with_compose.model.PokemonInfo
import com.example.pokedex_with_compose.network.model.PokemonResponse
import retrofit2.Response

interface PokedexRepository {
    suspend fun fetchPokemonList(
        limit: Int,
        offset: Int
    ): Response<PokemonResponse>

    suspend fun fetchPokemonInfo(
        name: String
    ): Response<PokemonInfo>
}

class NetworkPokedexRepository(
    private val pokedexService: PokedexService
) : PokedexRepository {
    override suspend fun fetchPokemonList(limit: Int, offset: Int): Response<PokemonResponse> = pokedexService.fetchPokemonList(
        limit, offset
    )
    override suspend fun fetchPokemonInfo(name: String): Response<PokemonInfo> = pokedexService.fetchPokemonInfo(name)
}