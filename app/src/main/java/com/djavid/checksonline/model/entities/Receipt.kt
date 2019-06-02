package com.djavid.checksonline.model.entities

import org.joda.time.DateTime
import java.io.Serializable
import java.util.*

data class Receipt(
        val receiptId: Long, val tokenId: Long, val created: Long, val logo: String?,
        val isEmpty: Boolean, val items: List<Item>,
        val operationType: Long, val rawData: String?, val taxationType: Long,
        val requestNumber: Long, val operator: String?,
        val userInn: String?, val ecashTotalSum: Long,
        val kktRegId: String?, val dateTime: String?, val user: String?,
        val nds18: Long, val receiptCode: Long, val shiftNumber: Long,
        val retailPlaceAddress: String?, val totalSum: Long, val cashTotalSum: Long,
        val fiscalDriveNumber: String?, val fiscalDocumentNumber: String?, val fiscalSign: String?) : Serializable {

    override fun toString(): String {
        var s = ""

        s += "Чек на сумму " + "%.2f ₽".format(Locale.ROOT, totalSum / 100f)

        if (user != null && user.isNotEmpty())
            s += " из магазина \"" + user + "\"\n"

        val date = DateTime.parse(dateTime)
        if (date != null)
            s += "Дата покупки: " + date.toString("dd/MM/yyyy HH:mm") + "\n\n"

        if (items.isNotEmpty()) {
            s += "Товары:\n\n"

            items.forEach {
                s += it.name + " за " + "%.2f ₽".format(Locale.ROOT, it.sum / 100f) + "\n"
                s += "Категория: " + it.category + "\n\n"
            }
        }

        return s
    }

}