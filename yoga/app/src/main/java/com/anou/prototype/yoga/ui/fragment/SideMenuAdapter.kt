package com.anou.prototype.yoga.ui.fragment


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.databinding.ItemSideMenuBinding
import com.anou.prototype.core.db.ModuleEntity
import com.anou.prototype.yoga.navigation.MainRouter
import com.anou.prototype.yoga.ui.MainActivity
import com.anou.prototype.yoga.utils.DiffCallback


class SideMenuAdapter(val lifecycleOwner: LifecycleOwner, val inflater: LayoutInflater, val mainRouter: MainRouter) : BaseRecyclerViewAdapter<ModuleEntity, SideMenuAdapter.DrawerModuleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerModuleViewHolder {
        return DrawerModuleViewHolder(lifecycleOwner, inflater, parent)
    }

    override fun onBindViewHolder(holder: DrawerModuleViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { mainRouter.onModuleSelected(holder.itemView.context as MainActivity, getItem(position), false) }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    class DrawerModuleViewHolder(lifecycleOwner: LifecycleOwner, val binding: ItemSideMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        constructor(lifecycleOwner: LifecycleOwner, inflater: LayoutInflater, container: ViewGroup) : this(lifecycleOwner, DataBindingUtil.inflate(inflater, R.layout.item_side_menu, container, false))

        fun bind(module: ModuleEntity) {
            binding.module = module
        }
    }

}