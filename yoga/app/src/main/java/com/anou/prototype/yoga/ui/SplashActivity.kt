package com.anou.prototype.yoga.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.ActivityNavigator
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.controller.ApplicationController
import com.anou.prototype.yoga.databinding.ActivityMainBinding
import com.anou.prototype.yoga.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityNavigator(this).navigate(ActivityNavigator(this).createDestination()
                .setIntent(Intent(this, MainActivity::class.java)), null, null, null)
        finish()
    }
}
