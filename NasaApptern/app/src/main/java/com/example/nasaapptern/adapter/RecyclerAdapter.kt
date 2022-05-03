package com.example.nasaapptern.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapptern.R
import com.example.nasaapptern.databinding.ItemRowBinding
import com.example.nasaapptern.model.Photo
import com.squareup.picasso.Picasso

class RecyclerAdapter(var onItemClick:(position:Int)->Unit,val photoList:ArrayList<String>):RecyclerView.Adapter<RecyclerAdapter.ItemVH>() {
    class ItemVH(val binding:ItemRowBinding):RecyclerView.ViewHolder(binding.root) {
        lateinit var itemClick: (position : Int)->Unit
        init {
            binding.root.setOnClickListener {
                itemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val binding=ItemRowBinding.inflate(LayoutInflater.from(parent!!.context),parent,false)

        return ItemVH(binding)
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val Photo=photoList.get(position)

        with(holder){
            Picasso.get().load(Photo).resize(200,200).into(binding.ivItemPhoto)
       //     binding.tvEarthDate.text=Photo.earth_date
        //    binding.tvCarName.text=Photo.rover!!.name
            itemClick=onItemClick
        }
    }
    override fun getItemCount(): Int {
        return photoList.size
    }
}