package com.khanhpham.smartkidz.data

import android.content.Context
import android.content.res.Resources
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.SmartKidzApplication
import com.khanhpham.smartkidz.data.models.AppUser
import com.khanhpham.smartkidz.data.models.Games

object GamesData{
    val games = mutableListOf<Games>()
    lateinit var user: AppUser

    @JvmStatic
    fun urlConvert (url: String?): String? {
        if (url != null) {
            return url.replace("localhost", SmartKidzApplication.instance.getString(R.string.localhost))
        }
        return null
    }
}

const val EXTRA_GAME = "game"
const val EXTRA_POINT = "correctAnswer"
const val FALSE = "error"

