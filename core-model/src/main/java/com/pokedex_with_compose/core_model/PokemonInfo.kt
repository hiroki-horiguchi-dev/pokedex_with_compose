package com.pokedex_with_compose.core_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class PokemonInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    @SerialName("base_experience")
    val experience: Int,
    val types: List<TypeResponse>,
    val hp: Int = Random.nextInt(MAX_HP),
    val attack: Int = Random.nextInt(MAX_ATTACK),
    val defense: Int = Random.nextInt(MAX_DEFENSE),
    val speed: Int = Random.nextInt(MAX_SPEED),
    val exp: Int = Random.nextInt(MAX_EXP),
) {

    @Serializable
    data class TypeResponse(
        val slot: Int,
        val type: Type
    )

    @Serializable
    data class Type(
        val name: String
    )

    fun getIdString(): String = String.format("#%03d", id)
    fun getWeightString(): String = String.format("%.1f KG", weight.toFloat() / 10)
    fun getHeightString(): String = String.format("%.1f M", height.toFloat() / 10)
    fun getHpString(): String = " $hp/$MAX_HP"
    fun getAttackString(): String = " $attack/$MAX_ATTACK"
    fun getDefenseString(): String = " $defense/$MAX_DEFENSE"
    fun getSpeedString(): String = " $speed/$MAX_SPEED"
    fun getExpString(): String = " $exp/$MAX_EXP"

    companion object {
        const val MAX_HP = 300
        const val MAX_ATTACK = 300
        const val MAX_DEFENSE = 300
        const val MAX_SPEED = 300
        const val MAX_EXP = 1000
    }
}