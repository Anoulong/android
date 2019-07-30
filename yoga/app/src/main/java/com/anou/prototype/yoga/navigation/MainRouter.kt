package com.anou.prototype.yoga.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.anou.prototype.core.db.ModuleEntity
import com.anou.prototype.core.db.category.CategoryEntity
import com.anou.prototype.core.db.feature.FeatureEntity
import com.anou.prototype.yoga.base.BaseActivity
import com.anou.prototype.yoga.ui.AboutActivity
import com.anou.prototype.yoga.ui.FaqActivity
import com.anou.prototype.yoga.utils.Constants
import kotlinx.android.synthetic.main.activity_base.*

class MainRouter {

    fun onModuleSelected(baseActivity: BaseActivity, module: ModuleEntity, isLaunchModule: Boolean) {

        var bundle = Bundle()

        module.let {
            bundle.putString(Constants.MODULE_EID, module.eid)
            bundle.putString(Constants.MODULE_TITLE, module.title)

            when (module.type) {
                ModuleEntity.FAQ -> {
                    baseActivity.startActivity(Intent(baseActivity, FaqActivity::class.java))
                    baseActivity.finishAffinity()
//                    Navigation.findNavController(BaseActivity, R.id.mainNavigationHost).navigate(R.id.categoryFragmentDestination, bundle, navOptions)
                }
                ModuleEntity.ABOUT -> {
                    baseActivity.startActivity(Intent(baseActivity, AboutActivity::class.java))
//                    baseActivity.finishAffinity()
//                    Navigation.findNavController(BaseActivity, R.id.mainNavigationHost).navigate(R.id.aboutFragmentDestination, bundle, navOptions)
                }
                ModuleEntity.TEXT_TYPE -> {
//                    Navigation.findNavController(BaseActivity, R.id.mainNavigationHost).navigate(R.id.textFragmentDestination, bundle, navOptions)
                }
                else -> Toast.makeText(baseActivity, module.title, Toast.LENGTH_SHORT).show()
            }
        }

        baseActivity.drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun onCategorySelected(BaseActivity: BaseActivity, category: CategoryEntity) {
        var bundle = Bundle()
        category.let {
            bundle.putString(Constants.CATEGORY_EID, category.eid)
            bundle.putString(Constants.CATEGORY_TITLE, category.title)

//            Navigation.findNavController(BaseActivity, R.id.mainNavigationHost).navigate(R.id.featureFragmentDestination, bundle)
        }
        Toast.makeText(BaseActivity, category.title, Toast.LENGTH_SHORT).show()
    }

    fun onFeatureSelected(BaseActivity: BaseActivity, featureEntity: FeatureEntity) {
        Toast.makeText(BaseActivity, featureEntity.eid, Toast.LENGTH_SHORT).show()
    }

    fun onFragmentViewed(BaseActivity: BaseActivity, string: String) {
        println("Log state ==> $string")
        BaseActivity.supportActionBar?.title = string
    }
}