import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex_with_compose.R
import com.example.pokedex_with_compose.model.Pokemon
import com.example.pokedex_with_compose.ui.theme.PokeDex_with_composeTheme

@Composable
fun PokedexScreen(
    pokedexApiState: PokedexApiState,
    retryAction: () -> Unit,
    loadMoreAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (pokedexApiState) {
        is PokedexApiState.Success -> {
            GridListScreen(pokedexApiState.data, modifier, loadMoreAction)
        }
        is PokedexApiState.Loading -> {
            LoadingScreen()
        }
        is PokedexApiState.Error -> {
            ErrorScreen(pokedexApiState.message, retryAction)
        }
        else -> {}
    }
}

@Composable
fun ErrorScreen(message: String, retryAction: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        Button(onClick = retryAction) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun LoadingScreen() {
    Image(
        modifier = Modifier.size(200.dp),
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = "Loading")
}

@Composable
fun GridListScreen(data: List<Pokemon>,modifier: Modifier = Modifier, loadMoreAction: () -> Unit) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(data) {
                GridCard(it)
            }
        },
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
    )
}

@Composable
fun GridCard(pokemon: Pokemon, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation()
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                modifier = modifier.fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.getImageUrl())
                    .crossfade(true)
                    .build(),
                contentDescription = "pokemon image",
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_launcher_background),
                placeholder = painterResource(id = R.drawable.loading_img)
                )
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewGridCard() {
    PokeDex_with_composeTheme {
        val pokemonFake = Pokemon(
            page = 1,
            name = "Bulbasaur",
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        )
        GridCard(
            pokemonFake
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewList() {
    val pokemonFakeList = listOf(
        Pokemon(
            page = 1,
            name = "Bulbasaur",
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        ),
        Pokemon(
            page = 1,
            name = "Bulbasaur",
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        ),
        Pokemon(
            page = 1,
            name = "Bulbasaur",
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        ),
        Pokemon(
            page = 1,
            name = "Bulbasaur",
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        ),
        Pokemon(
            page = 1,
            name = "Bulbasaur",
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        ),
        Pokemon(
            page = 1,
            name = "Bulbasaur",
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        ),
        Pokemon(
            page = 1,
            name = "Bulbasaur",
            url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        )
    )
    GridListScreen(data = pokemonFakeList, modifier = Modifier) {}
}