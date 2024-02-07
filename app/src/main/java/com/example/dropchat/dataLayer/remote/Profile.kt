package com.example.dropchat.dataLayer.remote

import java.util.Date

data class Profile(
    var userName : String = "",
    var userMail : String = "",      // convert to mail type later..
    var userBio : String = "",
    var userGender : String = "",     // convert to Gender type later..
    //var userDob : DOB // to be changed into date tupe later..
    var userImage : String = ""
)
