package com.anou.prototype.yoga.ui

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseRecyclerViewAdapter
import com.anou.prototype.yoga.databinding.ItemDrawerModuleBinding
import com.anou.prototype.yoga.db.ModuleEntity
import kotlinx.android.synthetic.main.item_drawer_module.view.*


import java.util.HashMap


class DrawerAdapter(val lifecycleOwner: LifecycleOwner, val inflater: LayoutInflater, private val itemList: MutableList<ModuleEntity> = mutableListOf()) : RecyclerView.Adapter<DrawerAdapter.DrawerModuleViewHolder>() {
    override fun onBindViewHolder(holder: DrawerModuleViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerModuleViewHolder {
        return DrawerModuleViewHolder(lifecycleOwner, inflater, parent)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(data: List<ModuleEntity>) {
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    class DrawerModuleViewHolder(lifecycleOwner: LifecycleOwner, val binding: ItemDrawerModuleBinding) : RecyclerView.ViewHolder(binding.root) {

        constructor(lifecycleOwner: LifecycleOwner, inflater: LayoutInflater, container: ViewGroup) : this(lifecycleOwner, DataBindingUtil.inflate(inflater, R.layout.item_drawer_module, container, false))

        fun bind(module: ModuleEntity) {
            binding.module = module
        }
    }

}