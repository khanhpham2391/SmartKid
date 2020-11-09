package com.khanhpham.smartkidz.ui.game

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.Difficulty
import com.khanhpham.smartkidz.data.models.Games
import com.khanhpham.smartkidz.databinding.ItemDifficultyBinding
import com.khanhpham.smartkidz.databinding.ItemPreviewGameBinding
import kotlinx.coroutines.selects.select

class GameViewHolder (val itemPreviewGameBinding: ItemPreviewGameBinding): RecyclerView.ViewHolder(itemPreviewGameBinding.root){

}

class GameAdapter (private var gameList: ArrayList<Games>): RecyclerView.Adapter<GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemPreviewGameBinding: ItemPreviewGameBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_preview_game,
            parent,
            false
        )
        return GameViewHolder(itemPreviewGameBinding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.itemPreviewGameBinding.gameInfo = gameList[position]
//        holder.itemPreviewGameBinding.RGDiff.setOnCheckedChangeListener(toggleListener)
    }

    override fun getItemCount(): Int = gameList.size


}

class GameViewModel{
    companion object{
        @JvmStatic
        @BindingAdapter("imageUrl", "placeholder")
        fun loadImage(view: ImageView, imageUrl: String?, placeholder: Drawable){
            if (!imageUrl.isNullOrEmpty()){
                Glide.with(view.context).load(GamesData.urlConvert(imageUrl)).into(view)
            } else {
                view.setImageDrawable(placeholder)
            }
        }
    }
}

class DifficultyViewHolder (val itemDifficultyBinding: ItemDifficultyBinding) : RecyclerView.ViewHolder(itemDifficultyBinding.root){
}
class DifficultyAdapter (
    private var diffList: ArrayList<Difficulty>,
    private val callback: (Int) -> Unit
): RecyclerView.Adapter<DifficultyViewHolder>(){

    var selectedIndex = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DifficultyViewHolder {

        val itemDifficultyBinding: ItemDifficultyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_difficulty,
            parent,
            false
        )
        //Log.d("diffList",": ${diffList}")
        return DifficultyViewHolder(itemDifficultyBinding)
    }

    override fun onBindViewHolder(holder: DifficultyViewHolder, position: Int) {
        holder.itemDifficultyBinding.toggle.setOnClickListener {
            val temp = selectedIndex
            selectedIndex = position
            notifyItemChanged(temp)
            notifyItemChanged(selectedIndex)
            callback.invoke(diffList[position].id)
        }

        holder.itemDifficultyBinding.difficultyChoice = diffList[position]
        holder.itemDifficultyBinding.toggle.isChecked = selectedIndex == position
    }

    override fun getItemCount(): Int = diffList.size

}


