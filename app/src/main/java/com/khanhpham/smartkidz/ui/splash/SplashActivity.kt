package com.khanhpham.smartkidz.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.AppUser
import com.khanhpham.smartkidz.data.models.Games
import com.khanhpham.smartkidz.helpers.Status
import com.khanhpham.smartkidz.ui.LoginActivity
import com.khanhpham.smartkidz.ui.MainActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity :AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        observeLiveData()
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            if (username.length<3) {
                Toast.makeText(this, "username required at least 3 characters!",Toast.LENGTH_SHORT).show()
                etUsername.requestFocus()
            } else {
                    splashViewModel.createUser(AppUser(username = username))
                    splashViewModel.appUser.observe(this, Observer { appUser->
                        val (status, data, error, isFirst) = appUser
                        when (status){
                            Status.LOADING -> showLoading(isFirst)
                            Status.FAILED -> {
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }
                            Status.SUCCEED -> proceed(data)
                        }
                    })
                }
            }
        }

    override fun onResume() {
        super.onResume()
    }

    private fun observeLiveData(){
        splashViewModel.gameList.observe(this, Observer { allGames->
            val (status, data, error, isFirst) = allGames
            when(status){
                Status.LOADING -> showLoading(isFirst)
                Status.FAILED -> showError(error)
                Status.SUCCEED -> showListGame(data)
                else -> throw IllegalAccessException("Unexpected case...")
            }
        })
    }

    private fun showError(error: Throwable?) {
        splash_fetch_progress.visibility = View.GONE
        splash_fetch_error.visibility = View.VISIBLE
        loginBoard.visibility = View.GONE
        Log.d("splash","$error")
    }

    private fun showListGame(data: List<Games>?) {
        splash_fetch_progress.visibility = View.GONE
        splash_fetch_error.visibility = View.GONE
        loginBoard.visibility = View.GONE
        if (data != null) {
            GamesData.games.clear()
            GamesData.games.addAll(data)
        }
        observeUser()
    }

    private fun observeUser() {
        val sharedPref: SharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)
        val userId = sharedPref.getInt(getString(R.string.user_id_key),0)
        Log.d("observeUser","userId: ${userId}")
        if (userId==0){
            loginBoard.visibility = View.VISIBLE
        } else {
            splashViewModel.appUser.observe(this, Observer { appUser->
                val (status, data, error, isFirst) = appUser
                when (status){
                    Status.LOADING -> showLoading(isFirst)
                    Status.FAILED -> showCreate(error)
                    Status.SUCCEED -> proceed(data)
                }
            })
        }
    }

    private fun showCreate(error: Throwable?) {
        loginBoard.visibility = View.VISIBLE
        Log.e("loadUser","$error")
    }

    private fun proceed(data: AppUser?) {
        if (data != null) {
            GamesData.user = data
            val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                data.id?.let { putInt(getString(R.string.user_id_key), it) }
                apply()
            }
            Log.d("proceed","userId: ${sharedPref.getInt(getString(R.string.user_id_key),0)}")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(first: Boolean) {
        splash_fetch_progress.visibility = View.VISIBLE
        splash_fetch_error.visibility = View.GONE
        loginBoard.visibility = View.GONE
    }


//    fun createUser(){
//        val username = etUsername.text.toString()
//        if (username=="") {
//            etUsername.requestFocus()
//        } else {
//            if (splashViewModel.createUser(AppUser(username = username))){
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            } else {
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//            }
//        }
//    }
}