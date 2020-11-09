package com.khanhpham.smartkidz.ui.game.topic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.EXTRA_GAME
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.GameData
import com.khanhpham.smartkidz.data.models.Topic

class TopicActivity: AppCompatActivity() {
    lateinit var gameData: GameData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)
        gameData = intent.getParcelableExtra(EXTRA_GAME)!!


        findViewById<RecyclerView>(R.id.topicRecyclerView).apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = TopicAdapter(GamesData.games[gameData.gamePosition].topicsCollection as List<Topic>, gameData)
        }

    }
}