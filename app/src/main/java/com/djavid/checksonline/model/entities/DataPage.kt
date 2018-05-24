package com.djavid.checksonline.model.entities

data class DataPage<out T>(val items: List<T>, val hasNext: Boolean = false)