package com.bigtoapp.notes.notes.data

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

    @Query(
        "UPDATE notes_table " +
                "SET title = :title, subtitle = :subtitle, " +
                "performDate = :performDate, categoryId = :categoryId " +
                "WHERE id = :id"
    )
    fun updateNote(id: String, title: String, subtitle: String, performDate: Long, categoryId: String)

    // todo think move to another dao
    @Transaction
    @Query("SELECT * FROM notes_table")
    fun allNotesWithCategory(): List<NoteWithCategoryData>
}