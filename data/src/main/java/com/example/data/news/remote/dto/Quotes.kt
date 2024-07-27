package com.example.data.news.remote.dto

data class Quotes(
    val status: Boolean,
    val code: Int,
    val msg: String,
    val response: List<ResponseItem>,
    val info: Info
)

data class ResponseItem(
    val id: String,
    val o: String,
    val h: String,
    val l: String,
    val c: String,
    val t: String,
    val up: String,
    val ch: String,
    val cp: String,
    val s: String,
    val tm: String
)

data class Info(
    val server_time: String,
    val credit_count: Int
)