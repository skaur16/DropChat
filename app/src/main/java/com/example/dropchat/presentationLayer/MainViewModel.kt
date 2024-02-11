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
    var dob = mutableStateOf("")
    var genderSelected = mutableStateOf(gender.value.male)
    var messageText = mutableStateOf("")


    var listOfAllUsers = mutableStateOf( mutableListOf(Profile()))
    var listOfChats = mutableStateOf(mutableListOf(Profile()))

    var currentUserId = mutableStateOf("")
    var friendUserId = mutableStateOf("")
    var uniqueId =mutableStateOf( "${currentUserId} and ${friendUserId}")
    var uniqueIdReverse = mutableStateOf("${friendUserId} and ${currentUserId}")


    fun sendProfile() {
        viewModelScope.launch {

            serverRepoRef.sendProfile(profile.value)
        }
    }

    fun datePicker(){
        viewModelScope.launch {
            var date: Int
            var month: Int
            var year: Int

            var calender = Calendar.getInstance()
            date = calender.get(Calendar.DAY_OF_MONTH)
            month = calender.get(Calendar.MONTH)
            year = calender.get(Calendar.YEAR)

            val datePicker = DatePickerDialog(
                Application(),
                { _: DatePicker, year: Int, month: Int, date: Int ->
                    dob.value = "${date}/${month}/${year}"
                }, year, month, date
            )


            datePicker.show()
        }
    }

    fun getAllProfiles(){
        viewModelScope.launch {
            serverRepoRef.getProfiles().also{
                listOfAllUsers.value = it.toMutableList()
            }
        }

    }
    fun getMessages(){
        viewModelScope.launch {
            serverRepoRef.getMessages(uniqueId.value,uniqueIdReverse.value).also{
                if (it != null) {
                    listOfMessages.value = it
                Log.e("List",it.toString())
                }
            }
        }
    }
     fun sendMessage(){
         viewModelScope.launch {
             Log.e("MSG",uniqueId.value)



             serverRepoRef.sendMessage(uniqueId.value,
                                uniqueIdReverse.value,
                                message.value
                 )
         }
     }
}

