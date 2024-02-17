package com.example.dropchat.dataLayer.remote

data class GroupProfile(
    var groupMembers : List<Profile> = listOf(),
    var groupName : String = "",
    var groupDisplay : String = "",
    var groupBio : String = ""
)
