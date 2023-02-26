package com.bigtoapp.notes.dialog.presentation

import android.graphics.Color
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
): RecyclerView.Adapter<SelectCategoryViewHolder>(), Mapper.Unit<List<SelectedCategoryUi>>,
    SetDefaultCategory{

    private val list = mutableListOf<SelectedCategoryUi>()
    private var setSelected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SelectCategoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.select_category_item, parent, false),
        selectedCategoryItem
    )

    override fun onBindViewHolder(holder: SelectCategoryViewHolder, position: Int) {
        val function = { pos: Int ->
            if(setSelected != pos){
                setSelected = pos
                notifyDataSetChanged()
            }
        }
        holder.bind(list[position], function, setSelected == position, position)
    }

    override fun getItemCount(): Int = list.size

    override fun map(source: List<SelectedCategoryUi>) {
        list.clear()
        list.addAll(source)
    }

    override fun setDefaultCategory(){
        setSelected = -1
        notifyDataSetChanged()
    }
}

class SelectCategoryViewHolder(
    view: View,
    private val selectedCategoryItem: SelectedCategoryItem
): RecyclerView.ViewHolder(view){

    private val title = itemView.findViewById<TextView>(R.id.selectCategoryItemName)
    private val cardView = itemView.findViewById<CardView>(R.id.selectCategoryView)
    private val mapper = SelectCategoryItemUi(title, cardView)

    fun bind(model: SelectedCategoryUi, function: (Int) -> Unit, selected: Boolean, position: Int){
        cardView.setOnClickListener {
            selectedCategoryItem.setSelectedItem(model)
            function(position)
        }
        model.changeSelected(selected)
        model.map(mapper)
    }
}

interface SelectedCategoryItem{
    fun setSelectedItem(categoryUi: SelectedCategoryUi)
}

interface SetDefaultCategory{
    fun setDefaultCategory()
}