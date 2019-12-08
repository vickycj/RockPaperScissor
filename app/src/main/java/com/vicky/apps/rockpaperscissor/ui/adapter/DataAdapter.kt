package com.vicky.apps.rockpaperscissor.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.vicky.apps.gamecore.GameConstants
import com.vicky.apps.rockpaperscissor.R
import com.vicky.apps.rockpaperscissor.ui.model.GameItems


class DataAdapter constructor(var data:List<GameItems>, val clickListener: (GameItems, Int) -> Unit) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_child_view,parent,false)
        return DataViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        when(data[position].item){
            GameConstants.OBJECT_NAME_ROCK -> holder.imageView.setImageResource(R.drawable.rock)
            GameConstants.OBJECT_NAME_SCISSOR -> holder.imageView.setImageResource(R.drawable.scissor)
            GameConstants.OBJECT_NAME_PAPER -> holder.imageView.setImageResource(R.drawable.paper)
        }

        if(data[position].selected){
            holder.imageView.alpha = 1F
        }else{
            holder.imageView.alpha = 0.1F
        }

        holder.imageView.setOnClickListener { clickListener(data[position],position) }
    }

    fun updateData(data: List<GameItems>){
        this.data = data
        notifyDataSetChanged()
    }
    class DataViewHolder(v:View): RecyclerView.ViewHolder(v){
        val imageView = v.findViewById<ImageView>(R.id.itemImage)
    }
}