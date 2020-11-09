package com.khanhpham.smartkidz.ui.game.gamePlay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khanhpham.smartkidz.SmartKidzApplication
import com.khanhpham.smartkidz.data.models.GameData
import com.khanhpham.smartkidz.data.models.GameDetails
import com.khanhpham.smartkidz.helpers.Response
import com.khanhpham.smartkidz.repository.SmartKidRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PlayViewModel(private val gameData: GameData): ViewModel() {
    @Inject
    lateinit var smartKidRepository: SmartKidRepository

    private val compositeDisposable = CompositeDisposable()

    private val _gameDataList =  MutableLiveData<Response<List<GameDetails>>>()
    val gameDataList: LiveData<Response<List<GameDetails>>>
        get() = _gameDataList

    init {
        SmartKidzApplication.instance.component.inject(this)
        fetchGameDataList()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun fetchGameDataList() {
        compositeDisposable.add(
            smartKidRepository.getGamePlay(gameData.game,gameData.topic,gameData.diff)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _gameDataList.value = Response.succeed(it,true)
                },{
                    _gameDataList.value = Response.error(it)
                })
        )
    }

}