package com.bigtoapp.notes.main.data

import com.bigtoapp.notes.notes.domain.NoteDomain

interface MutableListRepository<T>: ListRepository<T>, DeleteRepository

interface ListRepository<T>{
    suspend fun all(): T
}

interface DeleteRepository{
    suspend fun delete(id: String)
}