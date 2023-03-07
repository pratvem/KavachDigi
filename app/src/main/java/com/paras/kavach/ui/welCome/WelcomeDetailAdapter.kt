package com.paras.kavach.ui.welCome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paras.kavach.databinding.ItemViewPagerBinding

class WelcomeDetailAdapter:ListAdapter<Int, WelcomeDetailAdapter.ViewHolder>(object :DiffUtil.ItemCallback<Int>(){
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return  oldItem==newItem
    }
}) {
    class ViewHolder(val binding:ItemViewPagerBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item=ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(holder.binding){
           ivView.setImageResource(getItem(position))
       }
    }
}