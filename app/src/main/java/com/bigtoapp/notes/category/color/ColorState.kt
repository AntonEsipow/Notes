package com.bigtoapp.notes.category.color

import com.bigtoapp.notes.main.presentation.Mapper

data class ColorState(
    private val red: Int = 0,
    private val green: Int = 0,
    private val blue: Int = 0
): Mapper<String, ColorState> {

    override fun map(source: ColorState) = "Color ($red, $green, $blue)"

    interface Mapper<T>{
        fun map(red: Int, green: Int, blue: Int): T
    }

    fun <T> map(mapper: Mapper<T>):T = mapper.map(red, green, blue)
}

class MapRed: ColorState.Mapper<Int>{
    override fun map(red: Int, green: Int, blue: Int): Int = red
}

class MapGreen: ColorState.Mapper<Int>{
    override fun map(red: Int, green: Int, blue: Int): Int = green
}

class MapBlue: ColorState.Mapper<Int>{
    override fun map(red: Int, green: Int, blue: Int): Int = blue
}