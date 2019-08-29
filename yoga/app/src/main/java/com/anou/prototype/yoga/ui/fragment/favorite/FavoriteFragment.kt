package com.anou.prototype.yoga.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.navigation.MainRouter
import kotlinx.android.synthetic.main.activity_base.*
import org.koin.android.ext.android.inject

class FavoriteFragment : BaseFragment() {
    override val fragmentTag: String
        get() = TAG

    private lateinit var notificationsViewModel: FavoriteViewModel
    val mainRouter: MainRouter by inject()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        notificationsViewModel.text.observe(this, Observer {
            mainRouter.onFragmentViewed(activity as BaseActivity, it)
        })
        return root
    }

    companion object{
         val TAG = FavoriteFragment::class.java.simpleName
    }
}