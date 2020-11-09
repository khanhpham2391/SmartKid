package com.khanhpham.smartkidz.ui.game.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.EXTRA_GAME
import com.khanhpham.smartkidz.data.EXTRA_POINT
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.GameData
import com.khanhpham.smartkidz.data.models.History
import com.khanhpham.smartkidz.helpers.Status
import com.khanhpham.smartkidz.ui.MainActivity
import com.khanhpham.smartkidz.ui.game.gamePlay.PlayActivity
import kotlinx.android.synthetic.main.activity_result.*
import java.time.Instant
import java.util.*

class ResultActivity : AppCompatActivity() {

    private lateinit var gameData: GameData
    lateinit var resultViewModel: ResultViewModel

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
        setContentView(R.layout.activity_result)
        val correctAnswer = intent.getIntExtra(EXTRA_POINT,0)
        gameData = intent.getParcelableExtra(EXTRA_GAME)!!
        val history = History(0,GamesData.user.id,gameData.game,correctAnswer,null,null)
        resultViewModel = ResultViewModel(history)

        observeHistory()
        tvResult.text = "You get $correctAnswer pts"
        btnPlayAgain.setOnClickListener(playAgain)
        btnResultHome.setOnClickListener(home)
    }

    private fun observeHistory() {
        resultViewModel.historyItem.observe(this, { history->
            val (status, data, error, isFirst) = history
            when(status){
                Status.SUCCEED -> showData(data)
                Status.LOADING -> showLoading(isFirst)
                Status.FAILED -> showError(error)
            }
        })
    }

    private fun showError(error: Throwable?) {
        resultProgress.visibility = View.VISIBLE
        cvResult.visibility = View.GONE
    }

    private fun showLoading(first: Boolean) {
        resultProgress.visibility = View.VISIBLE
        cvResult.visibility = View.GONE
    }

    private fun showData(data: History?) {
        resultProgress.visibility = View.GONE
        cvResult.visibility = View.VISIBLE
    }

    private val playAgain = View.OnClickListener{
        val intent = Intent(this, PlayActivity::class.java)
        intent.putExtra(EXTRA_GAME,gameData)
        startActivity(intent)
    }
    private val home = View.OnClickListener{
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}