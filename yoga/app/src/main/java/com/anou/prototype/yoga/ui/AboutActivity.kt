package com.anou.prototype.yoga.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.databinding.ActivityAboutBinding
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.fragment.AboutFragment
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutActivity : BaseActivity() {
    override val fragmentManager: FragmentManager
        get() = this@AboutActivity.supportFragmentManager
    override val fragmentContainer: Int
        get() = R.id.aboutContainer

    val mainViewModel by viewModel<MainViewModel>()
    val applicationController: ApplicationController by inject()
    val mainRouter: MainRouter by inject()
    lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = inflater.inflate(R.layout.activity_about, null, false)
        drawerLayout.addView(contentView, 0)
    }

    override fun onResume() {
        super.onResume()
        val errorChannel = applicationController.receiveErrorChannel()

        activityScope.launch() {
            val errorMessage = errorChannel.receive()
            Toast.makeText(this@AboutActivity, errorMessage, Toast.LENGTH_LONG).show()
        }

        replaceFragment(AboutFragment.newInstance("test", "About title"), true, true)

    }
}
