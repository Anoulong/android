package com.anou.prototype.yoga.ui

import android.content.Intent
import android.os.Bundle
import androidx.navigation.ActivityNavigator
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.databinding.ActivityMainBinding
import com.anou.prototype.core.viewmodel.MainViewModel

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityNavigator(this).navigate(ActivityNavigator(this).createDestination()
                .setIntent(Intent(this, MainActivity::class.java)), null, null, null)
        finish()
    }
}
