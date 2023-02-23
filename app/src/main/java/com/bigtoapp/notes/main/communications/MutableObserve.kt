package com.bigtoapp.notes.main.communications

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface MutableObserve<L, S>: ObserveList<L>, ObserveState<S>, ObserveProgress

interface ObserveList<T>{
    fun observeList(owner: LifecycleOwner, observer: Observer<List<T>>)
}

interface ObserveState<T>{
    fun observeState(owner: LifecycleOwner, observer: Observer<T>)
}

interface ObserveProgress{
    fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>)
}