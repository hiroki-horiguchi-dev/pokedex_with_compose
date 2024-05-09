package com.pokedex_with_compose.core_model

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    var page: Int = 0,
    val name: String,
    val url: String
) {

    // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png
    // こんな感じのやつが取れるので Coil の AsyncImage で表示する
    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/$index.png"
    }

    /// 先頭文字を大文字変換するやつね
    fun name(): String = name.replaceFirstChar { it.uppercase() }
}
