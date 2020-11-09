package com.khanhpham.smartkidz.ui.history

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.SmartKidzApplication
import com.khanhpham.smartkidz.data.models.History
import com.khanhpham.smartkidz.helpers.Status
import com.khanhpham.smartkidz.ui.MainActivity
import kotlinx.android.synthetic.main.activity_history.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class HistoryActivity : AppCompatActivity() {

    @Inject
    lateinit var historyAdapter: HistoryAdapter

    private val historyViewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        SmartKidzApplication.instance.component.inject(this)
        //recycler view
        rcHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }
        observeHistory()
        btnBackHistory.setOnClickListener(back)
    }

    private fun observeHistory() {
        historyViewModel.historyCardItem.observe(this,{
            val (status, data, error, isFirst) = it
            when(status){
                Status.FAILED -> showError(error)
                Status.LOADING -> showLoading(isFirst)
                Status.SUCCEED -> showHistory(data)
            }
        })
    }

    @SuppressLint("SimpleDateFormat")
    private fun showHistory(data: List<History>?) {
        if (data != null) {
            history_fetch_progress.visibility = View.GONE
            history_fetch_error.visibility = View.GONE
            rcHistory.visibility = View.VISIBLE

            data.forEach {
                val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val newFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
                it.timestamp = it.timestamp ?.let { str ->
                    newFormatter.format(formatter.parse(str)!!) } .toString()
            }
            historyAdapter.setUpHistory(data)
        }
    }

    private fun showLoading(first: Boolean) {
        history_fetch_progress.visibility = View.VISIBLE
        if (first){
            history_fetch_error.visibility = View.GONE
            rcHistory.visibility = View.GONE
        }
    }

    private fun showError(error: Throwable?) {
        history_fetch_error.visibility = View.VISIBLE
        history_fetch_progress.visibility = View.GONE
        rcHistory.visibility = View.GONE
        Log.d("historyList","$error")
    }

    private fun recyclerViewManager(data: List<History>?) {
        rcHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = HistoryAdapter(data as ArrayList<History>)
        }
    }

    private val back = View.OnClickListener {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}