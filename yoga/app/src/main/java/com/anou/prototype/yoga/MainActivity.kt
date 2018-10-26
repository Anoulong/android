package com.anou.prototype.yoga

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.viewmodel.MainViewModel
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
     val mainViewModel : MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launch(UI) {
            mainViewModel.getModules()
        }
    }
}
