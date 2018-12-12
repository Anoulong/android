package com.anou.prototype.yoga.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.controller.ApplicationController
import com.anou.prototype.yoga.databinding.ActivityMainBinding
import com.anou.prototype.yoga.db.ModuleEntity
import com.anou.prototype.yoga.navigation.MainNavigationListener
import com.anou.prototype.yoga.ui.fragment.FaqFragment
import com.anou.prototype.yoga.ui.fragment.LoadingFragment
import com.anou.prototype.yoga.ui.fragment.LoadingFragmentDirections
import com.anou.prototype.yoga.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
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
            onModuleSelected(modules.get(0))
        })

    }

    override fun onFragmentViewed(string: String) {
        println("Log state ==> $string")
        supportActionBar?.title = string
    }

    override fun onSupportNavigateUp(): Boolean =  findNavController(this, R.id.mainNavigationHost).navigateUp()

    override fun onModuleSelected(module: ModuleEntity) {

        when (module.type) {
            ModuleEntity.FAQ ->{
                val directions = LoadingFragmentDirections.actionLoadingFragmentToFaqFragment()
                module.eid?.let {
                    directions.setModuleEid(it)
                    findNavController(this, R.id.mainNavigationHost).navigate(directions, null)
                }
            }
            ModuleEntity.ABOUT -> {
                val directions = LoadingFragmentDirections.actionLoadingFragmentToAboutFragment()
                module.eid?.let {
                    directions.setModuleEid(it)
                    findNavController(this, R.id.mainNavigationHost).navigate(directions, null)
                }
            }
            else -> Toast.makeText(this, module.title, Toast.LENGTH_SHORT).show()
        }



//        val navBuilder = NavOptions.Builder()
//        val navOptions = navBuilder.setPopUpTo(R.id.loadingFragment, true).build()
//
//        NavHostFragment.findNavController(this).navigate(R.id.faqFragment, null, navOptions)


//        val navBuilder = NavOptions.Builder()
//        val navOptions = navBuilder.setPopUpTo(R.id.loadingFragment, true).build()
//        NavHostFragment.findNavController(this).navigate(R.id.faqFragment, null, navOptions)
       Toast.makeText(this, module.title, Toast.LENGTH_SHORT).show()
    }

}
