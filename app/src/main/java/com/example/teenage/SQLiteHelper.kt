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
        private const val TBL_USER = "tbl_user"
        private const val ID = "id"
        private const val NAME = "name"
        private const val JENIS_KELAMIN = "jenis_kelamin"
        private const val TINGGI_BADAN = "tinggi_badan"

        private const val TBL_DRINK = "tbl_drink"
        private const val NAME_DRINK = "name_drink"
        private const val JUMLAH = "jumlah"
        private const val TANGGAL = "tanggal"

        private const val TBL_WAKTU = "tbl_waktu"
        private const val WAKTU = "waktu"
        private const val STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_USER = ("CREATE TABLE " + TBL_USER + "("
                + ID + "INTEGER PRIMARY KEY," + NAME + "TEXT,"
                + JENIS_KELAMIN + "TEXT," + TINGGI_BADAN + "INTEGER"
                + ")" )

        val CREATE_TABLE_DRINK = ("CREATE TABLE " + TBL_DRINK + "("
                + ID + "INTEGER PRIMARY KEY," + NAME_DRINK + "TEXT,"
                + JUMLAH + "INTEGER," + TANGGAL + "TEXT"
                + ")" )

        val CREATE_TABLE_WAKTU = ("CREATE TABLE " + TBL_WAKTU + "("
                + ID + "INTEGER PRIMARY KEY," + JUMLAH + "INTEGER,"
                + WAKTU + "TEXT," + STATUS + "INTEGER"
                + ")" )
        db?.execSQL(CREATE_TABLE_USER + CREATE_TABLE_DRINK + CREATE_TABLE_WAKTU)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE_USER = "DROP TABLE IF EXISTS $TBL_USER"
        val DROP_TABLE_DRINK = "DROP TABLE IF EXISTS $TBL_DRINK"
        val DROP_TABLE_WAKTU = "DROP TABLE IF EXISTS $TBL_WAKTU"

        db!!.execSQL(DROP_TABLE_USER + DROP_TABLE_DRINK + DROP_TABLE_WAKTU)

        onCreate(db)
    }

    fun insertUser(acc : UserModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, acc.id)
        contentValues.put(NAME, acc.name)
        contentValues.put(JENIS_KELAMIN, acc.jenis_kelamin)
        contentValues.put(TINGGI_BADAN, acc.tinggi_badan)

        val success = db.insert(TBL_USER, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllUser(): ArrayList<UserModel>{
        val accList: ArrayList<UserModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_USER"
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

                val acc = UserModel(id = id, name = name, jenis_kelamin = jenis_kelamin, tinggi_badan = tinggi_badan)
                accList.add(acc)
            }while (cursor.moveToNext())
        }
        return accList
    }
}