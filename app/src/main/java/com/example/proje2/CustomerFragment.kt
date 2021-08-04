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

class CustomerFragment : Fragment() {
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


        return inflater.inflate(R.layout.fragment_customer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DbAccess(mContext)

        val clientName = ArrayList<String>()

        val size = db.readData().size
        for (i in 0 until size){
           clientName.add(db.readData()[i].clientname)
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                item= clientName[position]


                Toast.makeText(mContext,"$id",Toast.LENGTH_SHORT).show()
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

        val arrayAdapter = ArrayAdapter(mContext,R.layout.support_simple_spinner_dropdown_item,clientName)
        spinner.adapter = arrayAdapter
        val layoutManager = LinearLayoutManager(mContext)
        customerRecycler.layoutManager = layoutManager


        val adapter = RecyclerAdapter(db.readData())
        customerRecycler.adapter = adapter


        deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(mContext)
            //set title for alert dialog
            builder.setTitle("Delete Customer")
            //set message for alert dialog
            builder.setMessage("Are you sure you want to delete the customer?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Yes"){dialogInterface, which ->
                //seçilen müşterinin ismi sql sorgusu ile aranır ve id değeri bulunur. İd değeri silinmek üzere delete fonksiyonuna
                //gönderilir.
                clientName.clear()

                db.deleteData("customer",db.getIdValue("customer",item))
                val adapter = RecyclerAdapter(db.readData())
                customerRecycler.adapter = adapter
                deleteLinear.visibility = View.GONE
                val size = db.readData().size
                for (i in 0 until size){
                    clientName.add(db.readData()[i].clientname)
                }
                Toast.makeText(mContext,"customer deleted",Toast.LENGTH_LONG).show()
            }

            //performing negative action
            builder.setNegativeButton("No"){dialogInterface, which ->
                Toast.makeText(mContext,"clicked No",Toast.LENGTH_LONG).show()
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
            if(name.text.toString() == "" || country.text.toString() == ""){
                Toast.makeText(mContext, "the name and country field cannot be left blank", Toast.LENGTH_LONG).show()
            }
            else{
                db.insertData("customer",name.text.toString(),country.text.toString())
                Toast.makeText(mContext, "successful", Toast.LENGTH_LONG).show()
                val adapter = RecyclerAdapter(db.readData())
                customerRecycler.adapter = adapter
                addLinear.visibility = View.GONE


            }
        }


        del.setOnClickListener {

                clientName.clear()


            val size = db.readData().size
            for (i in 0 until size){
                clientName.add(db.readData()[i].clientname)
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