package com.bigtoapp.notes.notes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.presentation.Mapper
import com.bigtoapp.notes.main.presentation.adapter.DiffUtilCallback
import com.bigtoapp.notes.notes.presentation.NoteId
import com.bigtoapp.notes.notes.presentation.NoteItemUi
import com.bigtoapp.notes.notes.presentation.NoteUi

class NotesAdapter(
    private val noteActions: ItemActions
): RecyclerView.Adapter<NotesViewHolder>(), Mapper.Unit<List<NoteUi>>, GetItemByPosition<NoteUi> {

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

    override fun getItem(position: Int) = list[position]
}

class NotesViewHolder(
    view: View,
    private val noteActions: ItemActions
): RecyclerView.ViewHolder(view){

    private val title = itemView.findViewById<TextView>(R.id.noteTitleTextView)
    private val description = itemView.findViewById<TextView>(R.id.descriptionTextView)
    private val date = itemView.findViewById<TextView>(R.id.dateTextView)

    private val categoryName = itemView.findViewById<TextView>(R.id.noteCategoryName)
    private val card = itemView.findViewById<CardView>(R.id.noteCardView)

    private val mapper = NoteItemUi(title, description, date, categoryName, card)
    private val mapId = NoteId()

    fun bind(model: NoteUi){
        model.map(mapper)
        card.setOnClickListener { noteActions.edit(model.map(mapId)) }
    }
}

interface ItemActions{
    fun edit(id: String)
}

interface GetItemByPosition<T>{
    fun getItem(position: Int): T
}