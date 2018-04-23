package com.djavid.checksonline.model.networking.responses

data class CheckResponseFns(val document: Document) {

    data class Document(val receipt: Receipt) {

        data class Receipt(
                val receiptId: Long, val items: List<Item>, val operationType: Long,
                val rawData: String, val taxationType: Long, val requestNumber: Long,
                val fiscalDocumentNumber: Long, val operator: String, val fiscalDriveNumber: String,
                val userInn: String, val ecashTotalSum: Long, val kktRegId: String,
                val dateTime: String, val fiscalSign: Long, val user: String,
                val nds18: Long, val receiptCode: Long, val shiftNumber: Long,
                val retailPlaceAddress: String, val totalSum: Long, val cashTotalSum: Long)
        {

            data class Item(val itemId: Long, val sum: Long, val quantity: Double, val name: String,
                            val nds18: Long, val price: Long)

        }
    }

}