package com.brainnit.test.model

import com.google.gson.annotations.SerializedName

data class ResponseModelItem(

    @field:SerializedName("date")
    val date: String? = null,
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("link")
    val link: String? = null,
    @field:SerializedName("source_url")
    val source_url: String? = null,
    @field:SerializedName("status")
    val status: String? = null,
    @field:SerializedName("title")
    val title: Title? = null,
)

data class Title(
    val rendered: String
)