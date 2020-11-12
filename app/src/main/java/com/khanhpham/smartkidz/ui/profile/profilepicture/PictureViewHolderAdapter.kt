package com.khanhpham.smartkidz.ui.profile.profilepicture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.models.IconImage
import com.khanhpham.smartkidz.databinding.ItemPictureBinding

class PictureViewHolder(val itemPictureBinding: ItemPictureBinding): RecyclerView.ViewHolder(itemPictureBinding.root)

class PictureAdapter(var imageList: ArrayList<IconImage>): RecyclerView.Adapter<PictureViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val itemPictureBinding: ItemPictureBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_picture,
            parent,
            false
        )
        return PictureViewHolder(itemPictureBinding)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.itemPictureBinding.image = imageList[position]
    }

    override fun getItemCount(): Int = imageList.size

    fun setUpPicture(pictureList: ArrayList<IconImage>){
        imageList.clear()
        imageList.addAll(pictureList)
        notifyDataSetChanged()
    }
}