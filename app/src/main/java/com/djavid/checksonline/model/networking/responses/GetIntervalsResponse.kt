package com.djavid.checksonline.model.networking.responses

import com.djavid.checksonline.model.entities.DateInterval

data class GetIntervalsResponse(val error: String, val result: List<DateInterval>)