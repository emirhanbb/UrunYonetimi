package com.example.proje2

import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_prooduct_to_customer.*
import kotlinx.android.synthetic.main.add_prooduct_to_customer.view.*
import kotlinx.android.synthetic.main.fragment_customer.*
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.product_recycler_design.view.*
import kotlinx.android.synthetic.main.recycler_design.view.*
import kotlinx.android.synthetic.main.recycler_design.view.countryList
import kotlinx.android.synthetic.main.recycler_design.view.nameList

class productRecyclerAdapter(val liste: ArrayList<productData>): RecyclerView.Adapter<productRecyclerAdapter.productVH>() {
    class productVH(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_recycler_design,parent,false)
        return productVH(itemView)
    }

    override fun onBindViewHolder(holder: productVH, position: Int) {
        holder.itemView.nameList.text = liste.get(position).productname
        holder.itemView.priceList.text = liste.get(position).price.toString()
        holder.itemView.setOnClickListener {

            val mDialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.add_prooduct_to_customer, null)
            val mBuilder = AlertDialog.Builder(holder.itemView.context).setView(mDialogView).setTitle("").show()
            var item: String = ""

            val db = DbAccess(holder.itemView.context)
            val clientName = ArrayList<String>()
            val size = db.readData().size
            for (i in 0 until size){
                clientName.add(db.readData()[i].clientname)
            }


            mDialogView.spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    item= clientName[position]

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {


                }

            }


            val arrayAdapter = ArrayAdapter(holder.itemView.context,R.layout.support_simple_spinner_dropdown_item,clientName)
            mDialogView.spinner.adapter = arrayAdapter

            mDialogView.quantity.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                      }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                    if(s.toString() == ""){
                        mDialogView.total.setText(
                            "Total: " + (liste.get(position).price * 0 ).toString()
                        )
                    }
                    else{
                        mDialogView.total.setText(
                            "Total: " + (liste.get(position).price * s.toString()
                                .toInt()).toString()
                        )
                    }
                                        }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if(s.toString() == ""){
                        mDialogView.total.setText(
                            "Total: " + (liste.get(position).price * 0 ).toString()
                        )
                    }
                    else{
                        mDialogView.total.setText(
                            "Total: " + (liste.get(position).price * s.toString()
                                .toInt()).toString()
                        )
                    }

                }

            })


            mBuilder.save.setOnClickListener {

                val builder = AlertDialog.Builder(holder.itemView.context)
                //set title for alert dialog
                builder.setTitle("Save Product")
                //set message for alert dialog
                builder.setMessage("Are you sure to save?")
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                builder.setPositiveButton("Yes"){dialogInterface, which ->
                    val oldValue = db.getCustomerTotal(db.getIdValue("customer",item))

                    db.updateData("customer",(oldValue + liste.get(position).price * mDialogView.quantity.text.toString().toDouble()).toDouble(),db.getIdValue("customer",item))
                    mBuilder.dismiss()
                }

                //performing negative action
                builder.setNegativeButton("No"){dialogInterface, which ->
                }

                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()





            }
            mBuilder.close.setOnClickListener {
                mBuilder.dismiss()

            }
        }

    }

    override fun getItemCount(): Int {
        return liste.size
    }
}