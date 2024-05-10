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

sealed interface PokedexApiState {
    data class Success(val data: List<Pokemon>): PokedexApiState
    object Error: PokedexApiState
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
        TODO("Pokemon API の規約に、DBに保存して叩きまくらないようにって書いてあったので room に入れる分岐必須")
        fetchPokemonList()
    }

    fun fetchPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                pokedexRepository.fetchPokemonList(
                    limit = limit,
                    offset = 0
                )
            }.fold(
                onSuccess = {
                    TODO("PokedexApiState.Success")
                    TODO("offset の値をとれた List の数だけ更新、offset は Viewmodel が生きている間だけいればいい")
                    TODO("Room でローカル DB に保存する")
                },
                onFailure = {
                    TODO("PokedexApiState.Error")
                    TODO("現職だとかなり細かくみているので、できるだけ細かくハンドリングするサンプルを試しておきたい。")
                }
            )
        }
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
