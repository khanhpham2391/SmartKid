package com.khanhpham.smartkidz.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.Gender
import com.khanhpham.smartkidz.ui.MainActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        if (GamesData.user.photo!=null){
            Glide.with(this).load(GamesData.urlConvert(GamesData.user.photo)).into(profileImage)
        }
        profileName.text = "Nick Name: ${GamesData.user.username}"
        profileFullname.text = "Name: ${checkNull(GamesData.user.fullname,"No name")}"
        profileGender.text = "Gender: ${checkNull(GamesData.user.gender,"Female")}"
        profileLevel.text = "Level: ${GamesData.user.levelId?.name}"
        if (GamesData.user.levelId!=null){
            Glide.with(this).load(GamesData.urlConvert(GamesData.user.levelId!!.icon)).into(profileLevelIcon)
        }
        var totalScore = 0
        GamesData.user.scoreCollection?.forEach { score ->
            totalScore += score.score
        }
        profilePoint.text = "Score: $totalScore"

        btnProfileBack.setOnClickListener(back)

    }

    private fun checkNull(check: String?,placeholder: String): String{
        return if (check.isNullOrEmpty()){
            placeholder
        } else {
            check
        }
    }

    private val back = View.OnClickListener {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}