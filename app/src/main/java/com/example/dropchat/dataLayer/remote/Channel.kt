package com.example.dropchat.dataLayer.remote

data class Channel(
    var members : List<String> = listOf(),
    var messages : List<Message> = listOf(),
    var senderId : String = ""

)
