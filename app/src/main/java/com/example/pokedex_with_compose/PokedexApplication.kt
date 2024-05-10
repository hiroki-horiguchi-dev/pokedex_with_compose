package com.example.pokedex_with_compose

import PokedexContainer
import PokedexDefaultContainer
import android.app.Application

class PokedexApplication : Application() {
    lateinit var pokedexDefaultContainer: PokedexContainer

    override fun onCreate() {
        super.onCreate()
        pokedexDefaultContainer = PokedexDefaultContainer()
    }
}