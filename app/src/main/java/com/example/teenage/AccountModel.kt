package com.example.teenage

import java.util.*

data class AccountModel (
    var id: Int = getAutoId(),
    var name: String = "",
    var jenis_kelamin : String = "",
    var tinggi_badan : Int = getAutoId()

){
    companion object{
        fun getAutoId():Int{
            val random = Random()
            return  random.nextInt(100)
        }
    }

}