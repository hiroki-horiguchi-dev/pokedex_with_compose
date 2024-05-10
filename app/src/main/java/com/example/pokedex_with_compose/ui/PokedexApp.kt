import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex_with_compose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexApp(
    modifier: Modifier = Modifier.fillMaxSize()
) {

    // TODO("Navhost を定義して一覧画面 --> 詳細画面の導線を作る")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) {innerPaddingValues ->
        val viewModel: PokedexViewModel = viewModel(
            factory = PokedexViewModel.Factory
        )
        PokedexScreen(
            pokedexApiState = viewModel.pokedexApiState,
            retryAction = viewModel::retryAction,
            loadMoreAction = viewModel::loadMoreAction,
            modifier = modifier.padding(innerPaddingValues))
    }
}