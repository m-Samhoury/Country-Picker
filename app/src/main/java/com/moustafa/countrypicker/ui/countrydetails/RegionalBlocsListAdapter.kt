package com.moustafa.countrypicker.ui.countrydetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moustafa.countrypicker.R
import com.moustafa.countrypicker.models.RegionalBloc
import kotlinx.android.synthetic.main.item_regional_bloc_list.view.*

/**
 * @author moustafasamhoury
 * created on Saturday, 21 Sep, 2019
 */

class RegionalBlocsListAdapter :
    ListAdapter<RegionalBloc, RegionalBlocsListAdapter.RegionalBlocViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<RegionalBloc>() {
            override fun areItemsTheSame(oldItem: RegionalBloc, newItem: RegionalBloc): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: RegionalBloc, newItem: RegionalBloc): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionalBlocViewHolder =
        RegionalBlocViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_regional_bloc_list, parent, false
            )
        )

    override fun onBindViewHolder(holder: RegionalBlocViewHolder, position: Int) =
        holder.bind(getItem(position))

    class RegionalBlocViewHolder(
        view: View,
        private val onRowClicked: ((View, Int) -> Any)? = null
    ) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onRowClicked?.invoke(it, adapterPosition)
            }
        }

        fun bind(item: RegionalBloc) {
            itemView.textViewName.text = "${item.name}(${item.acronym})"
        }
    }
}
