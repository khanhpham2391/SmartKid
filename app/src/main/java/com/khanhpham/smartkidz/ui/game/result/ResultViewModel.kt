package com.khanhpham.smartkidz.ui.game.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khanhpham.smartkidz.SmartKidzApplication
import com.khanhpham.smartkidz.data.models.History
import com.khanhpham.smartkidz.helpers.Response
import com.khanhpham.smartkidz.repository.SmartKidRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ResultViewModel(var history: History): ViewModel() {
    @Inject
    lateinit var repository: SmartKidRepository

    private val compositeDisposable = CompositeDisposable()

    private val _historyItem = MutableLiveData<Response<History>>()
    val historyItem: LiveData<Response<History>>
        get() = _historyItem

    init {
        SmartKidzApplication.instance.component.inject(this)
        createHistory()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun createHistory() {
        compositeDisposable.add(
            repository.createHistory(history)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _historyItem.value = Response.succeed(it, true)
                },{
                    _historyItem.value = Response.error(it)
                })
        )
    }
}