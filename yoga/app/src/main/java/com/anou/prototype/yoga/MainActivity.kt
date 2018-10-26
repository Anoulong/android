package com.anou.prototype.yoga

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import kotlinx.coroutines.experimental.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
