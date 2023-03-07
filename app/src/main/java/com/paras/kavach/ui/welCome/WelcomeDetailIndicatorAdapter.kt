package com.paras.kavach.ui.welCome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paras.kavach.databinding.ItemSelectedPagerBinding
import com.paras.kavach.utils.gone
import com.paras.kavach.utils.visible

class WelcomeDetailIndicatorAdapter :
    ListAdapter<Int, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }) {

    var selectedPosition = 0
    var previousSelected = 0


    class ViewHolderSelected(val binding: ItemSelectedPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val item = ItemSelectedPagerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderSelected(item)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderSelected).binding.apply {
            if (position == selectedPosition) {
               cvUnselected.gone()
               cvSelected.visible()
            } else {
                cvUnselected.visible()
                cvSelected.gone()
            }
        }
    }


}