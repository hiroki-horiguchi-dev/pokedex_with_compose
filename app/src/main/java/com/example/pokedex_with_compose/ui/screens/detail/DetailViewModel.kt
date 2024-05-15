import android.app.FragmentManager
import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokedex_with_compose.PokedexApplication
import com.example.pokedex_with_compose.model.PokemonInfo

sealed interface DetailApiState {
    data class Success(val data: PokemonInfo) : DetailApiState
    data class Error(val message: String) : DetailApiState
    object Loading : DetailApiState
}

class DetailViewModel(
    private val pokemonName: String,
    private val pokedexRepository: PokedexRepository
) : ViewModel() {

    var detailApiState: DetailApiState by mutableStateOf(DetailApiState.Loading)
        private set

    init {
        // TODO("詳細 API を叩く"))
    }


    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
        fun Factory(args: Bundle?): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application =
                        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PokedexApplication)
                    val pokedexRepository = application.pokedexDefaultContainer.pokemonRepository

                    val pokemonName = args?.getString("pokemonName") ?: ""
                    DetailViewModel(
                        pokemonName = pokemonName,
                        pokedexRepository = pokedexRepository
                    )
                }
            }
    }
}