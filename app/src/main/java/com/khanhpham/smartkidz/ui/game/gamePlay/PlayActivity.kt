package com.khanhpham.smartkidz.ui.game.gamePlay

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.SmartKidzApplication
import com.khanhpham.smartkidz.data.EXTRA_GAME
import com.khanhpham.smartkidz.data.EXTRA_POINT
import com.khanhpham.smartkidz.data.FALSE
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.Difficulty
import com.khanhpham.smartkidz.data.models.GameData
import com.khanhpham.smartkidz.data.models.GameDetails
import com.khanhpham.smartkidz.helpers.Status
import com.khanhpham.smartkidz.ui.game.GameActivity
import com.khanhpham.smartkidz.ui.game.result.ResultActivity
import kotlinx.android.synthetic.main.activity_play.*


class PlayActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var viewModel: PlayViewModel
    private var mCurrentPosition = -1
    private var mCurrentCorrectAnswerId = 1
    private var mCurrentOptionId = 0
    private var mCorrectAnswer = 0
    private lateinit var mQuestionList: List<GameDetails>
    private lateinit var mCurrentQuestion: GameDetails
    private lateinit var gameData: GameData
    private lateinit var difficulty: Difficulty
    private lateinit var countTimer: CountDownTimer

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_GAME, gameData)
        outState.putInt(EXTRA_POINT,mCorrectAnswer)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        gameData = savedInstanceState.getParcelable(EXTRA_GAME)!!
        mCorrectAnswer = savedInstanceState.getInt(EXTRA_POINT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        gameData = intent.getParcelableExtra(EXTRA_GAME)!!
        viewModel = PlayViewModel(gameData)
        for (d: Difficulty in GamesData.games[gameData.gamePosition].difficultiesCollection){
            if (d.id == gameData.diff)
                difficulty = d
        }
        observeGameData()
        option1.setOnClickListener(this)
        option2.setOnClickListener(this)
        option3.setOnClickListener(this)
        option4.setOnClickListener(this)
        btnPlaySubmit.setOnClickListener(this)
    }

    private fun observeGameData() {
        viewModel.gameDataList.observe(this, Observer {
            val (status, data, error, isFirst) = it
            when (status) {
                Status.LOADING -> showLoading(isFirst)
                Status.FAILED -> showError(error)
                Status.SUCCEED -> showListGame(data)
                else -> showError(error)
            }
        })
    }

    private fun showListGame(data: List<GameDetails>?) {
        content.visibility = View.VISIBLE
        playError.visibility = View.GONE
        playProgressBar.visibility = View.GONE
        countTotalTimer(secondToMilli(difficulty.totalTimePlay))
        if (data != null) {
            mQuestionList = data
            play(mQuestionList)
        }
    }

    private fun showError(error: Throwable?) {
        content.visibility = View.GONE
        playError.visibility = View.VISIBLE
        playProgressBar.visibility = View.GONE
        val intent = Intent(this@PlayActivity,GameActivity::class.java)
        intent.putExtra(FALSE,1)
        startActivity(intent)
        Log.d("PlayActivity","showError: $error")
    }

    private fun showLoading(first: Boolean) {
        if (first){
            content.visibility = View.GONE
            playProgressBar.visibility = View.VISIBLE
            playError.visibility = View.GONE
        }
    }

    private fun play(data: List<GameDetails>) {

        mCurrentPosition += 1
        mCurrentQuestion = data[mCurrentPosition]

        defaultOptionsView()

        countTimer = countTimer(secondToMilli(difficulty.timeLimit))
        countTimer.start()

        progressBar.progress = mCurrentPosition +1
        progressBar.max = mQuestionList.size
        tvProgress.text = "${mCurrentPosition+1} / ${progressBar.max}"
        Glide.with(playImage.context).load(GamesData.urlConvert(mCurrentQuestion.image)).into(playImage)
        playQuestion.text = mCurrentQuestion.question
        option1.text = mCurrentQuestion.answerOne
        option2.text = mCurrentQuestion.answerTwo
        option3. text = mCurrentQuestion.answerThree
        option4.text = mCurrentQuestion.answerFour
        btnPlaySubmit.text = getString(R.string.submit)
    }


    private fun countTimer(millisecond: Long): CountDownTimer {
        return object : CountDownTimer(millisecond, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = ("${millisUntilFinished / 1000}")
            }
            override fun onFinish() {
                submitAnswer()
            }
        }
    }

    private fun countTotalTimer(millisecond: Long){
        object : CountDownTimer(millisecond, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                tvTotalTimer.text = "$minutes : $seconds"
            }
            override fun onFinish() {
                val intent = Intent(this@PlayActivity,ResultActivity::class.java)
                intent.putExtra(EXTRA_POINT,mCorrectAnswer)
                intent.putExtra(EXTRA_GAME,gameData)
                startActivity(intent)
            }
        }.start()
    }

    private fun secondToMilli(sec: Int): Long{
        return sec*1000.toLong()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.option1 -> {
                selectedOptionView(option1)
            }
            R.id.option2 -> {
                selectedOptionView(option2)
            }
            R.id.option3 -> {
                selectedOptionView(option3)
            }
            R.id.option4 -> {
                selectedOptionView(option4)
            }
            R.id.btnPlaySubmit -> {
                submitAnswer()
            }
        }
    }

    private fun submitAnswer() {
        countTimer.cancel()
        if (btnPlaySubmit.text.toString() == getString(R.string.submit)) {
            if (mCurrentCorrectAnswerId == mCurrentOptionId){
                mCorrectAnswer++
                findViewById<TextView>(mCurrentOptionId).background = ContextCompat.getDrawable(this,R.drawable.button_true)
            } else {
                if (mCurrentOptionId>0) {
                    findViewById<TextView>(mCurrentOptionId).background =
                        ContextCompat.getDrawable(this, R.drawable.button_false)
                    findViewById<TextView>(mCurrentCorrectAnswerId).background = ContextCompat.getDrawable(this,R.drawable.button_true)
                }
            }
            if (mCurrentPosition+1 < mQuestionList.size){
                btnPlaySubmit.text = getString(R.string.next_question)
            } else {
                btnPlaySubmit.text = getString(R.string.finish)
            }
        } else if (btnPlaySubmit.text.toString() == getString(R.string.next_question)){
            mCurrentCorrectAnswerId = 1
            mCurrentOptionId = 0
            play(mQuestionList)
        } else {
            Toast.makeText(this,"Finish with $mCorrectAnswer point",Toast.LENGTH_LONG).show()
            val intent = Intent(this,ResultActivity::class.java)
            intent.putExtra(EXTRA_POINT,mCorrectAnswer)
            intent.putExtra(EXTRA_GAME,gameData)
            startActivity(intent)
        }
    }

    private fun selectedOptionView(tv: TextView){
        defaultOptionsView()

        //tv.setTextColor(Color.parseColor("#363A43"))
        //tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.button_brown_stroke)
        mCurrentOptionId = tv.id
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0,option1)
        options.add(1,option2)
        options.add(2,option3)
        options.add(3,option4)

        for (option in options){
            if (option.text.toString() == mCurrentQuestion.correctAnswer){
                mCurrentCorrectAnswerId = option.id
            }
            option.background = ContextCompat.getDrawable(this,R.drawable.button_brown)

        }
    }
}