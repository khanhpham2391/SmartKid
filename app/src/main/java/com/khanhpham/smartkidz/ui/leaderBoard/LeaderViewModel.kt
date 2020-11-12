package com.khanhpham.smartkidz.ui.leaderBoard

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.khanhpham.smartkidz.SmartKidzApplication
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.AppUser
import com.khanhpham.smartkidz.data.models.IconImage
import com.khanhpham.smartkidz.helpers.Response
import com.khanhpham.smartkidz.repository.SmartKidRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LeaderViewModel: ViewModel() {
    @Inject
    lateinit var repository: SmartKidRepository

    private val compositeDisposable = CompositeDisposable()

    private val _userSource = MutableLiveData<Response<Map<Int,AppUser>>>()
    val userSource: LiveData<Response<Map<Int,AppUser>>>
        get() = _userSource

    init {
        SmartKidzApplication.instance.component.inject(this)
        fetchUsers()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun fetchUsers() {
        compositeDisposable.add(
            repository.getPosition(GamesData.user.id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _userSource.value = Response.loading(true)
                }
                .subscribe({
                    _userSource.value = Response.succeed(it, true)
                },{
                    _userSource.value = Response.error(it)
                })
        )
    }

    companion object{
        @JvmStatic
        @BindingAdapter("imageUrl", "placeholder")
        fun loadLeaderImage(view: ImageView, imageUrl: String?, placeholder: Drawable){
            if (!imageUrl.isNullOrEmpty()){
                Glide.with(view.context).load(GamesData.urlConvert(imageUrl)).into(view)
            } else {
                view.setImageDrawable(placeholder)
            }
        }
    }
}