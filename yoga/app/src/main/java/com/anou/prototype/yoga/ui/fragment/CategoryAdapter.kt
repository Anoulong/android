package com.anou.prototype.yoga.ui.fragment


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anou.prototype.core.db.category.CategoryEntity
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.utils.DiffCallback
import com.anou.prototype.yoga.databinding.ItemCategoryBinding
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.AboutActivity


class CategoryAdapter(val lifecycleOwner: LifecycleOwner, val inflater: LayoutInflater,val mainRouter: MainRouter, private val itemList: MutableList<CategoryEntity> = mutableListOf()) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(lifecycleOwner, inflater, parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.itemView.setOnClickListener { mainRouter.onCategorySelected(holder.itemView.context as AboutActivity, itemList.get(position)) }

    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(data: List<CategoryEntity>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(this.itemList, data))
        diffResult.dispatchUpdatesTo(this)
        this.itemList.clear()
        this.itemList.addAll(data)
    }

    class CategoryViewHolder(lifecycleOwner: LifecycleOwner, val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

            constructor(lifecycleOwner: LifecycleOwner, inflater: LayoutInflater, container: ViewGroup) : this(lifecycleOwner, DataBindingUtil.inflate(inflater, R.layout.item_category, container, false))

        fun bind(category: CategoryEntity) {
            binding.category = category
        }
    }

}