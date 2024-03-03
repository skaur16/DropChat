package com.example.dropchat.presentationLayer

import android.content.Context
import android.content.SharedPreferences
import android.net.MailTo
import java.util.jar.Attributes.Name

class SharedPref(context : Context) {

    val sharedPreference : SharedPreferences = context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
    )

    val editor = sharedPreference.edit()

        val nameKey = "Name"
        val mailKey = "Mail"
        val imageKey = "Image"

    var Name
        get() = sharedPreference.getString(nameKey,null)
        set(value) {
                editor.putString(nameKey,value)
                editor.commit()
        }

    var Mail
        get() = sharedPreference.getString(mailKey,null)
        set(value) {
            editor.putString(mailKey,value)
            editor.commit()
        }

    var Image
        get() = sharedPreference.getString(imageKey,null)
        set(value) {
            editor.putString(imageKey,value)
            editor.commit()
        }



}