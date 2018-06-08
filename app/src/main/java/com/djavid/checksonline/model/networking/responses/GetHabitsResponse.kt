package com.djavid.checksonline.model.networking.responses

import com.djavid.checksonline.model.entities.Receipt

data class GetHabitsResponse(val error: String, val result: Result) {

    data class Result(val popularShop: PopularShop?, val popularItem: PopularItem?,
                      val mostExpensiveItem: PopularItem?, val mostExpensiveCheck: Receipt?) {

        data class PopularShop(val title: String?, val address: String?, val sum: Double,
                               val empty: Boolean)

        data class PopularItem(val title: String?, val shop: String?, val price: Double,
                               val totalSum: Double, val quantity: Double, val empty: Boolean)

    }

}