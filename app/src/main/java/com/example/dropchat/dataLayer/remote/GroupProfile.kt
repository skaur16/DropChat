package com.example.dropchat.dataLayer.remote

data class GroupProfile(
    var groupMembers : List<Profile>,
    var groupName : String,
    var groupDisplay : String
)
