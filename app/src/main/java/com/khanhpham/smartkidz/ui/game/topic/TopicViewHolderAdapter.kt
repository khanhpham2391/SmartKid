package com.khanhpham.smartkidz.ui.game.topic

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.EXTRA_GAME
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.GameData
import com.khanhpham.smartkidz.data.models.Topic
import com.khanhpham.smartkidz.databinding.ItemTopicBinding
import com.khanhpham.smartkidz.ui.game.gamePlay.PlayActivity

class TopicViewHolder( val itemTopicBinding: ItemTopicBinding): RecyclerView.ViewHolder(itemTopicBinding.root)

class TopicAdapter(val topicList: List<Topic>, val gameData: GameData): RecyclerView.Adapter<TopicViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val itemTopicBinding: ItemTopicBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_topic,
            parent,
            false
        )
        return TopicViewHolder(itemTopicBinding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.itemTopicBinding.topic = topicList[position]
        holder.itemTopicBinding.topicChoice.setOnClickListener {
             gameData.topic = topicList[position].id
            val intent = Intent(holder.itemTopicBinding.root.context,PlayActivity::class.java)
            intent.putExtra(EXTRA_GAME, gameData)
            holder.itemTopicBinding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = topicList.size

//    companion object{
//        @JvmStatic
//        @BindingAdapter("imageUrl", "placeholder")
//        fun loadImage(view: ImageView, imageUrl: String?, placeholder: Drawable){
//            if (!imageUrl.isNullOrEmpty()){
//                Glide.with(view.context).load(GamesData.urlConvert(imageUrl)).into(view)
//            } else {
//                view.setImageDrawable(placeholder)
//            }
//        }
//    }
}