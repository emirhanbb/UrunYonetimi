package com.example.proje2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_design.view.*

class RecyclerAdapter(val liste: ArrayList<Data>): RecyclerView.Adapter<RecyclerAdapter.CustomerVH>() {
    class CustomerVH(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_design,parent,false)
        return CustomerVH(itemView)
    }

    override fun onBindViewHolder(holder: CustomerVH, position: Int) {
        holder.itemView.nameList.text = liste.get(position).clientname
        holder.itemView.countryList.text = liste.get(position).country
        holder.itemView.totalList.text = liste.get(position).total.toString()


    }

    override fun getItemCount(): Int {
        return liste.size
    }
}