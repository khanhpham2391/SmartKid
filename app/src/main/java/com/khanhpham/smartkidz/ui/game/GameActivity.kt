package com.khanhpham.smartkidz.ui.game

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.EXTRA_GAME
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.Difficulty
import com.khanhpham.smartkidz.data.models.GameData
import com.khanhpham.smartkidz.data.models.Games
import com.khanhpham.smartkidz.ui.MainActivity
import com.khanhpham.smartkidz.ui.game.topic.TopicActivity
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {

    var gameData = GameData(0, 0, 0, 0)

    private val viewPool = RecyclerView.RecycledViewPool()

    val toggleListener = RadioGroup.OnCheckedChangeListener { radioGroup, i ->
        for (j in 0 until radioGroup.childCount) {
            val view = radioGroup.getChildAt(j) as ToggleButton
            view.isChecked = view.id == i
            Log.d("toggle button","button id: ${view.id}")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_GAME, gameData)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        gameData = savedInstanceState.getParcelable(EXTRA_GAME)!!
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)
        ivKid.setImageResource(R.drawable.kids)
        //ivKid.layoutParams.width = "120dp"

        viewPagerManager()
        diffLayoutManager()
        gameViewPager.registerOnPageChangeCallback(swipe)
        btnBack.setOnClickListener(back)
        btnNext.setOnClickListener(next)
    }

    private fun viewPagerManager() {
        findViewById<ViewPager2>(R.id.gameViewPager).apply {
            // Set offscreen page limit to at least 1, so adjacent pages are always laid out
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = resources.getDimensionPixelOffset(R.dimen.halfPageMargin) +
                        resources.getDimensionPixelOffset(R.dimen.halfPageMargin)
                // setting padding on inner RecyclerView puts overscroll effect in the right place
                // TODO: expose in later versions not to rely on getChildAt(0) which might break
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
            adapter = GameAdapter(GamesData.games as ArrayList<Games>)
        }
    }

    private fun diffLayoutManager(){

        val diffLayoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)

        diffLayoutManager.initialPrefetchItemCount = 2

        recyclerDiff.apply {
            layoutManager = diffLayoutManager
            if (gameViewPager.currentItem>0){
                adapter = DifficultyAdapter(GamesData.games[gameViewPager.currentItem].difficultiesCollection as ArrayList<Difficulty>){ value ->
                    gameData.diff = value;
                }
            } else {
                adapter = DifficultyAdapter(GamesData.games[0].difficultiesCollection as ArrayList<Difficulty>){ value ->
                    gameData.diff = value;
                }
            }

            setRecycledViewPool(viewPool)
        }
    }

    private val back = View.OnClickListener {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private val next = View.OnClickListener{
        gameData.game = GamesData.games[gameViewPager.currentItem].id
        gameData.gamePosition = gameViewPager.currentItem
        Log.d("gameid: ",": ${gameData.game} - ${gameData.diff}")
        if (gameData.diff==0){
            Toast.makeText(this, "Please choose a difficulty!", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, TopicActivity::class.java)
            intent.putExtra(EXTRA_GAME, gameData)
            startActivity(intent)
        }
    }

    private val swipe = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            recyclerDiff.adapter = DifficultyAdapter(GamesData.games[gameViewPager.currentItem].difficultiesCollection as ArrayList<Difficulty>){
                gameData.diff = it
            }
            recyclerDiff.adapter!!.notifyDataSetChanged()
        }
    }
}