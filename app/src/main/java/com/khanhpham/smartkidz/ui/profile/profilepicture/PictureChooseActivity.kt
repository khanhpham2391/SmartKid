package com.khanhpham.smartkidz.ui.profile.profilepicture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.SmartKidzApplication
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.AppUser
import com.khanhpham.smartkidz.data.models.Games
import com.khanhpham.smartkidz.data.models.IconImage
import com.khanhpham.smartkidz.helpers.Response
import com.khanhpham.smartkidz.helpers.Status
import com.khanhpham.smartkidz.repository.SmartKidRepository
import com.khanhpham.smartkidz.ui.game.GameAdapter
import com.khanhpham.smartkidz.ui.profile.ProfileActivity
import com.khanhpham.smartkidz.ui.profile.editProfile.EditViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_picture_choose.*
import javax.inject.Inject


class PictureChooseActivity : AppCompatActivity() {

    @Inject
    lateinit var pictureAdapter: PictureAdapter

    private val viewModel: PictureViewModel by viewModels()
    lateinit var editViewModel: EditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_choose)

        SmartKidzApplication.instance.component.inject(this)

        viewPagerManager()
        observePicture()
        btnSelect.setOnClickListener(choose)
    }

    private val choose = View.OnClickListener {
        GamesData.user.photo = pictureAdapter.imageList[pictureViewPager.currentItem].url
        GamesData.user.password = null
        Log.d("updatePicture","${GamesData.user}")
        editViewModel = EditViewModel(GamesData.user)
        observeUser()
    }

    private fun observeUser() {
        editViewModel.updatedUser.observe(this,{
            val (status, data, error, isFirst) = it
            when(status){
                Status.SUCCEED -> showUser(data)
                Status.LOADING -> showLoading(isFirst)
                Status.FAILED -> showError(error)
            }
        })
    }

    private fun showUser(data: AppUser?) {
        val intent = Intent(this,ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun viewPagerManager() {
        findViewById<ViewPager2>(R.id.pictureViewPager).apply {
            // Set offscreen page limit to at least 1, so adjacent pages are always laid out
            offscreenPageLimit = 1
            setPageTransformer(ZoomOut())
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = resources.getDimensionPixelOffset(R.dimen.fullMargin) +
                        resources.getDimensionPixelOffset(R.dimen.fullMargin)
                // setting padding on inner RecyclerView puts overscroll effect in the right place
                // TODO: expose in later versions not to rely on getChildAt(0) which might break
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
            adapter = pictureAdapter
        }
    }

    private fun observePicture(){
        viewModel.imageSource.observe(this,{
            val (status, data, error, isFirst) = it
            when(status){
                Status.FAILED -> showError(error)
                Status.LOADING -> showLoading(isFirst)
                Status.SUCCEED -> showPicture(data)
            }
        })
    }

    private fun showPicture(data: List<IconImage>?) {
        pictureError.visibility = View.GONE
        pictureLoading.visibility = View.GONE
        pictureViewPager.visibility = View.VISIBLE
        pictureAdapter.setUpPicture(data as ArrayList<IconImage>)
    }

    private fun showLoading(first: Boolean) {
        if (first){
            pictureError.visibility = View.GONE
            pictureLoading.visibility = View.VISIBLE
            pictureViewPager.visibility = View.GONE
        }
    }

    private fun showError(error: Throwable?) {
        pictureError.visibility = View.VISIBLE
        pictureLoading.visibility = View.GONE
        pictureViewPager.visibility = View.GONE
        Log.d("picture","$error")
    }
}