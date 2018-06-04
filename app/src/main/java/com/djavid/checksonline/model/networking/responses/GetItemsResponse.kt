package com.djavid.checksonline.model.networking.responses

import com.djavid.checksonline.model.entities.Receipt

data class GetItemsResponse(val error: String, val result: Result?) {

    data class Result(val hasNext: Boolean, val receipts: List<Receipt.Item>)

}