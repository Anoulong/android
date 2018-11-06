package com.anou.prototype.yoga.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.controller.ApplicationController
import com.anou.prototype.yoga.databinding.ActivityMainBinding
import com.anou.prototype.yoga.service.NetworkConnectivityService
import com.anou.prototype.yoga.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    val mainViewModel by viewModel<MainViewModel>()
    val applicationController: ApplicationController by inject()
    val networkConnectivityService: NetworkConnectivityService by inject()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter:DrawerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = bind()
        setupViews(view)
    }

    override fun onResume() {
        super.onResume()
        val errorChannel = applicationController.receiveErrorChannel()

        activityScope.launch() {
            val errorMessage = errorChannel.receive()
            Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
        }

        val connectionTypeChannel = networkConnectivityService.receiveConnectionTypeChannel()

        activityScope.launch() {
            val errorMessage =  connectionTypeChannel.receive()
            Toast.makeText(this@MainActivity, errorMessage.name, Toast.LENGTH_LONG).show()
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun bind(): View {
        binding  = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .apply {
                    this.setLifecycleOwner(this@MainActivity)
                    this.viewModel
                }

        return binding.root
    }

    private fun setupViews(view: View) {

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.setDrawerIndicatorEnabled(true)

        adapter = DrawerAdapter(this, inflater = LayoutInflater.from(this@MainActivity))
        binding.drawerRecyclerView.adapter = adapter


        mainViewModel.getModules().observe(this@MainActivity, Observer { modules ->
            //            toolbar.setTitle("modules count = ${modules?.size}")
            adapter.setData(modules)
        })

    }
}
