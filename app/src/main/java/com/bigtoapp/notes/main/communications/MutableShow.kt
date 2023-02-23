package com.bigtoapp.notes.main.communications

interface MutableShow<L, S>: ShowList<L>, ShowState<S>, ShowProgress

interface ShowList<T>{
    fun showList(showList: List<T>)
}

interface ShowState<T>{
    fun showState(showState: T)
}

interface ShowProgress{
    fun showProgress(show: Int)
}