package com.example.teenage

object DrinksData {
    private val drinkNames = arrayOf(
        "Water",
        "Sport Drink",
        "Zero Sports Drink",
        "Mineral Water",
        "Energy Drink",
        "Rice Drink"
    )

    private val drinkPictures = intArrayOf(
        R.drawable.water,
        R.drawable.sport_drink,
        R.drawable.zero_sport_drink,
        R.drawable.mineral_water,
        R.drawable.energy_drink,
        R.drawable.rice_drink
    )

    val listData: ArrayList<Drink>
        get() {
            val list = arrayListOf<Drink>()

            for (position in drinkNames.indices){
                val drink = Drink()
                drink.name = drinkNames[position]
                drink.picture = drinkPictures[position]
                list.add(drink)
            }

            return list
        }

}