package com.khanhpham.smartkidz.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.audiofx.BassBoost
import android.opengl.GLES10
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.SmartKidzApplication
import com.khanhpham.smartkidz.data.models.AppUser
import com.khanhpham.smartkidz.data.models.Games
import com.khanhpham.smartkidz.helpers.Response
import com.khanhpham.smartkidz.repository.SmartKidRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashViewModel : ViewModel() {
    @Inject
    lateinit var smartKidRepository: SmartKidRepository

    private val compositeDisposable = CompositeDisposable()

    private val _gameList =  MutableLiveData<Response<List<Games>>>()
    val gameList: LiveData<Response<List<Games>>>
        get() = _gameList

    private val _appUser by lazy { MutableLiveData<Response<AppUser>>() }
    val appUser: LiveData<Response<AppUser>>
        get() = _appUser

    private val _login by lazy { MutableLiveData<Response<AppUser>>() }
    val login: LiveData<Response<AppUser>> get() = _login


    init {
        SmartKidzApplication.instance.component.inject(this)
        fetchGameList()
        fetchUser(SmartKidzApplication.instance)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun refresh() {
        fetchGameList()
    }

    private fun fetchGameList() {
        compositeDisposable.add(
            smartKidRepository.getGames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _gameList.value = Response.loading(true)
                }
                .subscribe({
                    _gameList.value = Response.succeed(it, true)
                },{
                    Log.e("onError()", "Error: ${it.message}")
                    _gameList.value = Response.error(it)
                })
        )
    }

    private fun fetchUser(ctx: Context){
        val sharedPref: SharedPreferences = ctx.getSharedPreferences(
            ctx.getString(R.string.preference_file_key), AppCompatActivity.MODE_PRIVATE)
        val userId = sharedPref.getInt(ctx.getString(R.string.user_id_key),0)
        Log.d("fetchUser","userId: $userId")
        if (userId!=0)
        compositeDisposable.add(
            smartKidRepository.getAppUser(userId.toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _appUser.value = Response.loading(false)
                }
                .subscribe({
                    _appUser.value = Response.succeed(it,false)
                },{
                    Log.e("onError()", "Error: ${it.message}")
                    _appUser.value = Response.error(it)
                })
        )
    }

    //@SuppressLint("CheckResult", "CommitPrefEdits")
    fun createUser(appUser: AppUser) {
         compositeDisposable.add(
             smartKidRepository.createUser(appUser)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe({
                     _appUser.value = Response.succeed(it, false)
                 },{
                     Log.e("onError()", "Error: ${it.message}")
                     _appUser.value = Response.error(it)
                 })
         )
    }
}
