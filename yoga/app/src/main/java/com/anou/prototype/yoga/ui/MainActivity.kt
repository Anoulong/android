package com.anou.prototype.yoga.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.databinding.ActivityMainBinding
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.fragment.SideMenuAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    val applicationController: ApplicationController by inject()
    lateinit var binding: ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()

        setSupportActionBar(toolbar)
        val topLevelDestinations = setOf(R.id.categoryFragmentDestination, R.id.aboutFragmentDestination, R.id.textFragmentDestination)
        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations).setDrawerLayout(mainDrawerLayout).build()
        navController = Navigation.findNavController(this, R.id.mainNavigationHost)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onResume() {
        super.onResume()
        val errorChannel = applicationController.receiveErrorChannel()

        activityScope.launch() {
            val errorMessage = errorChannel.receive()
            Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun bind(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        return binding.root
    }

    override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(navController, appBarConfiguration)

    fun closeDrawer() {
        mainDrawerLayout.closeDrawers()
    }
}
