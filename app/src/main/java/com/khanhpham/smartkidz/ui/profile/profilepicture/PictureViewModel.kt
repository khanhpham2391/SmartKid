package com.khanhpham.smartkidz.ui.profile.profilepicture

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.khanhpham.smartkidz.SmartKidzApplication
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.IconImage
import com.khanhpham.smartkidz.helpers.Response
import com.khanhpham.smartkidz.repository.SmartKidRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PictureViewModel(): ViewModel() {
    @Inject
    lateinit var repository: SmartKidRepository

    private val compositeDisposable = CompositeDisposable()

    private val _imageSource = MutableLiveData<Response<List<IconImage>>>()
    val imageSource: LiveData<Response<List<IconImage>>>
        get() = _imageSource

    init {
        SmartKidzApplication.instance.component.inject(this)
        fetchPicture()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun fetchPicture() {
        compositeDisposable.add(
            repository.getIcon()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _imageSource.value = Response.succeed(it, true)
                },{
                    _imageSource.value = Response.error(it)
                })
        )
    }

    companion object{
        @JvmStatic
        @BindingAdapter("imageUrl", "placeholder")
        fun loadPictureImage(view: ImageView, imageUrl: String?, placeholder: Drawable){
            if (!imageUrl.isNullOrEmpty()){
                Glide.with(view.context).load(GamesData.urlConvert(imageUrl)).into(view)
            } else {
                view.setImageDrawable(placeholder)
            }
        }
    }
}