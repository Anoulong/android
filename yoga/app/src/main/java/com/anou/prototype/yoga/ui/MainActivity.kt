package com.anou.prototype.yoga.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.R.id.toggle
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.controller.ApplicationController
import com.anou.prototype.yoga.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    val mainViewModel by viewModel<MainViewModel>()
    val applicationController: ApplicationController by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val errorChannel = applicationController.receiveErrorChannel()

        activityScope.launch() {
            val errorMessage = errorChannel.receive()
            Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
        }

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.setDrawerIndicatorEnabled(true)

        val drawerAdapter = DrawerAdapter()
        drawerRecyclerView.setAdapter(drawerAdapter)

        mainViewModel.getModules().observe(this@MainActivity, Observer { modules ->
            toolbar.setTitle("modules count = ${modules?.size}")
            drawerAdapter.setData(modules)
        })

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
