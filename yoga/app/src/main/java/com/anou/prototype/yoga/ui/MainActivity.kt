package com.anou.prototype.yoga.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.controller.ApplicationController
import com.anou.prototype.yoga.databinding.ActivityMainBinding
import com.anou.prototype.yoga.db.ModuleEntity
import com.anou.prototype.yoga.navigation.MainNavigationListener
import com.anou.prototype.yoga.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), MainNavigationListener {

    val mainViewModel by viewModel<MainViewModel>()
    val applicationController: ApplicationController by inject()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: DrawerAdapter

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

    override fun onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun bind(): View {
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .apply {
                    this.setLifecycleOwner(this@MainActivity)
                    this.viewModel
                }

        return binding.root
    }

    private fun setupViews(view: View) {

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, mainDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.setDrawerIndicatorEnabled(true)

        adapter = DrawerAdapter(this, inflater = LayoutInflater.from(this@MainActivity))
        binding.drawerRecyclerView.adapter = adapter

        mainViewModel.getModules().observe(this@MainActivity, Observer { modules ->
            adapter.setData(modules)

            val firstModule = modules.get(0)

            when (firstModule.type) {
                ModuleEntity.FAQ -> {
                    firstModule.eid?.let {
                        Navigation.findNavController(this, R.id.mainNavigationHost).graph.startDestination = R.id.faqFragment
                    }
                }
                ModuleEntity.ABOUT -> {
                    firstModule.eid?.let {
                        Navigation.findNavController(this, R.id.mainNavigationHost).graph.startDestination = R.id.aboutFragment
                    }
                }
                else -> Toast.makeText(this, firstModule.title, Toast.LENGTH_SHORT).show()
            }
            onModuleSelected(firstModule, true)

            NavigationUI.setupWithNavController(toolbar, Navigation.findNavController(this, R.id.mainNavigationHost), mainDrawerLayout)
        })

    }

    override fun onFragmentViewed(string: String) {
        println("Log state ==> $string")
        supportActionBar?.title = string
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(this, R.id.mainNavigationHost).navigateUp()

    override fun onModuleSelected(module: ModuleEntity, isLaunchModule: Boolean) {
        val navBuilder = NavOptions.Builder()
        val navOptions = if(isLaunchModule) navBuilder.setPopUpTo(R.id.loadingFragment, true).build() else null
        var bundle = Bundle()
        when (module.type) {
            ModuleEntity.FAQ -> {
                module.eid?.let {
                    bundle.putString("moduleEid", it)
                    Navigation.findNavController(this, R.id.mainNavigationHost).navigate(R.id.faqFragment, bundle, navOptions)
                }
            }
            ModuleEntity.ABOUT -> {
                module.eid?.let {
                    bundle.putString("moduleEid", it)
                    Navigation.findNavController(this, R.id.mainNavigationHost).navigate(R.id.aboutFragment, bundle, navOptions)
                }
            }
            else -> Toast.makeText(this, module.title, Toast.LENGTH_SHORT).show()
        }

        mainDrawerLayout.closeDrawers()
    }

}
