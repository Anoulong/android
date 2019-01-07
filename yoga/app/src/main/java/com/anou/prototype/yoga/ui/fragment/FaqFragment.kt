package com.anou.prototype.yoga.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseMainFragment
import com.anou.prototype.yoga.common.Constants
import com.anou.prototype.yoga.viewmodel.FaqViewModel
import kotlinx.android.synthetic.main.fragment_faq.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception


class FaqFragment : BaseMainFragment() {
    val faqViewModel by viewModel<FaqViewModel>()
    lateinit var moduleEid: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        arguments?.let { bundle ->
            bundle.get(Constants.MODULE_EID)?.let { eid ->
                moduleEid = eid.toString()
            }
            bundle.get(Constants.MODULE_TITLE)?.let { title ->
                mainNavigationListener?.onFragmentViewed(title.toString())
            }
        }
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            faqViewModel.getFaqs().observe(this, Observer { faqs ->

                faqs?.let { listOfFaqs ->
                    textViewTitleFaq?.text =  "Count = ${listOfFaqs.count()}"
                }

            })
        } catch (e: Exception) {
            Log.e("FaqFragment", e.message)
        }

    }

}
