package com.example.weathermap.ui.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermap.R
import com.example.weathermap.model.countriesModel.Countries
import kotlinx.android.synthetic.main.item_city.view.*

class CityAdapter() : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    lateinit var list:List<Countries>

    fun updateList(list:List<Countries>){
        this.list = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_city,parent,false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        fun bind(countries: Countries) {
            itemView.city_name.text = countries.capital
            itemView.country_name.text = countries.name
        }

    }
}
