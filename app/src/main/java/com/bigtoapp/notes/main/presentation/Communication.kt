package com.bigtoapp.notes.main.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class Communication {

    interface Observe<T> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>)
    }

    interface Put<T>{
        fun put(value: T)
    }

    interface Get<T>{
        fun get(): T
    }

    interface EmptyGet<T>{
        fun emptyGet(): T?
    }

    interface Mutable<T>: Observe<T>, Put<T>, Get<T>, EmptyGet<T>

    abstract class Abstract<T>(
        protected val liveData: MutableLiveData<T> = MutableLiveData()
    ): Mutable<T> {

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) =
            liveData.observe(owner, observer)

        override fun get(): T = liveData.value!!

        override fun emptyGet(): T? = liveData.value
    }

    abstract class Ui<T>(
        liveData: MutableLiveData<T> = MutableLiveData()
    ): Communication.Abstract<T>(liveData) {

        override fun put(value: T) {
            liveData.value = value
        }
    }

    abstract class Post<T>(
        liveData: MutableLiveData<T> = MutableLiveData()
    ): Communication.Abstract<T>(liveData){

        override fun put(value: T) = liveData.postValue(value)
    }

    abstract class SingleUi<T> : Ui<T>(SingleLiveEvent())
    abstract class SinglePost<T> : Post<T>(SingleLiveEvent())
}