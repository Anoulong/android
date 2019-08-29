package com.anou.prototype.yoga.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.ui.fragment.article.ArticleFragment
import com.anou.prototype.yoga.ui.fragment.article.NewsArticleDetailsFragment
import com.anou.prototype.yoga.ui.fragment.article.dummy.DummyContent
import com.anou.prototype.yoga.ui.fragment.favorite.FavoriteFragment
import com.anou.prototype.yoga.ui.fragment.rss.RssFragment
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : BaseActivity(), ArticleFragment.OnNewsArticleItemListener{
    override fun onArticleSelected(item: DummyContent.DummyItem?) {
        replaceFragment(NewsArticleDetailsFragment.newInstance(item?.content ?: String()))
    }


    val homeFragment: BaseFragment = ArticleFragment()
    val dashboardFragment: BaseFragment = RssFragment()
    val notificationsFragment: BaseFragment = FavoriteFragment()
    var active = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = inflater.inflate(R.layout.activity_news, null, false)
        drawerLayout.addView(contentView, 0)


        newsBottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_news_article -> {
                    replaceFragment(homeFragment)
                }
                R.id.navigation_news_rss -> {
                    replaceFragment(dashboardFragment)
                }
                R.id.navigation_news_favorite -> {
                    replaceFragment(notificationsFragment)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: BaseFragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.news_host_fragment, fragment)
                .commit()
    }
}