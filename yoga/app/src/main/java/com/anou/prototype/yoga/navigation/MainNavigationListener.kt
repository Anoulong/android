package com.anou.prototype.yoga.navigation

import com.anou.prototype.yoga.db.ModuleEntity
import com.anou.prototype.yoga.db.category.CategoryEntity
import com.anou.prototype.yoga.db.feature.FeatureEntity


/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 *
 *
 * See the Android Training lesson [Communicating with Other Fragments]
 * (http://developer.android.com/training/basics/fragments/communicating.html)
 * for more information.
 */
interface MainNavigationListener {
    fun onFragmentViewed(string: String)
    fun onModuleSelected(module: ModuleEntity, isLaunchModule : Boolean = false)
    fun onCategorySelected(category: CategoryEntity)
    fun onFeatureSelected(featureEntity: FeatureEntity)
}