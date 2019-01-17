package com.anou.prototype.yoga.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.databinding.ActivityMainBinding
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.navigation.MainRouter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    val mainViewModel by viewModel<MainViewModel>()
    val applicationController: ApplicationController by inject()
    val mainRouter: MainRouter by inject()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: DrawerAdapter
    lateinit var appBarConfiguration: AppBarConfiguration

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

    }

    private fun bind(): View {
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .apply {
                    this.setLifecycleOwner(this@MainActivity)
                    this.mainViewModel
                }

        return binding.root
    }

    private fun setupViews(view: View) {

        setSupportActionBar(toolbar)
        val topLevelDestinations = setOf(R.id.categoryFragmentDestination, R.id.aboutFragmentDestination, R.id.textFragmentDestination)
        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations).setDrawerLayout(mainDrawerLayout).build()

        adapter = DrawerAdapter(this, inflater = LayoutInflater.from(this@MainActivity), mainRouter = mainRouter)
        binding.drawerRecyclerView.adapter = adapter

        mainViewModel.getModules().observe(this@MainActivity, Observer { modules ->
            adapter.setData(modules)

            //initialize the first module as the landing screen
            val firstModule = modules.get(0)
            mainRouter.onModuleSelected(this@MainActivity, firstModule, true)
            NavigationUI.setupActionBarWithNavController(this, Navigation.findNavController(this, R.id.mainNavigationHost), appBarConfiguration)

        })
    }

    override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(Navigation.findNavController(this, R.id.mainNavigationHost), appBarConfiguration)

    fun closeDrawer() {
        mainDrawerLayout.closeDrawers()
    }
}
