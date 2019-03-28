package com.anou.prototype.yoga.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.anou.prototype.core.controller.ApplicationController
import com.anou.prototype.core.service.NetworkConnectivityService
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.databinding.ActivityMainBinding
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.fragment.SideMenuAdapter
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    val applicationController: ApplicationController by inject()
    val networkConnectivityService: NetworkConnectivityService by inject()
    lateinit var binding: ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    private var infoSnackBar: Snackbar? = null

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

        addDisposable(networkConnectivityService.getConnectionTypeObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ networkStatus ->
                    if (networkStatus == NetworkConnectivityService.ConnectionType.TYPE_NO_INTERNET) {
                        infoSnackBar = Snackbar.make(
                                this@MainActivity.findViewById(R.id.mainCoordinatorLayout),
                                "Offline",
                                Snackbar.LENGTH_LONG
                        )
//                                infoSnackBar = SnackbarUtils.buildTopSnackbar(this@MainActivity, R.id.mainCoordinatorLayout,
//                                SnackbarUtils.Type.info, getString(R.string.msg_no_connection_title))
//                                .setAction(getString(R.string.settings)) { view -> startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS)) }
                        infoSnackBar?.let { snackbar ->

                            if (!snackbar.isShownOrQueued) {
                                snackbar.show()
                            }
                        }
                    } else {
                        infoSnackBar?.let { snackbar ->
                            snackbar.dismiss()
                        }
                    }
                }, { error -> Log.d(TAG, "appViewModel.getRemoteApp(): " + error.message) }, { }))

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
