package com.bigtoapp.notes.notes.presentation

import android.annotation.SuppressLint
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.ManageResources
import com.bigtoapp.notes.note.domain.Generate
import java.text.SimpleDateFormat
import java.util.*

interface DateFormatter<T, V> {
    fun format(value: V): T
}

class DateToUi: DateFormatter<String, Long>{
    @SuppressLint("SimpleDateFormat")
    override fun format(value: Long): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        return dateFormat.format(Date(value))
    }
}

class DateToDomain(
    private val generate: Generate.GenerateTime<String>,
    private val manageResources: ManageResources
): DateFormatter<Long, String>{
    override fun format(value: String): Long {
        @SuppressLint("SimpleDateFormat")
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        if(value == manageResources.string(R.string.select_date)){
            val date = generate.generateTime()
            return dateFormat.parse(date).time
        }
        return dateFormat.parse(value).time
    }
}

