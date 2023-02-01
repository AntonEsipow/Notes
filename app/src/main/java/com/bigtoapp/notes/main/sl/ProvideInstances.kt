package com.bigtoapp.notes.main.sl

import android.content.Context
import com.bigtoapp.notes.main.data.CacheModule

interface ProvideInstances {

    fun provideCacheModule(): CacheModule

    class Release(private val context: Context): ProvideInstances{
        override fun provideCacheModule(): CacheModule = CacheModule.Release(context)
    }

    class Mock(private val context: Context): ProvideInstances{
        override fun provideCacheModule(): CacheModule = CacheModule.Mock(context)
    }
}