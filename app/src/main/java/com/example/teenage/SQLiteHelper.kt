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
        private const val TBL_USERS = "tbl_users"
        private const val ID = "id"
        private const val NAMA = "nama"
        private const val GENDER = "gender"
        private const val BERAT = "berat"
        private const val TINGGI = "tinggi"
        private const val TANGGAL_LAHIR = "tanggal_lahir"

        private const val TBL_DRINKS = "tbl_drinks"
        private const val NAME_DRINK = "name_drink"
        private const val KADAR = "kadar"
        private const val INDEX_GAMBAR = "index_gambar"

        private const val TBL_ALARMS = "tbl_alarms"
        private const val WAKTU = "waktu"
        private const val STATUS = "status"

        private const val TBL_HISTORIES = "tbl_histories"
        private const val USER_ID = "user_id"
        private const val DRINK_ID = "drink_id"
        private const val VOLUME = "volume"

        // MASIH BELUM TAU WAKTU TIMESTAMPS GIMANA JADI BELUM DIMASUKKAN DI CREATE TABLE
        private const val DRINK_TIME = "drink_time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_USERS = ("CREATE TABLE " + TBL_USERS + "("
                + ID + "INTEGER PRIMARY KEY," + NAMA + "TEXT,"
                + GENDER + "TEXT," + BERAT + "INTEGER,"
                + TINGGI + "INTEGER," + TANGGAL_LAHIR + "TEXT"
                + ")" )

        val CREATE_TABLE_DRINKS = ("CREATE TABLE " + TBL_DRINKS + "("
                + ID + "INTEGER PRIMARY KEY," + NAME_DRINK + "TEXT,"
                + KADAR + "INTEGER," + INDEX_GAMBAR + "TEXT"
                + ")" )

        val CREATE_TABLE_ALARMS = ("CREATE TABLE " + TBL_ALARMS + "("
                + ID + "INTEGER PRIMARY KEY," + WAKTU + "TEXT,"
                + STATUS + "INTEGER"
                + ")" )

        val CREATE_TABLE_HISTORIES = ("CREATE TABLE " + TBL_HISTORIES + "("
                + ID + "INTEGER PRIMARY KEY," + USER_ID + "INTEGER,"
                + DRINK_ID + "INTEGER," + VOLUME + "INTEGER"
                // COLUMN DRINK_TIME BELUM SOALNUA GAK TAU TIMESTAMP
                + ")" )
        db?.execSQL(CREATE_TABLE_USERS + CREATE_TABLE_DRINKS + CREATE_TABLE_ALARMS + CREATE_TABLE_HISTORIES )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE_USERS = "DROP TABLE IF EXISTS $TBL_USERS"
        val DROP_TABLE_DRINKS= "DROP TABLE IF EXISTS $TBL_DRINKS"
        val DROP_TABLE_ALARMS = "DROP TABLE IF EXISTS $TBL_ALARMS"
        val DROP_TABLE_HISTORIES = "DROP TABLE IF EXISTS $TBL_HISTORIES"

        db!!.execSQL(DROP_TABLE_USERS + DROP_TABLE_DRINKS + DROP_TABLE_ALARMS + DROP_TABLE_HISTORIES )

        onCreate(db)
    }

    // Insert Yang Lain nanti menyesuaikan yang mau di insertkan
    fun insertUser(user : UserModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, user.id)
        contentValues.put(NAMA, user.nama)
        contentValues.put(GENDER, user.berat)
        contentValues.put(TINGGI, user.tinggi)
        contentValues.put(BERAT, user.berat)
        contentValues.put(TANGGAL_LAHIR, user.tanggal_lahir)

        val success = db.insert(TBL_USERS, null, contentValues)
        db.close()
        return success
    }

    fun userQuery(): Cursor {
        val db = this.writableDatabase
        return db!!.rawQuery("SELECT * FROM " + TBL_USERS, null)
    }
}