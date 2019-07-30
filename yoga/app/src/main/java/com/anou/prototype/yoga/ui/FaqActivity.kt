package com.anou.prototype.yoga.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.databinding.ActivityAboutBinding
import com.anou.prototype.yoga.navigation.MainRouter
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaqActivity : BaseActivity() {
    override val fragmentManager: FragmentManager
        get() = this@FaqActivity.supportFragmentManager

    override val fragmentContainer: Int
        get() = R.id.faqContainer

    val mainViewModel by viewModel<MainViewModel>()
    val applicationController: ApplicationController by inject()
    val mainRouter: MainRouter by inject()
    lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = inflater.inflate(R.layout.activity_faq, null, false)
        drawerLayout.addView(contentView, 0)
    }

    override fun onResume() {
        super.onResume()
        val errorChannel = applicationController.receiveErrorChannel()

        activityScope.launch {
            val errorMessage = errorChannel.receive()
            Toast.makeText(this@FaqActivity, errorMessage, Toast.LENGTH_LONG).show()
        }
    }


}