package com.bigtoapp.notes.dialog.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bigtoapp.notes.R
import com.bigtoapp.notes.categories.presentation.CategoryItemUi
import com.bigtoapp.notes.categories.presentation.CategoryUi
import com.bigtoapp.notes.main.presentation.Mapper

class SelectCategoryAdapter(
    private val selectedCategoryItem: SelectedCategoryItem
): RecyclerView.Adapter<SelectCategoryViewHolder>(), Mapper.Unit<List<CategoryUi>>{

    private val list = mutableListOf<CategoryUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SelectCategoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.select_category_item, parent, false),
        selectedCategoryItem
    )

    override fun onBindViewHolder(holder: SelectCategoryViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    override fun map(source: List<CategoryUi>) {
        list.clear()
        list.addAll(source)
    }
}

class SelectCategoryViewHolder(
    view: View,
    private val selectedCategoryItem: SelectedCategoryItem
): RecyclerView.ViewHolder(view){

    private val title = itemView.findViewById<TextView>(R.id.selectCategoryItemName)
    private val cardView = itemView.findViewById<CardView>(R.id.selectCategoryView)
    private val mapper = CategoryItemUi(title, cardView)

    fun bind(model: CategoryUi){
        cardView.setOnClickListener {
            selectedCategoryItem.setSelectedItem(model)
        }
        model.map(mapper)
    }
}

interface SelectedCategoryItem{
    fun setSelectedItem(categoryUi: CategoryUi)
}