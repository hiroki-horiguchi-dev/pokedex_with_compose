import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.example.pokedex_with_compose.network.interseptor.HttpRequestInterceptor

interface PokedexContainer {
    val pokemonRepository: PokedexRepository
}

class PokedexDefaultContainer: PokedexContainer {
    private val baseUrl = "https://pokeapi.co/api/v2/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpRequestInterceptor())
        .build()

    private val pokemonRetrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val pokemonRetrofitService: PokedexService by lazy {
        pokemonRetrofit.create(PokedexService::class.java)
    }

    override val pokemonRepository: PokedexRepository by lazy {
        NetworkPokedexRepository(pokemonRetrofitService)
    }
}