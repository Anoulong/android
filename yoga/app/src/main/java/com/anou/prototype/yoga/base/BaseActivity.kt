package com.anou.prototype.yoga.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.lifecycle.CoroutineLifecycleObserver
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseActivity : AppCompatActivity() {
    protected val activityLifecycle = CoroutineLifecycleObserver()
    protected val activityScope: CoroutineScope = CoroutineScope(Dispatchers.Main + activityLifecycle.job)

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add observer so any jobs are automatically cancelled
        lifecycle.addObserver(activityLifecycle)
        Log.d(BaseActivity::class.java.simpleName, "onCreate")

        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

     fun replaceFragment(fragment: BaseFragment, addToBackStack: Boolean, animate: Boolean) {
        if (fragmentManager.findFragmentByTag(fragment.fragmentTag) == null) {

            val transaction = fragmentManager.beginTransaction()
            if (animate && fragmentManager.backStackEntryCount > 0) { //prevent playing animation for the first fragment
//                transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit, R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit)
            }
            transaction.replace(fragmentContainer, fragment, fragment.fragmentTag)
            Log.d(BaseActivity::class.java.simpleName, "Navigate to " + fragment.fragmentTag)

            if (addToBackStack) {
                transaction.addToBackStack(fragment.fragmentTag)
            }
            transaction.commit()

        } else {
            fragmentManager.popBackStackImmediate(fragment.fragmentTag, 0)
        }
    }

    abstract val fragmentContainer : Int
    abstract val fragmentManager : FragmentManager

}