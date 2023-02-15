package com.bigtoapp.notes.main.domain

import com.bigtoapp.notes.notes.domain.NoteDomain

interface ListInteractor<T> {

    suspend fun all(): T

    suspend fun delete(id: String): T
}