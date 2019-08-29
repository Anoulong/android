package com.anou.prototype.yoga.ui.fragment.rss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseFragment

class RssFragment : BaseFragment() {
    override val fragmentTag: String
        get() = TAG

    private lateinit var dashboardViewModel: RssViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(RssViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_rss, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    companion object{
        val TAG = RssFragment::class.java.simpleName
    }
}