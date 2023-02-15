package com.bigtoapp.notes.category.color

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigtoapp.notes.main.presentation.Communication

interface ColorCommunications: ObserveColor, GetColor {

    fun showColor(state: ColorState)

    class Base(
        private val color: ColorCommunication
    ): ColorCommunications{

        override fun showColor(state: ColorState) = color.put(state)

        override fun emptyGet(): ColorState = color.emptyGet() ?: ColorState()

        override fun observeColor(owner: LifecycleOwner, observer: Observer<ColorState>) =
            color.observe(owner, observer)
    }
}

interface ObserveColor{
    fun observeColor(owner: LifecycleOwner, observer: Observer<ColorState>)
}

interface GetColor {
    fun emptyGet(): ColorState
}

interface ColorCommunication: Communication.Mutable<ColorState>{
    class Base: Communication.Post<ColorState>(), ColorCommunication
}