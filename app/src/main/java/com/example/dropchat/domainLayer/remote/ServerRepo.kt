package com.example.dropchat.domainLayer.remote

import com.example.dropchat.dataLayer.remote.GroupProfile
import com.example.dropchat.dataLayer.remote.Message
import com.example.dropchat.dataLayer.remote.Profile


interface ServerRepo {

        suspend fun sendProfile(profile : Profile)

        suspend fun getProfiles() : List<Profile>

        suspend fun sendMessage(uniqueId : String , message : Message)

        suspend fun getMessages(uniqueId : String) : List<Message>

        suspend fun sendGroupInfo(groupProfile : GroupProfile)

        suspend fun getGroupInfo(groupName : String) : GroupProfile

        suspend fun sendGroupMessage(groupName : String, message: Message , senderName : String)

        suspend fun getGroupMessages(groupName : String) : List<Message>

}