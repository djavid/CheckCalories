package com.djavid.checksonline.model.networking.responses

data class SendTokenResponse (val error: String, val token: RegistrationToken) {

    data class RegistrationToken(val id: Long, val token: String,
                                 val created: Long, val lastVisited: Long)

}