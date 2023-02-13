package com.bigtoapp.notes.main.data

import com.bigtoapp.notes.notes.domain.NoteDomain

interface ListRepository<T> {

    suspend fun delete(id: String)

    suspend fun all(): T
}