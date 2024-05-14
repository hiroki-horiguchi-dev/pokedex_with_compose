import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokedex_with_compose.PokedexApplication
import com.example.pokedex_with_compose.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PokedexApiState {
    data class Success(val data: List<Pokemon>): PokedexApiState
    data class Error(val message: String): PokedexApiState
    object Loading: PokedexApiState
}

class PokedexViewModel(
    private val pokedexRepository: PokedexRepository
) : ViewModel() {

    /**
     * API パラメータ　offset 管理
     */
    private var offset = 0

    var pokedexApiState: PokedexApiState by mutableStateOf(PokedexApiState.Loading)
        private set

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                pokedexRepository.fetchPokemonList(
                    limit = limit,
                    offset = offset
                )
            }.fold(
                onSuccess = { response ->
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        pokedexApiState = PokedexApiState.Success(body.results)
                        offset += body.count
                    } else {
                        errorHandling(response.code())
                    }
                },
                onFailure = {
                    errorHandling(it)
                }
            )
        }
    }

    /**
     * 通信成功時、エラーハンドリング
     */
    private fun errorHandling(code: Int) {
        pokedexApiState = when (code) {
            400 -> PokedexApiState.Error(message = "Bad Request")
            401 -> PokedexApiState.Error(message = "Unauthorized")
            403 -> PokedexApiState.Error(message = "Forbidden")
            404 -> PokedexApiState.Error(message = "Not Found")
            else -> PokedexApiState.Error(message = "その他のエラー")
        }
    }

    /**
     * 通信失敗時、エラーハンドリング
     */
    private fun errorHandling(throwable: Throwable) {
        pokedexApiState = when(throwable) {
            is IOException -> PokedexApiState.Error(message = "ネットワークエラー")
            is HttpException -> PokedexApiState.Error(message = "サーバーエラー")
            else -> PokedexApiState.Error(message = "その他のエラー")
        }
    }

    /**
     * リトライアクション、エラー時に使用
     */
    fun retryAction() {
        fetchPokemonList()
    }

    /**
     * 追加読み込み時に使用
     */
    fun loadMoreAction() {
        fetchPokemonList()
    }

    companion object {
        private val TAG = PokedexViewModel::class.java.simpleName
        private const val limit = 20

        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PokedexApplication)
                val pokedexRepository = application.pokedexDefaultContainer.pokemonRepository
                PokedexViewModel(
                    pokedexRepository = pokedexRepository
                )
            }
        }
    }
}
