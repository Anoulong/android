package com.anou.prototype.yoga.ui.fragment.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.utils.Constants
import kotlinx.android.synthetic.main.news_article_details_fragment.*
import org.koin.android.ext.android.inject

class NewsArticleDetailsFragment : BaseFragment() {
    override val fragmentTag: String
        get() = TAG
    val mainRouter: MainRouter by inject()
    var title: String = TAG


    companion object {
        val TAG = NewsArticleDetailsFragment::class.java.simpleName

        // TODO: Customize parameter argument names
        const val NEWS_ARTICLE_DETAILS_TITLE = "NEWS_ARTICLE_DETAILS_TITLE"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(title: String) =
                NewsArticleDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString(NEWS_ARTICLE_DETAILS_TITLE, title)
                    }
                }
    }

    private lateinit var viewModel: NewsArticleDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.news_article_details_fragment, container, false)

        val textView: TextView = root.findViewById(R.id.news_article_details_title)

        arguments?.let { bundle ->

            bundle.get(NEWS_ARTICLE_DETAILS_TITLE)?.let { title ->
                mainRouter.onFragmentViewed(activity as BaseActivity, NewsArticleDetailsViewModel::class.java.simpleName)
                textView.text = title.toString()
            }
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsArticleDetailsViewModel::class.java)

    }

}
