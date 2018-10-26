package com.anou.prototype.yoga

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anou.prototype.yoga.common.AppCoroutineDispatchers
import com.anou.prototype.yoga.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    val mainViewModel by viewModel<MainViewModel>()
//     val dispatchers : AppCoroutineDispatchers by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        GlobalScope.launch(dispatchers.main, CoroutineStart.LAZY) {
            mainViewModel.getModules().observe(this@MainActivity, Observer { modules ->
//                    Toast.makeText(this@MainActivity, "modules count = ${resource.data?.size}", Toast.LENGTH_LONG).show()
                mainTextView.setText("modules count = ${modules?.size}")
            })

//        }
    }
}
