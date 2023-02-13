package com.bigtoapp.notes.note.domain

import android.provider.Settings
import com.bigtoapp.notes.notes.presentation.DateFormatter
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.LocalDateTime
import java.util.*

interface Generate {

    interface GenerateId{
        fun generateId(): String
    }

    interface GenerateTime<T>{
        fun generateTime(): T
    }

    interface Mutable<T>: GenerateId, GenerateTime<T>

    abstract class Abstract(): GenerateId {
        override fun generateId(): String = UUID.randomUUID().toString()
    }

    class RandomId:Abstract()

    class CalendarTime(
        private val formatter: DateFormatter<String, Long>
    ): GenerateTime<String> {
        override fun generateTime() = formatter.format(System.currentTimeMillis())
    }

    class IdAndCurrentTime(): Abstract(), Mutable<Long> {
        override fun generateTime(): Long = System.currentTimeMillis()
    }
}