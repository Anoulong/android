package com.anou.prototype.yoga.ui.fragment.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.NewsActivity
import com.anou.prototype.yoga.ui.fragment.article.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_article.*
import org.koin.android.ext.android.inject

class ArticleFragment : BaseFragment() {
    override val fragmentTag: String
        get() = TAG
    val mainRouter: MainRouter by inject()
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
            mainRouter.onFragmentViewed(activity as BaseActivity, it)
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     val newsArticleItemRecyclerViewAdapter  =  NewsArticleItemRecyclerViewAdapter(DummyContent.ITEMS, activity as NewsActivity)
        newsArticleRecyclerView.adapter = newsArticleItemRecyclerViewAdapter
    }

    companion object{
        val TAG = ArticleFragment::class.java.simpleName
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnNewsArticleItemListener {
        fun onArticleSelected(item: DummyContent.DummyItem?)
    }
}