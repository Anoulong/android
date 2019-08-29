package com.anou.prototype.yoga.ui.fragment.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseFragment

class ArticleFragment : BaseFragment() {
    override val fragmentTag: String
        get() = TAG

    private lateinit var homeViewModel: ArticleViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_article, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    companion object{
        val TAG = ArticleFragment::class.java.simpleName
    }
}