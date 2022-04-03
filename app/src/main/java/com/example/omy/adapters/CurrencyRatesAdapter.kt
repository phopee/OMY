package com.example.omy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.omy.R

class CurrencyRatesAdapter(
    val itemList: List<Pair<String, Double>>
) : RecyclerView.Adapter<CurrencyRatesAdapter.MyViewHolder>() {


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currency: TextView = view.findViewById(R.id.currency)
        val value: TextView = view.findViewById(R.id.value)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val rate = LayoutInflater.from(parent.context)
            .inflate(R.layout.reate_list_item, parent, false)
        return MyViewHolder(rate)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.currency.text = itemList[position].first
        holder.value.text = itemList[position].second.toString()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}

