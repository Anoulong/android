package com.anou.prototype.yoga.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.anou.prototype.core.viewmodel.MainViewModel
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.base.BaseFragment
import com.anou.prototype.yoga.databinding.FragmentSideMenuBinding
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.utils.Constants
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SideMenuFragment : BaseFragment() {
    override val fragmentTag: String
        get() =  SideMenuFragment::class.java.simpleName

    val mainViewModel by viewModel<MainViewModel>()
    val mainRouter: MainRouter by inject()

    lateinit var binding: FragmentSideMenuBinding
    lateinit var adapter: SideMenuAdapter
    lateinit var categoryEid: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        arguments?.let { bundle ->
            bundle.get(Constants.CATEGORY_EID)?.let { eid ->
                categoryEid = eid.toString()
            }
            bundle.get(Constants.CATEGORY_TITLE)?.let { title ->
                mainRouter.onFragmentViewed(activity as BaseActivity, title.toString())
            }
        }

        // Bind views
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_side_menu, container, false)
        adapter = SideMenuAdapter(this, inflater = LayoutInflater.from(activity), mainRouter = mainRouter)
        binding.setLifecycleOwner(this)
        binding.sideMenuRecyclerView.adapter = adapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            mainViewModel.getModules().observe(this, Observer { result ->

                result.let { data ->
                    adapter.setData(data)

                }
//                if(adapter.itemCount > 0) {
//                    adapter.getItem(0).let { firstModule ->
//                        mainRouter.onModuleSelected(activity as BaseActivity, firstModule, true)
//                    }
//                }else{
//                    Toast.makeText(activity, "", Toast.LENGTH_LONG).show()
//                }



            })
        } catch (e: Exception) {
            Log.e("SideMenuFragment", e.message)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.mainViewModel = mainViewModel
    }

}
