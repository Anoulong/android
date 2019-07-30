package com.anou.prototype.yoga.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.AboutActivity
import com.anou.prototype.yoga.utils.Constants
import kotlinx.android.synthetic.main.fragment_about.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class AboutFragment : BaseFragment() {
    override val fragmentTag: String
        get() = AboutFragment::class.java.simpleName

    val mainViewModel by viewModel<MainViewModel>()
    val mainRouter: MainRouter by inject()
    lateinit var moduleEid: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        arguments?.let { bundle ->
            bundle.get(Constants.MODULE_EID)?.let { eid ->
                moduleEid = eid.toString()
            }
            bundle.get(Constants.MODULE_TITLE)?.let { title ->
                mainRouter.onFragmentViewed(activity as BaseActivity, title.toString())
            }
        }
        return inflater.inflate(R.layout.fragment_about, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewTitleAbout?.text = moduleEid
        textViewTitleAbout.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as AboutActivity).replaceFragment(TextFragment.newInstance("test text", "Text title"), true, true)
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(moduleEid: String, title: String): AboutFragment {
            val args = Bundle()
            args.putString(Constants.MODULE_EID, moduleEid)
            args.putString(Constants.MODULE_TITLE, title)
            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
