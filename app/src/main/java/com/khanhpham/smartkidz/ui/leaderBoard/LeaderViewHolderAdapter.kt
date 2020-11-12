package com.khanhpham.smartkidz.ui.leaderBoard

import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.models.AppUser
import com.khanhpham.smartkidz.databinding.ItemPositionBinding

class LeaderViewHolder(val itemPositionBinding: ItemPositionBinding) :
    RecyclerView.ViewHolder(itemPositionBinding.root)

class LeaderAdapter(var leaderMap: ArrayMap<Int, AppUser>) :
    RecyclerView.Adapter<LeaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderViewHolder {
        val itemPositionBinding: ItemPositionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_position,
            parent,
            false
        )
        return LeaderViewHolder(itemPositionBinding)
    }

    override fun onBindViewHolder(holder: LeaderViewHolder, position: Int) {

        holder.itemPositionBinding.positionL = leaderMap.keyAt(position)
        holder.itemPositionBinding.userLeader = leaderMap.valueAt(position)
        val score = leaderMap.valueAt(position).scoreCollection?.sumBy {
            it.score
        }
        Log.d("leaderAdapter","$position - id: ${leaderMap.keyAt(position)} - $score-  ${leaderMap.valueAt(position)}")
        holder.itemPositionBinding.scoreLeader = score
    }

    override fun getItemCount(): Int =leaderMap.size

    fun setUpAdapter(uCol: ArrayMap<Int,AppUser>){
        leaderMap.clear()
        leaderMap.putAll(uCol)
        notifyDataSetChanged()
    }
}

class UserAdapter(var userMap: ArrayMap<Int, AppUser>) :
    RecyclerView.Adapter<LeaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderViewHolder {
        val itemPositionBinding: ItemPositionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_position,
            parent,
            false
        )
        return LeaderViewHolder(itemPositionBinding)
    }

    override fun onBindViewHolder(holder: LeaderViewHolder, position: Int) {

        holder.itemPositionBinding.positionL = userMap.keyAt(position)
        holder.itemPositionBinding.userLeader = userMap.valueAt(position)
        val score = userMap.valueAt(position).scoreCollection?.sumBy {
            it.score
        }
        Log.d("userAdapter","$position - id: ${userMap.keyAt(position)} - $score-  ${userMap.valueAt(position)}")
        holder.itemPositionBinding.scoreLeader = score
    }

    override fun getItemCount(): Int =userMap.size

    fun setUpAdapter(userCol: ArrayMap<Int,AppUser>){
        userMap.clear()
        userMap.putAll(userCol)
        notifyDataSetChanged()
    }
}