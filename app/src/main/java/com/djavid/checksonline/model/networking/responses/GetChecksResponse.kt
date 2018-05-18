package com.djavid.checksonline.model.networking.responses

import com.djavid.checksonline.model.entities.Receipt

data class GetChecksResponse(val error: String, val result: Result) {

    data class Result(val hasNext: Boolean, val receipts: List<Receipt>,

                      val totalDay: Double, val totalWeek: Double,
                      val totalMonth: Double, val totalLastDay: Double,
                      val totalLastWeek: Double, val totalLastMont: Double)

}