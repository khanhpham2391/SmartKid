package com.khanhpham.smartkidz.ui.profile.editProfile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khanhpham.smartkidz.SmartKidzApplication
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.AppUser
import com.khanhpham.smartkidz.helpers.Response
import com.khanhpham.smartkidz.repository.SmartKidRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditViewModel(val appUser: AppUser): ViewModel() {
    @Inject
    lateinit var repository: SmartKidRepository

    private val compositeDisposable = CompositeDisposable()

    private val _updatedUser = MutableLiveData<Response<AppUser>>()
    val updatedUser: LiveData<Response<AppUser>>
        get() = _updatedUser

    init {
        SmartKidzApplication.instance.component.inject(this)
        Log.d("user in EditActivity","${GamesData.user}")
        createHistory()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun createHistory() {
        compositeDisposable.add(
            repository.createUser(appUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _updatedUser.value = Response.succeed(it, true)
                },{
                    _updatedUser.value = Response.error(it)
                })
        )
    }
}