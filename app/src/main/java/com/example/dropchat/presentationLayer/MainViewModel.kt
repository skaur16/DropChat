package com.example.dropchat.presentationLayer

import android.app.Application
import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dropchat.dataLayer.remote.Gender
import com.example.dropchat.dataLayer.remote.LastMessage
import com.example.dropchat.dataLayer.remote.Message
import com.example.dropchat.dataLayer.remote.Messages
import com.example.dropchat.dataLayer.remote.Profile
import com.example.dropchat.domainLayer.remote.ServerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var serverRepoRef: ServerRepo
) : ViewModel() {

    var profile = mutableStateOf(Profile())
    var gender = mutableStateOf(Gender())
    var message = mutableStateOf(Message())
    var listOfMessages = mutableStateOf(Messages())
    var lastMessage = mutableStateOf("")

    var pickImage = mutableStateOf<Uri?>(null)
    var name = mutableStateOf("")
    var bio = mutableStateOf("")
    var dob = mutableStateOf("01/01/1970")
    var genderSelected = mutableStateOf(gender.value.male)
    var messageText = mutableStateOf("")



    var listOfAllUsers = mutableStateOf( mutableListOf(Profile()))
    var listOfChats = mutableStateOf(mutableListOf(Profile()))

    var currentUserId = mutableStateOf("")
    var friendUserId = mutableStateOf("")
    var uniqueId = "${currentUserId.value} and ${friendUserId.value}"
    var uniqueIdReverse = "${friendUserId.value} and ${currentUserId.value}"
    var chatExist = mutableStateOf<Boolean>(false)


    fun sendProfile() {
        viewModelScope.launch {

            serverRepoRef.sendProfile(profile.value)
        }
    }



    fun getAllProfiles(){
        viewModelScope.launch {
            serverRepoRef.getProfiles().also{
                listOfAllUsers.value = it.toMutableList()
            }
        }

    }
   suspend fun getMessages(){



        viewModelScope.async(Dispatchers.Main){
            serverRepoRef.getMessages(
                "${currentUserId.value} and ${friendUserId.value}",
                "${friendUserId.value} and ${currentUserId.value}"
            ).also{
                if (it != null) {
                    listOfMessages.value = it
                Log.e("List",it.toString())
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
}

