package com.example.proje2

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_customer.*
import kotlinx.android.synthetic.main.fragment_customer.add
import kotlinx.android.synthetic.main.fragment_customer.addLinear
import kotlinx.android.synthetic.main.fragment_customer.del
import kotlinx.android.synthetic.main.fragment_customer.deleteButton
import kotlinx.android.synthetic.main.fragment_customer.deleteLinear
import kotlinx.android.synthetic.main.fragment_customer.name
import kotlinx.android.synthetic.main.fragment_customer.saveButton
import kotlinx.android.synthetic.main.fragment_customer.spinner
import kotlinx.android.synthetic.main.fragment_product.*


class ProductFragment : Fragment() {
    lateinit var mContext:Context
    var item: String = ""


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext= context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DbAccess(mContext)

        val productName = ArrayList<String>()

        val size = db.productReadData().size
        for (i in 0 until size){
            productName.add(db.productReadData()[i].productname)
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                item= productName[position]


                Toast.makeText(mContext,"$id", Toast.LENGTH_SHORT).show()
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

        val arrayAdapter = ArrayAdapter(mContext,R.layout.support_simple_spinner_dropdown_item,productName)
        spinner.adapter = arrayAdapter
        val layoutManager = LinearLayoutManager(mContext)
        productRecycler.layoutManager = layoutManager


        val adapter = productRecyclerAdapter(db.productReadData())
        productRecycler.adapter = adapter


        deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(mContext)
            //set title for alert dialog
            builder.setTitle("Delete Product")
            //set message for alert dialog
            builder.setMessage("Are you sure you want to delete the product?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Yes"){dialogInterface, which ->
                //seçilen müşterinin ismi sql sorgusu ile aranır ve id değeri bulunur. İd değeri silinmek üzere delete fonksiyonuna
                //gönderilir.
                productName.clear()

                db.deleteData("product",db.getProductIdValue("product",item))
                val adapter = productRecyclerAdapter(db.productReadData())
                productRecycler.adapter = adapter
                deleteLinear.visibility = View.GONE
                val size = db.productReadData().size
                for (i in 0 until size){
                    productName.add(db.productReadData()[i].productname)
                }
                Toast.makeText(mContext,"product deleted", Toast.LENGTH_LONG).show()
            }

            //performing negative action
            builder.setNegativeButton("No"){dialogInterface, which ->
                Toast.makeText(mContext,"clicked No", Toast.LENGTH_LONG).show()
            }

            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()



        }



        add.setOnClickListener{

            if(addLinear.visibility == View.GONE){
                addLinear.visibility = View.VISIBLE
            }
            else{
                addLinear.visibility = View.GONE
            }
        }


        saveButton.setOnClickListener {
            if(name.text.toString() == "" || price.text.toString() == ""){
                Toast.makeText(mContext, "the name and country field cannot be left blank", Toast.LENGTH_LONG).show()
            }
            else{
                db.insertProductData("product",name.text.toString(),price.text.toString())
                Toast.makeText(mContext, "successful", Toast.LENGTH_LONG).show()
                val adapter = productRecyclerAdapter(db.productReadData())
                productRecycler.adapter = adapter
                addLinear.visibility = View.GONE


            }
        }


        del.setOnClickListener {

            productName.clear()


            val size = db.productReadData().size
            for (i in 0 until size){
                productName.add(db.productReadData()[i].productname)
            }

            if(deleteLinear.visibility == View.GONE){
                deleteLinear.visibility = View.VISIBLE
            }
            else{
                deleteLinear.visibility = View.GONE
            }
        }


    }



}