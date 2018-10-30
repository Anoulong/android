package com.anou.prototype.yoga

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.controller.ApplicationController
import com.anou.prototype.yoga.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    val mainViewModel by viewModel<MainViewModel>()
    val applicationController: ApplicationController by inject()
    val dispatchers: AppCoroutineDispatchers by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel.getModules().observe(this@MainActivity, Observer { modules ->
            mainTextView.setText("modules count = ${modules?.size}")
        })
       val errorChannel  =  applicationController.receiveErrorChannel()

        GlobalScope.launch(dispatchers.computation, CoroutineStart.DEFAULT) {
           val errorMessage =  errorChannel.receive()

            withContext(dispatchers.main){
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()

            }

        }


    }
}
