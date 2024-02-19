package com.example.dropchat.presentationLayer

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dropchat.dataLayer.remote.Channel
import com.example.dropchat.dataLayer.remote.ChatList
import com.example.dropchat.dataLayer.remote.Gender
import com.example.dropchat.dataLayer.remote.GroupProfile
import com.example.dropchat.dataLayer.remote.Message
import com.example.dropchat.dataLayer.remote.Messages
import com.example.dropchat.dataLayer.remote.Profile
import com.example.dropchat.domainLayer.remote.ServerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var serverRepoRef: ServerRepo
) : ViewModel() {

    var profile = mutableStateOf(Profile())
    var gender = mutableStateOf(Gender())
    var message = mutableStateOf(Message())
    var listOfMessages = mutableStateOf(Messages())
    var groupProfile = mutableStateOf(GroupProfile())
    var channel = mutableStateOf(Channel())

    var lastMessage = mutableStateOf("")

    var pickImage = mutableStateOf<Uri?>(null)
    var name = mutableStateOf("")
    var bio = mutableStateOf("")
    var dob = mutableStateOf("01/01/1970")
    var genderSelected = mutableStateOf(gender.value.male)
    var messageText = mutableStateOf("")

    var groupImage = mutableStateOf<Uri?>(null)
    var groupName = mutableStateOf("")
    var groupBio = mutableStateOf("")
    var groupMembers = mutableStateOf(mutableListOf(Profile()))
    var isChecked = mutableStateOf<Boolean>(false)



    var listOfAllUsers = mutableStateOf( mutableListOf(Profile()))
    var listOfChats = mutableStateOf(mutableListOf(Profile()))

    var currentUserId = mutableStateOf("")
    var friendUserId = mutableStateOf("")
    var uniqueId = "${currentUserId.value} and ${friendUserId.value}"
    var uniqueIdReverse = "${friendUserId.value} and ${currentUserId.value}"
    var chatExist = mutableStateOf<Boolean>(false)
    var chatList = mutableStateOf(mutableListOf(Channel()))


    fun sendProfile() {
        viewModelScope.launch {

            serverRepoRef.sendProfile(profile.value)
        }
    }



    suspend fun getAllProfiles(){
        viewModelScope.async(Dispatchers.Main){
            serverRepoRef.getProfiles().also{
                listOfAllUsers.value = it.toMutableList()
            }
        }.await()

    }

    suspend fun getMessages(){



        viewModelScope.async(Dispatchers.Main){
            serverRepoRef.getMessages(
                "${currentUserId.value} and ${friendUserId.value}",
                "${friendUserId.value} and ${currentUserId.value}"
            ).also{
                if (it != null) {
                    channel.value = it
                }
            }
        }.await()
    }




     fun sendMessage(){
         viewModelScope.launch {
             Log.e("MSG",uniqueId)



             serverRepoRef.sendMessage(
                 "${currentUserId.value} and ${friendUserId.value}",
                 "${friendUserId.value} and ${currentUserId.value}",
                                channel.value,
                                message.value
                 )
         }
     }

    fun chatExist() {
        viewModelScope.launch {
             serverRepoRef.chatExist(
                 "${currentUserId.value} and ${friendUserId.value}",
                 "${friendUserId.value} and ${currentUserId.value}",
             ).also {
                 chatExist.value = it
                 Log.e("VM",it.toString())
             }
        }
    }

    fun sendGroupInfo(){
        viewModelScope.launch {
            serverRepoRef.sendGroupInfo(groupProfile.value).also {
                Log.e("Group",groupProfile.value.toString())
            }
        }
    }

    suspend fun chatList(){
       val x =  viewModelScope.async { serverRepoRef.chatList(currentUserId.value)?.toList() }.await()
        if(x!=null){
            chatList.value = x as MutableList<Channel>
        }
    }
    }


