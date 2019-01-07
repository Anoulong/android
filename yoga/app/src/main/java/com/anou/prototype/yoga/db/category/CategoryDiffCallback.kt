package com.anou.prototype.yoga.db.category


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.databinding.ItemCategoryBinding
import com.anou.prototype.yoga.databinding.ItemFeatureBinding
import com.anou.prototype.yoga.navigation.MainNavigationListener


class CategoryDiffCallback(private val oldList: List<CategoryEntity>,
                          private val newList: List<CategoryEntity>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].eid == newList[newItemPosition].eid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].slug == newList[newItemPosition].slug
    }

}