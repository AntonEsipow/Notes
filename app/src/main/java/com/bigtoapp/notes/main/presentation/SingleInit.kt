package com.bigtoapp.notes.main.presentation

interface SingleInit {
    fun singleInit(isFirstRun: Boolean)
}

interface Init{
    fun init()
}

interface InitWithId{
    fun init(isFirstRun: Boolean, id: String)
}