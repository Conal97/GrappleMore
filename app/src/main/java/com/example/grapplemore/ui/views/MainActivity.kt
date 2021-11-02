package com.example.grapplemore.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grapplemore.R
import com.example.grapplemore.data.model.daos.UserDao
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_fragment)
        Timber.d("userdao: " + userDao.hashCode())
    }
}