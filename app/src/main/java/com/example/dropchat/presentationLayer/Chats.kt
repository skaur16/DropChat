package com.example.dropchat.presentationLayer

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.dropchat.dataLayer.remote.LastMessage
import com.example.dropchat.dataLayer.remote.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chats(mainViewModel: MainViewModel,nav: NavHostController) {

    val scope = rememberCoroutineScope()

    Column {
        TopAppBar(title = {
            Text(text = "Chats")
            TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Cyan
            )
        })

        LazyColumn() {
            mainViewModel.getAllProfiles()
            items(mainViewModel.listOfAllUsers.value) {

                mainViewModel.friendUserId.value = it.userMail


                Log.e("LAST33",mainViewModel.friendUserId.value )
                Log.e("LAST34",mainViewModel.chatExist.value.toString())
                Log.e("LAST35","${mainViewModel.currentUserId.value} and ${mainViewModel.friendUserId.value}")



                mainViewModel.chatExist()
                if (mainViewModel.chatExist.value) {


                    scope.launch(Dispatchers.Main) {
                       withContext(Dispatchers.Main) {
                           mainViewModel.getMessages()
                       }
                        Log.e("TAG",mainViewModel.listOfMessages.value.Messages.lastIndex.toString())
                        Log.e("TAG2",mainViewModel.listOfMessages.value.Messages.size.toString())
                        Log.e("LAST37",mainViewModel.listOfMessages.value.Messages.last().message)
                        mainViewModel.lastMessage.value = mainViewModel.listOfMessages.value.Messages.last().message
                    }




                    chatCard(it, mainViewModel , nav , mainViewModel.lastMessage.value )
                }
            }
        }


    }
}

@Composable
fun chatCard(profile : Profile,mainViewModel: MainViewModel,nav: NavHostController, lastMessage: String) {
    Card(){
        Row(){
        Column {
            AsyncImage(model = profile.userImage.toUri() ,
                contentDescription = "IMG")
        }
            Column {
                Row(){
                    Text(text = profile.userName)
                }
                Row(){
                    Text(text=lastMessage)
                }
            }
        }
    }
}