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

    fun getChecks(): Single<List<Receipt>> {
        return Single.fromCallable { List(10) { receiptsOffline.random() } }
    }

}