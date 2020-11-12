package com.khanhpham.smartkidz.ui.leaderBoard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.SmartKidzApplication
import com.khanhpham.smartkidz.data.models.AppUser
import com.khanhpham.smartkidz.helpers.Status
import com.khanhpham.smartkidz.ui.MainActivity
import com.khanhpham.smartkidz.ui.profile.editProfile.EditViewModel
import com.khanhpham.smartkidz.ui.profile.profilepicture.PictureAdapter
import com.khanhpham.smartkidz.ui.profile.profilepicture.PictureViewModel
import kotlinx.android.synthetic.main.activity_leader_board.*
import java.util.HashMap
import javax.inject.Inject

class LeaderBoardActivity : AppCompatActivity() {

    @Inject
    lateinit var leaderAdapter: LeaderAdapter

//    @Inject
//    lateinit var userAdapter: UserAdapter

    private val viewModel: LeaderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        SmartKidzApplication.instance.component.inject(this)

        recyclerManager()
        observeUser()
        btnBackLB.setOnClickListener{
            val intent =Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeUser() {
        viewModel.userSource.observe(this, {
            val (status, data, error, isFirst) = it
            when(status){
                Status.SUCCEED -> showUser(data)
                Status.LOADING -> showLoading(isFirst)
                Status.FAILED -> showError(error)
            }
        })
    }

    private fun showError(error: Throwable?) {
        board_fetch_error.visibility = View.VISIBLE
        board_fetch_progress.visibility = View.GONE
        Log.d("leader","$error")
    }

    private fun showLoading(first: Boolean) {
        if (first){
            board_fetch_error.visibility = View.GONE
            board_fetch_progress.visibility = View.VISIBLE
        }
    }

    private fun showUser(data: Map<Int, AppUser>?) {
        board_fetch_error.visibility = View.GONE
        board_fetch_progress.visibility = View.GONE
//        val topMap = ArrayMap<Int,AppUser>()
//        topMap[1] = data?.get(1)
//        topMap[2] = data?.get(2)
//        topMap[3] = data?.get(3)
//        //Log.d("leaderboard","${topMap.keyAt(1)}")


        val userMap = ArrayMap<Int,AppUser>()
        data?.keys?.forEach {
            userMap[it] = data[it]
        }
        leaderAdapter.setUpAdapter(userMap)
        //if (!userMap.isEmpty()) userAdapter.setUpAdapter(userMap)
    }

    private fun recyclerManager() {
        top3Board.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoardActivity, LinearLayoutManager.VERTICAL,false)
            adapter = leaderAdapter
        }
//        userPosition.apply {
//            layoutManager = LinearLayoutManager(this@LeaderBoardActivity, LinearLayoutManager.VERTICAL,false)
//            adapter = userAdapter
//        }
    }
}