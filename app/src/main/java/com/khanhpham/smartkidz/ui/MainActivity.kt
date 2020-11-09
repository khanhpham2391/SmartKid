package com.khanhpham.smartkidz.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.ui.game.GameActivity
import com.khanhpham.smartkidz.ui.history.HistoryActivity
import com.khanhpham.smartkidz.ui.leaderBoard.LeaderBoardActivity
import com.khanhpham.smartkidz.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        btnLeader.setOnClickListener {
            val intent = Intent(this,LeaderBoardActivity::class.java)
            startActivity(intent)
        }

        btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}