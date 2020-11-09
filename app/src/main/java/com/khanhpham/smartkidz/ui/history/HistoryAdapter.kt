package com.khanhpham.smartkidz.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.models.History
import com.khanhpham.smartkidz.databinding.ItemHistoryBinding

class HistoryViewHolder(val itemHistoryBinding: ItemHistoryBinding): RecyclerView.ViewHolder(itemHistoryBinding.root)

class HistoryAdapter(private var historyList: ArrayList<History>) : RecyclerView.Adapter<HistoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemHistoryBinding: ItemHistoryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_history,parent,false)
        return HistoryViewHolder(itemHistoryBinding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.itemHistoryBinding.history = historyList[position]
    }

    override fun getItemCount(): Int = historyList.size

    fun setUpHistory(historyL: List<History>){
        historyList.clear()
        historyList.addAll(historyL)
        notifyDataSetChanged()
    }
}

