package com.moustafa.countrypicker.ui.countrieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moustafa.countrypicker.R
import com.moustafa.countrypicker.models.Country
import com.moustafa.countrypicker.utils.load
import kotlinx.android.synthetic.main.item_countries_list.view.*

/**
 * @author moustafasamhoury
 * created on Tuesday, 10 Sep, 2019
 */

class CountriesListAdapter(private val onRowClicked: ((View, Int) -> Any)? = null) :
    ListAdapter<Country, CountriesListAdapter.RecipeViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_countries_list, parent, false
            ), onRowClicked
        )

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) =
        holder.bind(getItem(position))

    class RecipeViewHolder(
        view: View,
        private val onRowClicked: ((View, Int) -> Any)? = null
    ) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onRowClicked?.invoke(it, adapterPosition)
            }
        }

        fun bind(item: Country) {
            itemView.textViewCountryName.text = item.name

            itemView.textViewCountryDescription.text = item.description

            if (item.flagUrl?.isNotBlank() == true) {
                itemView.imageViewFlag.visibility = View.VISIBLE
                itemView.imageViewFlag.load(item.flagUrl)
            } else {
                itemView.imageViewFlag.visibility = View.GONE
            }

            itemView.textViewTimeZone.text = item.timeZonesFormatted(3)
        }
    }
}
