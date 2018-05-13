package com.djavid.checksonline.utils

import com.google.gson.JsonObject
import java.io.*

class Config {

    companion object {
        val labels = arrayOf("Алкоголь", "Бакалея", "Гастрономия", "Дети", "Для дома",
                "Животные", "Здоровье", "Кафе", "Компьютер", "Косметика",
                "Кулинария", "Машина", "Молочка", "Мясо и птица", "Напитки",
                "Не определена", "Овощи и фрукты", "Одежда и обувь", "Рыба",
                "Снеки", "Табак", "Упаковка", "Услуги", "Хлеб", "Чай и сладкое")

        fun saveJsonToFile(jsonObject: JsonObject, showMessage: (msg: String) -> Unit) {

            try {
                var output: Writer? = null
                val fileJson = File("/sdcard/jsons.json")

                if (!fileJson.exists()) {
                    fileJson.createNewFile();
                    output = BufferedWriter(FileWriter(fileJson, true))
                    output.append("[")
                }

                if (output == null) output = BufferedWriter(FileWriter(fileJson, true))
                val pr = PrintWriter(output)

                pr.println(jsonObject.toString() + ",\n")
                pr.flush()
                pr.close()

                showMessage("Written to file")
            } catch (e : Exception) {
                e.printStackTrace()
                showMessage("Error writing to file!")
            }
        }

//        try {
//            val body = it.string()
//            val receiptJsonObject = JsonParser().parse(body)
//                    .asJsonObject.get("document")
//                    .asJsonObject.get("receipt").asJsonObject
//
//            Config.saveJsonToFile(receiptJsonObject, viewState::showMessage)
//
//            val receipt: Receipt = GsonBuilder().create()
//                    .fromJson(receiptJsonObject, Receipt::class.java)
//            sendCheck(receipt)
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            viewState.showMessage("Invalid JSON")
//            inProcess = false
//        }
    }
}