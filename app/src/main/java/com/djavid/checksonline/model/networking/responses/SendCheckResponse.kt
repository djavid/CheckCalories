package com.djavid.checksonline.model.networking.responses

import com.djavid.checksonline.model.entities.Receipt

data class SendCheckResponse(val error: String, val result: Receipt)