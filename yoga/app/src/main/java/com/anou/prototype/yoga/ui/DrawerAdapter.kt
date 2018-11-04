package com.anou.prototype.yoga.ui

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.anou.prototype.yoga.R
import com.anou.prototype.yoga.base.BaseRecyclerViewAdapter
import com.anou.prototype.yoga.db.ModuleEntity


import java.util.HashMap


class DrawerAdapter : BaseRecyclerViewAdapter<ModuleEntity, RecyclerView.ViewHolder>() {

    private var mListener: DrawerAdapterListener? = null

    interface DrawerAdapterListener {
        fun onDrawerItemSelected(v: View, item: ModuleEntity, position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View

        view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer_module, parent, false)

        return DrawerModuleViewHolder(view)

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        val settingViewHolder = holder as DrawerModuleViewHolder
        setupDrawer(settingViewHolder, item)

    }


    private fun setupDrawer(moduleViewHolder: DrawerModuleViewHolder, item: ModuleEntity) {
        moduleViewHolder.itemDrawerModuleTitle.setText(item.title)
        moduleViewHolder.itemView.setOnClickListener { v ->
            mListener!!.onDrawerItemSelected(
                v,
                item,
                moduleViewHolder.adapterPosition
            )
        }
    }

    fun setListener(listener: DrawerAdapterListener) {
        this.mListener = listener
    }



    override fun setData(data: List<ModuleEntity>) {
        super.setData(data)
    }

    class DrawerModuleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var itemDrawerModuleTitle: TextView
        internal var itemDrawerModuleLayout: LinearLayout


        init {
            itemDrawerModuleTitle = itemView.findViewById(R.id.itemDrawerModuleTitle)
            itemDrawerModuleLayout = itemView.findViewById(R.id.itemDrawerModuleLayout)
        }
    }

}