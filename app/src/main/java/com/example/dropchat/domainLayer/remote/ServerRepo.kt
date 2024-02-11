package com.example.dropchat.domainLayer.remote

import com.example.dropchat.dataLayer.remote.GroupMessage
import com.example.dropchat.dataLayer.remote.GroupProfile
import com.example.dropchat.dataLayer.remote.Message
import com.example.dropchat.dataLayer.remote.Messages
import com.example.dropchat.dataLayer.remote.Profile


interface ServerRepo {

        suspend fun sendProfile(profile : Profile )

        suspend fun getProfiles() : List<Profile>

        suspend fun sendMessage(uniqueId : String ,uniqueIdReverse : String, message : Message)

        suspend fun getMessages(uniqueId : String,uniqueIdReverse : String) : Messages?

        suspend fun sendGroupInfo(groupProfile : GroupProfile)

        suspend fun getGroupInfo(groupName : String) : GroupProfile?

        suspend fun sendGroupMessage(groupName : String, groupMessage: GroupMessage)

        suspend fun getGroupMessages(groupName : String) : GroupMessage?

        suspend fun chatExist(uniqueId : String, uniqueIdReverse : String) : Boolean

}