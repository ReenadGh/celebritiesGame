package com.example.post


import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*


//import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter( private val items: List<UserItem>): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){
    class ItemViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val pk = items[position].pk
        val name = items[position].name
        val location = items[position].location


        holder.itemView.apply {

         pk1.text = pk.toString()
         name1.text = name
         loc.text = location

        }
    }

    override fun getItemCount() = items.size





}