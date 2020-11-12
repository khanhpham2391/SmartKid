package com.khanhpham.smartkidz.ui.profile.editProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.khanhpham.smartkidz.R
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.AppUser
import com.khanhpham.smartkidz.helpers.Status
import com.khanhpham.smartkidz.ui.MainActivity
import com.khanhpham.smartkidz.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    lateinit var editViewModel: EditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        ilUsername.editText?.setText(GamesData.user.username)
        ilFullName.editText?.setText(GamesData.user.fullName)
        ilEmail.editText?.setText(GamesData.user.email)
        btnGender.isChecked = GamesData.user.gender.equals("male")

        btnEditBack.setOnClickListener(back)
        btnEditUpdate.setOnClickListener(update)
    }

    private val back = View.OnClickListener {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private val update = View.OnClickListener {
        if (ilUsername.editText?.text.isNullOrEmpty()) {
            Snackbar.make(ilUsername, "Please fill username", Snackbar.LENGTH_SHORT)
                .setAction("Enter") {
                    ilUsername.requestFocus()
                }.show()
        } else if (ilPassword.editText?.text.isNullOrEmpty()) {
            Snackbar.make(ilUsername, "Please fill password", Snackbar.LENGTH_SHORT)
                .setAction("Enter") {
                    ilPassword.requestFocus()
                }.show()
        } else if (ilEmail.editText?.text.isNullOrEmpty()) {
            Snackbar.make(ilUsername, "Please fill email", Snackbar.LENGTH_SHORT)
                .setAction("Enter") {
                    ilEmail.requestFocus()
                }.show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(ilEmail.editText?.text.toString()).matches()) {
            Snackbar.make(ilUsername, "Please enter valid email address", Snackbar.LENGTH_SHORT)
                .setAction("Enter") {
                    ilEmail.editText?.text?.clear()
                    ilEmail.requestFocus()
                }.show()
        } else if (ilFullName.editText?.text.isNullOrEmpty()) {
            Snackbar.make(ilUsername, "Please fill fullname", Snackbar.LENGTH_SHORT)
                .setAction("Enter") {
                    ilFullName.requestFocus()
                }.show()
        } else {
            GamesData.user.username = ilUsername.editText?.text.toString()
            GamesData.user.password = ilPassword.editText?.text.toString()
            GamesData.user.email = ilEmail.editText?.text.toString()
            GamesData.user.fullName = ilFullName.editText?.text.toString()
            if (btnGender.isChecked){
                GamesData.user.gender = "male"
            } else {
                GamesData.user.gender = "female"
            }
            Log.d("user in EditActivity","${GamesData.user}")
            editViewModel = EditViewModel(GamesData.user)
            editViewModel.updatedUser.observe(this,{
                val (status, data, error, isFirst) = it
                when(status){
                    Status.SUCCEED -> showData(data)
                    Status.LOADING -> showLoading(isFirst)
                    Status.FAILED -> showError(error)
                }
            })
        }
    }

    private fun showError(error: Throwable?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Log.d("err","$error")
    }

    private fun showLoading(first: Boolean) {
        TODO("Not yet implemented")
    }

    private fun showData(data: AppUser?) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
}