package com.example.asuransiastrabuana.db

import android.annotation.SuppressLint
import android.content.Context

class MyDataRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    @SuppressLint("Range")
    fun getAllData(): List<MyData> {
        val data = mutableListOf<MyData>()
        val db = dbHelper.readableDatabase

        val cursor = db.query("MyTable", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME))
            val url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_URL))
            data.add(MyData(id, name, url))
        }

        cursor.close()
        db.close()

        return data
    }
}
