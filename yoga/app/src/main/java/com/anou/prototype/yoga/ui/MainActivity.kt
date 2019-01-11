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
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.databinding.ActivityMainBinding
import com.anou.prototype.core.db.ModuleEntity
import com.anou.prototype.core.db.category.CategoryEntity
import com.anou.prototype.core.db.feature.FeatureEntity
import com.anou.prototype.yoga.navigation.MainNavigationListener
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), MainNavigationListener {

    val mainViewModel by viewModel<MainViewModel>()
    val applicationController: ApplicationController by inject()
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
                    this.mainViewModel
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
        val topLevelDestinations = setOf(R.id.categoryFragment, R.id.aboutFragment, R.id.textFragment)
         appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations).setDrawerLayout(mainDrawerLayout).build()

        adapter = DrawerAdapter(this, inflater = LayoutInflater.from(this@MainActivity))
        binding.drawerRecyclerView.adapter = adapter

        mainViewModel.getModules().observe(this@MainActivity, Observer { modules ->
            adapter.setData(modules)

            val firstModule = modules.get(0)
            onModuleSelected(firstModule, true)
            NavigationUI.setupActionBarWithNavController(this, Navigation.findNavController(this, R.id.mainNavigationHost), appBarConfiguration)

        })

    }

    override fun onFragmentViewed(string: String) {
        println("Log state ==> $string")
        supportActionBar?.title = string
    }

    override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(Navigation.findNavController(this, R.id.mainNavigationHost), appBarConfiguration)

    override fun onModuleSelected(module: ModuleEntity, isLaunchModule: Boolean) {
        val navBuilder = NavOptions.Builder()
        val navOptions = if (isLaunchModule) navBuilder.setPopUpTo(R.id.loadingFragment, true).build() else null

        var bundle = Bundle()

        module.let {
            bundle.putString(Constants.MODULE_EID, module.eid)
            bundle.putString(Constants.MODULE_TITLE, module.title)

            when (module.type) {
                ModuleEntity.FAQ -> {
                    Navigation.findNavController(this, R.id.mainNavigationHost).navigate(R.id.categoryFragment, bundle, navOptions)
                }
                ModuleEntity.ABOUT -> {
                    Navigation.findNavController(this, R.id.mainNavigationHost).navigate(R.id.aboutFragment, bundle, navOptions)
                }
                ModuleEntity.TEXT_TYPE -> {
                    Navigation.findNavController(this, R.id.mainNavigationHost).navigate(R.id.textFragment, bundle, navOptions)
                }
                else -> Toast.makeText(this, module.title, Toast.LENGTH_SHORT).show()
            }
        }

        mainDrawerLayout.closeDrawers()
    }

    override fun onCategorySelected(category: CategoryEntity) {
        var bundle = Bundle()
        category.let {
            bundle.putString(Constants.CATEGORY_EID, category.eid)
            bundle.putString(Constants.CATEGORY_TITLE, category.title)

            Navigation.findNavController(this, R.id.mainNavigationHost).navigate(R.id.featureFragment, bundle)
        }
        Toast.makeText(this, category.title, Toast.LENGTH_SHORT).show()
    }

    override fun onFeatureSelected(featureEntity: FeatureEntity) {
        Toast.makeText(this, featureEntity.eid, Toast.LENGTH_SHORT).show()
    }


}
