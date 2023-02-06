package com.bigtoapp.notes.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bigtoapp.notes.main.data.ToDoRoomDatabase
import com.bigtoapp.notes.notes.data.NoteData
import com.bigtoapp.notes.notes.data.cache.NotesDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class NotesRoomTest {

    private lateinit var db: ToDoRoomDatabase
    private lateinit var dao: NotesDao

    @Before
    fun setUp(){
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, ToDoRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.notesDao()
    }

    @After
    @Throws(IOException::class)
    fun clear(){
        db.close()
    }

    @Test
    fun test_add_note(){
        val note = NoteData("1", "book", "alice in wonderland", 1L, 1L)
        assertEquals(emptyList<NoteData>(), dao.allNotes())

        dao.insertNote(note)
        val actualList = dao.allNotes()
        assertEquals(note, actualList[0])
    }

    @Test
    fun test_add_2_notes(){
        val noteFirst = NoteData("1", "book", "alice in wonderland", 1L, 1L)
        val noteSecond = NoteData("2", "shop", "peanuts", 2L, 2L)

        assertEquals(emptyList<NoteData>(), dao.allNotes())
        dao.insertNote(noteFirst)
        val list = dao.allNotes()
        assertEquals(noteFirst, list[0])

        dao.insertNote(noteSecond)
        val newList = dao.allNotes()
        assertEquals(noteFirst, newList[0])
        assertEquals(noteSecond, newList[1])
    }

    @Test
    fun test_delete_note(){
        val noteFirst = NoteData("1", "book", "alice in wonderland", 1L, 1L)
        val noteSecond = NoteData("2", "shop", "peanuts", 2L, 2L)

        dao.insertNote(noteFirst)
        dao.insertNote(noteSecond)
        val list = dao.allNotes()
        assertEquals(2, list.size)

        dao.deleteNote("1")
        val newList = dao.allNotes()
        assertEquals(1, newList.size)
        assertEquals(noteSecond, newList[0])
    }

    @Test
    fun test_delete_last_note(){
        val note = NoteData("1", "book", "alice in wonderland", 1L, 1L)
        assertEquals(emptyList<NoteData>(), dao.allNotes())

        dao.insertNote(note)
        val list = dao.allNotes()
        assertEquals(note, list[0])

        dao.deleteNote("1")
        val newList = dao.allNotes()
        assertEquals(0, newList.size)
        assertEquals(emptyList<NoteData>(), newList)
    }

    @Test
    fun test_update_note(){
        val noteFirst = NoteData("1", "book", "alice in wonderland", 1L, 1L)
        val noteSecond = NoteData("2", "shop", "peanuts", 2L,2L)

        assertEquals(emptyList<NoteData>(), dao.allNotes())
        dao.insertNote(noteFirst)
        val list = dao.allNotes()
        assertEquals(noteFirst, list[0])

        dao.insertNote(noteSecond)
        val newList = dao.allNotes()
        assertEquals(noteFirst, newList[0])
        assertEquals(noteSecond, newList[1])

        dao.updateNote("1", "workout", "bench press", 2L)
        val finalList = dao.allNotes()
        assertEquals(2, finalList.size)
        assertEquals(
            NoteData("1", "workout", "bench press", 1L, 2L),
            finalList[0]
        )
    }
}