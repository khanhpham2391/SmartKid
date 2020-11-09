package com.khanhpham.smartkidz.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khanhpham.smartkidz.repository.SmartKidRepository
import java.util.*
import javax.inject.Inject

data class History (val id: Int?, val userId: Int?, val gameId: Int, val score: Int, var timestamp: String?, val gameName:String?)

