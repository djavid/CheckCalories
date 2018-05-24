package com.djavid.checksonline.model.networking.responses

import com.djavid.checksonline.model.entities.Percentage

data class StatPercentResponse (val error: String, val result: Result) {

    data class Result(val categories: List<Percentage>, val shops: List<Percentage>, val totalSum: Double)

}