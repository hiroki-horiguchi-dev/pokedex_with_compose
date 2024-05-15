import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedex_with_compose.model.PokemonInfo

@Composable
fun DetailScreen(
    deitailApiState: DetailApiState,
    retryAction: () -> Unit,
    modifier: Modifier
) {
    Text(text = "Pokedex Detail")
}

@Composable
@Preview
private fun PreviewPokedexDetail() {
    DetailScreen(
        deitailApiState = DetailApiState.Success(
            PokemonInfo(
                id = 0,
                name = "",
                height = 2,
                weight = 50,
                experience = 40,
                types = listOf(
                    PokemonInfo.TypeResponse(
                        slot = 0,
                        type = PokemonInfo.Type(
                            name = ""
                        )
                    )
                ),
                hp = 0,
                attack = 40,
                defense = 40,
                speed = 50,
                exp = 30
            )
        ),
        retryAction = { /*TODO*/ },
        modifier = Modifier
    )
}
