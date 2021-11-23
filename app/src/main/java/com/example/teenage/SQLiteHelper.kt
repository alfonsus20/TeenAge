package com.example.teenage

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "teenage.db"
        private const val TBL_ACCOUNT = "tbl_account"
        private const val ID = "id"
        private const val NAME = "name"
        private const val JENIS_KELAMIN = "jenis_kelamin"
        private const val TINGGI_BADAN = "tinggi_badan"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblAccount = ("CREATE TABLE " + TBL_ACCOUNT + "("
                + ID + "INTEGER PRIMARY KEY," + NAME + "TEXT,"
                + JENIS_KELAMIN + "TEXT," + TINGGI_BADAN + "INTEGER"
                + ")" )
        db?.execSQL(createTblAccount)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_ACCOUNT ")
        onCreate(db)
    }

    fun insertAccount(acc : AccountModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, acc.id)
        contentValues.put(NAME, acc.name)
        contentValues.put(JENIS_KELAMIN, acc.jenis_kelamin)
        contentValues.put(TINGGI_BADAN, acc.tinggi_badan)

        val success = db.insert(TBL_ACCOUNT, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAlAccount(): ArrayList<AccountModel>{
        val accList: ArrayList<AccountModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_ACCOUNT"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var name: String
        var jenis_kelamin : String
        var tinggi_badan : Int

        if (cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                jenis_kelamin = cursor.getString(cursor.getColumnIndex("jenis_kelamin"))
                tinggi_badan = cursor.getInt(cursor.getColumnIndex("tinggi badan"))

                val acc = AccountModel(id = id, name = name, jenis_kelamin = jenis_kelamin, tinggi_badan = tinggi_badan)
                accList.add(acc)
            }while (cursor.moveToNext())
        }
        return accList
    }
}