package com.bigtoapp.notes.notes.presentation

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.Mapper

class NotesAdapter(
    private val noteActions: NoteActions
): RecyclerView.Adapter<NotesViewHolder>(), Mapper.Unit<List<NoteUi>> {

    private val list = mutableListOf<NoteUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false),
        noteActions
    )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    override fun map(source: List<NoteUi>) {
        val diff = DiffUtilCallback(list, source)
        val result = DiffUtil.calculateDiff(diff)
        list.clear()
        list.addAll(source)
        result.dispatchUpdatesTo(this)
    }
}

class NotesViewHolder(
    view: View,
    private val noteActions: NoteActions
): RecyclerView.ViewHolder(view){

    private val title = itemView.findViewById<TextView>(R.id.titleTextView)
    private val description = itemView.findViewById<TextView>(R.id.descriptionTextView)
    private val date = itemView.findViewById<TextView>(R.id.dateTextView)
    private val deleteButton = itemView.findViewById<Button>(R.id.deleteNoteButton)
    private val updateButton = itemView.findViewById<Button>(R.id.updateNoteButton)
    private val mapper = NoteItemUi(title, description, date)

    fun bind(model: NoteUi){
        model.map(mapper)
        deleteButton.setOnClickListener { noteActions.deleteNote(model.mapId()) }
        updateButton.setOnClickListener { noteActions.editNote(model.mapId()) }
    }

}

interface NoteActions{
    fun deleteNote(noteId: String)
    fun editNote(noteId: String)
}

class DiffUtilCallback(
    private val oldList: List<NoteUi>,
    private val newList: List<NoteUi>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].map(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].equals(newList[newItemPosition])
}