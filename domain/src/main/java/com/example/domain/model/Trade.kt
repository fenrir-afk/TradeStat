package com.example.domain.model


data class Trade(
    val id: Int,
    val tradeDirection: String,
    val tradeDate: String,//day of position opening
    val strategy: String,
    val tradeResult: String,
    val instrument: String,
    val ADDate: String, //date of adding to the app
    val description: String
)
