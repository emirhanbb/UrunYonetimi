package com.example.proje2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.Editable
import android.view.View

class DbAccess(var context: Context):SQLiteOpenHelper(context,"Database",null,1)  {


    override fun onCreate(db: SQLiteDatabase?) {
        val database = "CREATE TABLE customer(id INTEGER PRIMARY KEY AUTOINCREMENT,clientname VARCHAR(50),country VARCHAR(50),total DOUBLE);"
        db?.execSQL(database)

        val database2 = "CREATE TABLE product(id INTEGER PRIMARY KEY AUTOINCREMENT,productname VARCHAR(50),price DOUBLE)"
        db?.execSQL(database2)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }



    fun insertData(table: String,name: String, country: String,  ) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("clientname",name)
        cv.put("country",country)
        cv.put("total",0)
        db.insert(table,null,cv)
    }

    fun insertProductData(table: String,name: String, price: String,  ) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("productname",name)
        cv.put("price",price)
        db.insert(table,null,cv)
    }


    fun readData(): ArrayList<Data> {
        val db = this.readableDatabase

        val customerArray = ArrayList<Data>()

            val cursor = db.rawQuery("SELECT * FROM 'customer'",null)
            val idindex = cursor.getColumnIndex("id")
            val clientname = cursor.getColumnIndex("clientname")
            val country = cursor.getColumnIndex("country")
            val total = cursor.getColumnIndex("total")

            while (cursor.moveToNext()){
                val data = Data(cursor.getInt(idindex),cursor.getString(clientname),cursor.getString(country),cursor.getDouble(total))
                customerArray.add(data)
            }



        return customerArray

    }

    fun productReadData():ArrayList<productData>{
        val db = this.readableDatabase

        val productArray = ArrayList<productData>()

        val cursor = db.rawQuery("SELECT * FROM 'product'",null)
        val idindex = cursor.getColumnIndex("id")
        val clientname = cursor.getColumnIndex("productname")
        val price = cursor.getColumnIndex("price")

        while (cursor.moveToNext()){
            val data = productData(cursor.getInt(idindex),cursor.getString(clientname),cursor.getDouble(price))
            productArray.add(data)
        }

        return productArray
    }

    fun updateData(table:String, value:Double,id:Int){
        val db = this.writableDatabase
        var query = "UPDATE $table SET total = $value WHERE id = $id"
        db.execSQL(query)
        db.close()
    }

    fun deleteData(table: String, index: Int){
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $table WHERE id=$index")
        db.close()
    }

    fun getIdValue(table: String, item: String):Int{
        val db1 = this.readableDatabase

        val cursor = db1.rawQuery("SELECT id FROM $table Where clientname='$item'",null)
        val idindex = cursor.getColumnIndex("id")
        var a: Int? = null

        while (cursor.moveToNext()){
            a= cursor.getInt(cursor.getColumnIndex("id"))
        }
        cursor.close();
        db1.close()

        return a!!

    }

    fun getCustomerTotal(index:Int):Int{
        val db1 = this.readableDatabase

        val cursor = db1.rawQuery("SELECT total FROM 'customer' Where id='$index'",null)
        var a: Int? = null

        while (cursor.moveToNext()){
            a= cursor.getInt(cursor.getColumnIndex("total"))
        }
        cursor.close();
        db1.close()

        return a!!
    }


    fun getProductIdValue(table: String, item: String):Int{
        val db1 = this.readableDatabase

        val cursor = db1.rawQuery("SELECT id FROM $table Where productname='$item'",null)
        val idindex = cursor.getColumnIndex("id")
        var a: Int? = null

        while (cursor.moveToNext()){
            a= cursor.getInt(cursor.getColumnIndex("id"))
        }
        cursor.close();
        db1.close()

        return a!!

    }


}