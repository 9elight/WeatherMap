package com.example.weathermap.ui.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathermap.R
import com.example.weathermap.model.countriesModel.Countries
import kotlinx.android.synthetic.main.item_city.view.*
//private var list: List<Countries>,
class CityAdapter(val listener: OnItemClickListener) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    private var list:List<Countries> = ArrayList<Countries>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_city,parent,false)
    )

    fun updateList(list: List<Countries>){
        this.list = list
        notifyDataSetChanged()
    }

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
            Glide.with(itemView.context).load(countries.flag).into(itemView.flag_ic)
            itemView.setOnClickListener{
                listener.onItemClicked(countries)
            }
        }

    }
    interface OnItemClickListener {
        fun onItemClicked(item: Countries)
    }
}
