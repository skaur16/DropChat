package com.example.dropchat.data_layer.remote

import java.util.Date

data class Profile(
    var userName : String,
    var userMail : String,      // convert to mail type later..
    var userBio : String,
    //var userGender :    ,     // convert to Gender type later..
    var userDob : Date
)
