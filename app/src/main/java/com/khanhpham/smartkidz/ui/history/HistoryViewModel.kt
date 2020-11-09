package com.khanhpham.smartkidz.ui.history

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

class HistoryViewModel: ViewModel() {
    @Inject
    lateinit var repository: SmartKidRepository

    private val compositeDisposable = CompositeDisposable()

    private val _historyCardItem = MutableLiveData<Response<List<History>>>()
    val historyCardItem: LiveData<Response<List<History>>>
        get() = _historyCardItem

    init {
        SmartKidzApplication.instance.component.inject(this)
        loadHistory()
    }

    private fun loadHistory() {
        compositeDisposable.add(
            repository.getHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _historyCardItem.value = Response.succeed(it,true)
                },{
                    _historyCardItem.value = Response.error(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}