package com.khanhpham.smartkidz.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.ui.MainActivity
import com.khanhpham.smartkidz.ui.profile.editProfile.EditActivity
import com.khanhpham.smartkidz.ui.profile.profilepicture.PictureChooseActivity
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
        profileFullname.text = "Name: ${checkNull(GamesData.user.fullName, "No name")}"
        profileGender.text = "Gender: ${checkNull(GamesData.user.gender, "Female")}"
        profileLevel.text = "Level: ${GamesData.user.levelId?.name}"
        if (GamesData.user.levelId!=null){
            Glide.with(this).load(GamesData.urlConvert(GamesData.user.levelId!!.icon)).into(
                profileLevelIcon
            )
        }
        var totalScore = 0
        GamesData.user.scoreCollection?.forEach { score ->
            totalScore += score.score
        }
        profilePoint.text = "Score: $totalScore"

        btnProfileBack.setOnClickListener(back)
        btnProfileCommunity.setOnClickListener(facebookConnect)
        btnChangePhoto.setOnClickListener(loadImage)
        btnProfileRegister.setOnClickListener(editProfile)
        btnEdit.setOnClickListener(editProfile)
    }

    private val editProfile = View.OnClickListener {
        val intent = Intent(this,EditActivity::class.java)
        startActivity(intent)
    }

    private val loadImage = View.OnClickListener {
        val intent = Intent(this,PictureChooseActivity::class.java)
        startActivity(intent)
    }

    private val facebookConnect= View.OnClickListener {
        val intent = newFacebookIntent(packageManager,"https://www.facebook.com/SmartKidz-V%C3%A0-Nh%E1%BB%AFng-Ng%C6%B0%E1%BB%9Di-B%E1%BA%A1n-105731851351722")
        startActivity(intent)
    }

    private fun checkNull(check: String?, placeholder: String): String{
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

    fun newFacebookIntent(pm: PackageManager, url: String): Intent? {
        var uri: Uri = Uri.parse(url)
        try {
            val applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0)
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=$url")
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        return Intent(Intent.ACTION_VIEW, uri)
    }
}