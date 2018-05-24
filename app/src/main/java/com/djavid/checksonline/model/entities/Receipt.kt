package com.djavid.checksonline.model.entities

data class Receipt(
        val receiptId: Long, val tokenId: Long, val created: Long, val logo: String?,
        val isEmpty: Boolean, val items: List<Item>,
        val operationType: Long, val rawData: String?, val taxationType: Long,
        val requestNumber: Long, val operator: String?,
        val userInn: String?, val ecashTotalSum: Long,
        val kktRegId: String?, val dateTime: String?, val user: String?,
        val nds18: Long, val receiptCode: Long, val shiftNumber: Long,
        val retailPlaceAddress: String?, val totalSum: Long, val cashTotalSum: Long,
        val fiscalDriveNumber: String?, val fiscalDocumentNumber: String?, val fiscalSign: String?)
{

    data class Item(val itemId: Long, val sum: Long, val quantity: Double, val name: String?,
                    val nds18: Long, val price: Long, val category: String?)

}