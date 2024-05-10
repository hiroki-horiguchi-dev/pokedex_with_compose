import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface PokedexContainer {
    val pokemonRepository: PokedexRepository
}

class PokedexDefaultContainer: PokedexContainer {
    private val BASE_URL = "https://pokeapi.co/api/v2/"

    private val pokemonRetrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val pokemonRetrofitService: PokedexService by lazy {
        pokemonRetrofit.create(PokedexService::class.java)
    }

    override val pokemonRepository: PokedexRepository by lazy {
        NetworkPokedexRepository(pokemonRetrofitService)
    }
}