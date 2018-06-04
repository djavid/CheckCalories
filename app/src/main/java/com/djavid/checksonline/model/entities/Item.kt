package com.djavid.checksonline.model.entities

data class Item(val itemId: Long, val sum: Long, val quantity: Double, val name: String?,
                val nds18: Long, val price: Long, val category: String?)