package com.example.storage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException

internal const val FIRST_COLUMN = "FIRST_COLUMN"
internal const val SECOND_COLUMN = "SECOND_COLUMN"
internal const val THIRD_COLUMN = "THIRD_COLUMN"

private const val DATABASE_NAME = "ALL_PERSON"
private const val TABLE_NAME = "PERSON"
private const val DATABASE_VERSION = 1
private const val CREATE_TABLE_SQL =
    "CREATE TABLE IF NOT EXISTS $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, $FIRST_COLUMN VARCHAR(50), $SECOND_COLUMN VARCHAR(50), $THIRD_COLUMN VARCHAR(50));"

class SQLiteOpen(context: Context?) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
), ButtonListener{
    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_TABLE_SQL)
            db.execSQL("INSERT INTO $TABLE_NAME ($FIRST_COLUMN, $SECOND_COLUMN, $THIRD_COLUMN) VALUES('Eferger','ffffffffffffwe','5');")
            db.execSQL("INSERT INTO $TABLE_NAME ($FIRST_COLUMN, $SECOND_COLUMN, $THIRD_COLUMN) VALUES('gre','ffewffffwe','4');")
            db.execSQL("INSERT INTO $TABLE_NAME ($FIRST_COLUMN, $SECOND_COLUMN, $THIRD_COLUMN) VALUES('Effewger','ffffergefffwe','3');")
            db.execSQL("INSERT INTO $TABLE_NAME ($FIRST_COLUMN, $SECOND_COLUMN, $THIRD_COLUMN) VALUES('Eferger','fewgfffffwe','2');")
            db.execSQL("INSERT INTO $TABLE_NAME ($FIRST_COLUMN, $SECOND_COLUMN, $THIRD_COLUMN) VALUES('Efefew','gt5u','1');")
        } catch (exception: SQLException) {
            Log.e("onCreate", "Exception while trying to create database", exception)
        }


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("onUpgrade", "onUpgrade called")
    }

    private fun getCursorWithTopicsRead(): Cursor {
        return readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getListOfTopics(): List<Person> {
        val listOfTopics = mutableListOf<Person>()
        getCursorWithTopicsRead().use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("_id"))
                    val firstName = cursor.getString(cursor.getColumnIndex(FIRST_COLUMN))
                    val secondName = cursor.getString(cursor.getColumnIndex(SECOND_COLUMN))
                    val age = cursor.getString(cursor.getColumnIndex(THIRD_COLUMN))
                    val person = Person(id, firstName, secondName, age)
                    listOfTopics.add(person)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        for (p in listOfTopics) {
            Log.d("getListOfTopics", p.toString())
        }
        return listOfTopics
    }

    fun savePerson(name: String, secondName: String, age: String){
        val values = ContentValues()
        values.put(FIRST_COLUMN, name)
        values.put(SECOND_COLUMN, secondName)
        values.put(THIRD_COLUMN, age)
        writableDatabase.insert(TABLE_NAME,null, values)
    }

    override fun delete(id: Int) {
        writableDatabase.delete(TABLE_NAME, "_id = $id", null)
    }

    override fun edit(id: Int?, name: String?, secondName: String?, age: String?) {
        val updatedValues = ContentValues()
        updatedValues.put(FIRST_COLUMN, name)
        updatedValues.put(SECOND_COLUMN, secondName)
        updatedValues.put(THIRD_COLUMN, age)
        val where = "_id = $id"
        writableDatabase.update(TABLE_NAME, updatedValues, where, null)
    }
}