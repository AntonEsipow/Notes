package com.bigtoapp.notes.main.presentation

interface Dialog<T> {
    fun create(): T
}