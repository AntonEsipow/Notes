package com.bigtoapp.notes.notes.data.cache

import androidx.room.*
import com.bigtoapp.notes.notes.data.NoteData

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table")
    fun allNotes(): List<NoteData>

    @Query("DELETE FROM notes_table WHERE id = :noteId")
    fun deleteNote(noteId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteData: NoteData)

    @Query("UPDATE notes_table SET title = :title, subtitle = :subtitle WHERE id = :id")
    fun updateNote(id: String, title: String, subtitle: String)
}