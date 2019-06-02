package com.djavid.checksonline.model.repositories

import android.content.Context
import com.djavid.checksonline.R
import com.djavid.checksonline.dagger.AppContext
import com.djavid.checksonline.model.entities.Receipt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import io.reactivex.Single
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MockRepository @Inject constructor(
        @AppContext private val appContext: Context
) {

    private val receiptsOffline: List<Receipt> by lazy {
        val collectionType = object : TypeToken<List<Receipt>>() {}.type
        val inputReader = InputStreamReader(appContext.resources.openRawResource(R.raw.checks))
        val jsonReader = JsonReader(inputReader)
        Gson().fromJson<List<Receipt>>(jsonReader, collectionType)
    }

    private val categories = listOf(
            "Чай и сладкое", "Для дома", "Упаковка", "Молочка", "Бакалея", "Дети", "Овощи и фрукты", "Напитки", "Здоровье" +
            "Косметика", "Гастрономия", "Кафе", "Мясо и птица", "Хлеб", "Услуги", "Одежда и обувь", "Рыба", "Машина" +
            "Не определена", "Животные", "Кулинария", "Алкоголь", "Снеки", "Табак", "Компьютер"
    )

    fun getChecks(): Single<List<Receipt>> {
        return Single.fromCallable {
            List(10) {
                receiptsOffline.random().apply {
                    this.items.forEach {
                        it.category = categories.random()
                    }
                }
            }
        }.delay(1000, TimeUnit.MILLISECONDS)
    }

}