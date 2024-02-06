package com.example.dropchat.dataLayer.remote

data class GroupMessage(
    var senderName : String,
    var groupMessages : List<Message> = listOf()
)
