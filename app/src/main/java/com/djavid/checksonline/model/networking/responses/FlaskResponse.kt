package com.djavid.checksonline.model.networking.responses

data class FlaskResponse(
        val categories: List<String>,
        val normalized: List<String>
)