package com.anou.prototype.yoga.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.databinding.ItemDrawerModuleBinding
import com.anou.prototype.core.db.ModuleEntity
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.utils.DiffCallback


class DrawerAdapter(val lifecycleOwner: LifecycleOwner, val inflater: LayoutInflater, val mainRouter: MainRouter, private val itemList: MutableList<ModuleEntity> = mutableListOf()) : RecyclerView.Adapter<DrawerAdapter.DrawerModuleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerModuleViewHolder {
        return DrawerModuleViewHolder(lifecycleOwner, inflater, parent)
    }

    override fun onBindViewHolder(holder: DrawerModuleViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.itemView.setOnClickListener { mainRouter.onModuleSelected(holder.itemView.context as MainActivity, itemList.get(position), false) }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(data: List<ModuleEntity>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(this.itemList, data))
        diffResult.dispatchUpdatesTo(this)
        this.itemList.clear()
        this.itemList.addAll(data)
    }

    class DrawerModuleViewHolder(lifecycleOwner: LifecycleOwner, val binding: ItemDrawerModuleBinding) : RecyclerView.ViewHolder(binding.root) {

        constructor(lifecycleOwner: LifecycleOwner, inflater: LayoutInflater, container: ViewGroup) : this(lifecycleOwner, DataBindingUtil.inflate(inflater, R.layout.item_drawer_module, container, false))

        fun bind(module: ModuleEntity) {
            binding.module = module
        }
    }

}