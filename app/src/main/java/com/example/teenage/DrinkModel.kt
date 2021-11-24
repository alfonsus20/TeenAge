package com.example.teenage

import java.util.*

data class DrinkModel (
    var id: Int = getAutoId(),
    var name_drink: String = "",
    var jumlah : Int = 0,
    var tanggal : String = "",
){
    companion object{
        fun getAutoId():Int{
            val random = Random()
            return  random.nextInt(100)
        }
    }

}