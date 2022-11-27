package com.example.fakestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.R
import com.example.fakestore.utils.CategoryListener

class CategoryAdapter(private var categoryListener: CategoryListener, var categories: ArrayList<String>?) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_category, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.cardCategory.text = categories?.get(position) ?: "Category Not Found"
        holder.categoryCard.setOnClickListener {
            categoryListener.isSelected(categories?.get(position) ?: "")
        }

    }

    override fun getItemCount(): Int {
        return categories?.size ?: 0
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardCategory: TextView = itemView.findViewById<TextView>(R.id.cardCategory)
        val categoryCard: CardView = itemView.findViewById<CardView>(R.id.categoryCard)
    }


}