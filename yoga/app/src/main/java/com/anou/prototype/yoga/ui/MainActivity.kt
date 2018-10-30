package com.anou.prototype.yoga.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anou.prototype.yoga.R
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
        mainViewModel.getModules().observe(this@MainActivity, Observer { modules ->
            mainTextView.setText("modules count = ${modules?.size}")
        })

        val errorChannel = applicationController.receiveErrorChannel()

        activityScope.launch() {
            val errorMessage = errorChannel.receive()
            Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}
