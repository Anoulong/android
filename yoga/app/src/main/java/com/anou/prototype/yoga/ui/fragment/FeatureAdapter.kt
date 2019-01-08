package com.anou.prototype.yoga.ui.fragment


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.databinding.ItemFeatureBinding
import com.anou.prototype.yoga.navigation.MainNavigationListener
import androidx.recyclerview.widget.DiffUtil
import com.anou.prototype.core.db.feature.FeatureEntity
import com.anou.prototype.yoga.utils.DiffCallback


class FeatureAdapter(val lifecycleOwner: LifecycleOwner, val inflater: LayoutInflater, private val itemList: MutableList<FeatureEntity> = mutableListOf()) : RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        return FeatureViewHolder(lifecycleOwner, inflater, parent)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bind(itemList[position])

    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(data: List<FeatureEntity>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(this.itemList, data))
        this.itemList.clear()
        this.itemList.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    class FeatureViewHolder(lifecycleOwner: LifecycleOwner, val binding: ItemFeatureBinding) : RecyclerView.ViewHolder(binding.root) {

            constructor(lifecycleOwner: LifecycleOwner, inflater: LayoutInflater, container: ViewGroup) : this(lifecycleOwner, DataBindingUtil.inflate(inflater, R.layout.item_feature, container, false))

        init {
            if (binding.root.context is MainNavigationListener) {
                binding.navigation = binding.root.context as MainNavigationListener
            }
        }

        fun bind(feature: FeatureEntity) {
            binding.feature = feature
        }
    }

}