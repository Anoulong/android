package com.anou.prototype.yoga.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.utils.Constants
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.AboutActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_text.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class TextFragment : BaseFragment() {
    override val fragmentTag: String
        get() = TextFragment::class.java.simpleName

    val mainViewModel by viewModel<MainViewModel>()
    val mainRouter: MainRouter by inject()

    lateinit var moduleEid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as BaseActivity).supportActionBar?.setHomeButtonEnabled(true)
        (activity as BaseActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as BaseActivity).toolbar.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                (activity as AboutActivity).onBackPressed()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        arguments?.let {bundle ->
            bundle.get(Constants.MODULE_EID)?.let { eid ->
                moduleEid = eid.toString()
            }
            bundle.get(Constants.MODULE_TITLE)?.let { title ->
                mainRouter.onFragmentViewed(activity as BaseActivity, title.toString())
            }
        }
        return inflater.inflate(R.layout.fragment_text   , container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewTitleText ?.text = moduleEid
        mainRouter.onFragmentViewed(activity as BaseActivity, "Text Fragment")
    }


    companion object {

        @JvmStatic
        fun newInstance(moduleEid: String, title: String): TextFragment {
            val args = Bundle()
            args.putString(Constants.MODULE_EID, moduleEid)
            args.putString(Constants.MODULE_TITLE, title)
            val fragment = TextFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
