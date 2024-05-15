import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex_with_compose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier.fillMaxSize()
) {

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
    ) { innerPaddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home_route",
        ) {
            val pokemonName = "pokemonName"

            composable(route = "home_route") {
                /// TODO("ボイラープレートコードが汚いので HiltViewModel を使ってここのファクトリーを消す")
                val viewModel: HomeViewModel = viewModel(
                    factory = HomeViewModel.Factory
                )
                HomeScreen(
                    homeScreenApiState = viewModel.pokedexApiState,
                    retryAction = viewModel::retryAction,
                    loadMoreAction = viewModel::loadMoreAction,
                    onPokemonItemClicked = { pokemonName ->
                        navController.navigate("detail_route/{$pokemonName}")
                    },
                    modifier = Modifier.padding(innerPaddingValues)
                )
            }

            composable(
                route = "detail_route/{$pokemonName}",
                arguments = listOf(navArgument(pokemonName) { type = NavType.StringType })
            ) {backStackEntry ->
                /// TODO("ボイラープレートコードが汚いので HiltViewModel を使ってここのファクトリーを消す")
                val viewmodel: DetailViewModel = viewModel(
                    factory = DetailViewModel.Factory(backStackEntry.arguments)
                )
                DetailScreen(
                    deitailApiState = viewmodel.detailApiState,
                    retryAction = {},
                    modifier = modifier.padding(innerPaddingValues)
                )
            }
        }
    }
}