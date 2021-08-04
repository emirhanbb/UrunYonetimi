package com.example.proje2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_customer.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DbAccess(this)
        val list = ArrayList<String>()

        db.readData()
        val size = db.readData().size
        for (i in 0 until size){
            list.add(db.readData()[i].clientname)
        }

        db.productReadData()
        val size1 = db.productReadData().size
        for (i in 0 until size1){
            list.add(db.productReadData()[i].productname)
        }

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        searchListView.adapter = arrayAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (list.contains(query)) {
                    arrayAdapter.filter.filter(query)
                } else {
                    Toast.makeText(this@MainActivity, "No Match found", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                arrayAdapter.filter.filter(newText)
                return false
            }

        })

            searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
                if(hasFocus){
                    searchListView.visibility = View.VISIBLE
                }
                else{
                    searchListView.visibility = View.GONE

                }



            }





    }
}