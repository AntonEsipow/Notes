package com.bigtoapp.notes.categories.presentation

import android.graphics.Color
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
import com.bigtoapp.notes.notes.presentation.ItemActions

class CategoriesAdapter(
    private val categoryActions: ItemCategoryActions
): RecyclerView.Adapter<CategoriesViewHolder>(), Mapper.Unit<List<CategoryUi>> {

    private val list = mutableListOf<CategoryUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoriesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false),
        categoryActions
    )

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    override fun map(source: List<CategoryUi>) {
        val diff = DiffUtilCallback(list, source)
        val result = DiffUtil.calculateDiff(diff)
        list.clear()
        list.addAll(source)
        result.dispatchUpdatesTo(this)
    }
}

class CategoriesViewHolder(
    view: View,
    private val categoryActions: ItemCategoryActions
): RecyclerView.ViewHolder(view){

    private val title = itemView.findViewById<TextView>(R.id.categoryTitleTextView)
    private val deleteButton = itemView.findViewById<Button>(R.id.deleteCategoryButton)
    private val updateButton = itemView.findViewById<Button>(R.id.updateCategoryButton)
    private val viewCard = itemView.findViewById<CardView>(R.id.cardView)
    private val mapper = CategoryItemUi(title, viewCard)
    private val mapId = MapCategoryId()
    private val mapColor = MapCategoryColor()

    fun bind(model: CategoryUi){
        model.map(mapper)
        deleteButton.setOnClickListener { categoryActions.delete(model.map(mapId)) }
        updateButton.setOnClickListener { categoryActions.edit(model.map(mapId), model.map(mapColor)) }
    }
}

interface ItemCategoryActions {
    fun delete(id: String)
    fun edit(id: String, color: Int)
}