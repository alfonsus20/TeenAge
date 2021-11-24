package com.example.teenage

import java.util.*

data class WaktuModel (
    var id: Int = getAutoId(),
    var jumlah : Int = 0,
    var waktu: String = "",
    var status : Int = 0,
){
    companion object{
        fun getAutoId():Int{
            val random = Random()
            return  random.nextInt(100)
        }
    }

}