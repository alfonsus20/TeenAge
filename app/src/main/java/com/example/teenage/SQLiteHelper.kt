package com.example.teenage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
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
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAMA + " TEXT, "
                + GENDER + " TEXT, " + BERAT + " INTEGER, "
                + TINGGI + " INTEGER, " + TANGGAL_LAHIR + " TEXT"
                + ");")

        val CREATE_TABLE_DRINKS = ("CREATE TABLE " + TBL_DRINKS + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME_DRINK + " TEXT, "
                + KADAR + " DOUBLE, " + INDEX_GAMBAR + " INTEGER"
                + ");")

        val CREATE_TABLE_ALARMS = ("CREATE TABLE " + TBL_ALARMS + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + WAKTU + " TEXT, "
                + STATUS + " BOOLEAN"
                + ");")

        val CREATE_TABLE_HISTORIES = ("CREATE TABLE " + TBL_HISTORIES + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_ID + " INTEGER, "
                + DRINK_ID + " INTEGER, " + VOLUME + " INTEGER,"
                + " FOREIGN KEY ("+ USER_ID +") REFERENCES " + TBL_USERS + "(id),"
                + " FOREIGN KEY ("+ DRINK_ID +") REFERENCES " + TBL_DRINKS + "(id)"
                + ");")
        db?.execSQL(CREATE_TABLE_USERS)
        db?.execSQL(CREATE_TABLE_DRINKS)
        db?.execSQL(CREATE_TABLE_ALARMS)
        db?.execSQL(CREATE_TABLE_HISTORIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE_USERS = "DROP TABLE IF EXISTS $TBL_USERS;"
        val DROP_TABLE_DRINKS = "DROP TABLE IF EXISTS $TBL_DRINKS;"
        val DROP_TABLE_ALARMS = "DROP TABLE IF EXISTS $TBL_ALARMS;"
        val DROP_TABLE_HISTORIES = "DROP TABLE IF EXISTS $TBL_HISTORIES;"

        db?.execSQL(DROP_TABLE_ALARMS)
        db?.execSQL(DROP_TABLE_HISTORIES)
        db?.execSQL(DROP_TABLE_USERS)
        db?.execSQL(DROP_TABLE_DRINKS)

        onCreate(db)
    }

    fun insertUser(user: UserModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(NAMA, user.nama)
        contentValues.put(GENDER, user.gender)
        contentValues.put(TINGGI, user.tinggi)
        contentValues.put(BERAT, user.berat)
        contentValues.put(TANGGAL_LAHIR, user.tanggal_lahir)

        val result = db.insert(TBL_USERS, null, contentValues)
        db.close()
        return result
    }

    fun insertDrinks() {
        val db = this.writableDatabase
        val drinkData = DrinksData.listData

        for ((index,drink) in drinkData.withIndex()) {
            val contentValues = ContentValues()
            contentValues.put(NAME_DRINK, drink.name)
            contentValues.put(KADAR, drink.waterRate)
            contentValues.put(INDEX_GAMBAR, index)
            db.insert(TBL_DRINKS, null, contentValues)
        }

        db.close()
    }

    fun insertHistory(history: HistoryModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(USER_ID, history.user_id)
        contentValues.put(DRINK_ID, history.drink_id)
        contentValues.put(VOLUME, history.volume)
        contentValues.put(DRINK_TIME, getCurrentTimeStamp())

        val result = db.insert(TBL_HISTORIES, null, contentValues)
        db.close()
        return result
    }

    fun historyQuery(): Cursor {
        val db = this.writableDatabase
        return db!!.rawQuery("SELECT * FROM " + TBL_HISTORIES, null)
    }

    fun getUsers() :Cursor {
        val db = this.writableDatabase
        return db!!.rawQuery("SELECT * FROM " + TBL_USERS, null)
    }

    fun getDrinks(): Cursor {
        val db = this.writableDatabase
        return db!!.rawQuery("SELECT * FROM " + TBL_DRINKS, null)
    }

    fun getCurrentTimeStamp(): String? {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            dateFormat.format(Date())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}