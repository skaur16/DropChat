package com.example.dropchat.presentationLayer

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.dropchat.R
import com.example.dropchat.dataLayer.remote.Profile
import com.example.dropchat.ui.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chats(mainViewModel: MainViewModel,nav: NavHostController) {

     val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit){
            mainViewModel.getAllProfiles()
           mainViewModel.chatExist()
    }

    Column {
        TopAppBar(title = {
            Text(text = "Home")
                          },
           colors =  TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.LightGray
            ),
            actions = {
                IconButton(onClick = {
                    nav.navigate(Screens.UserInfo.name)
                }) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile"
                    )
                }
            }
            )

        LazyColumn() {


        }
        }


        Column(
           horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        )
        {

            Row(

            ) {
                Column {
                    Row {

                        IconButton(onClick = { nav.navigate(Screens.ListOfAllUsers.name) }) {
                            Icon(imageVector = Icons.Default.Add,
                                contentDescription = "New Chats")
                        }

                    }
                    Row {

                        IconButton(onClick = {
                            nav.navigate(Screens.GroupProfileScreen.name)
                        }) {
                            Icon(painter = painterResource(R.drawable.group),
                                contentDescription = "Group")
                        }
                    }
                }
            }
        }
    }

@Composable
fun ChatCard(profile : Profile, mainViewModel: MainViewModel,nav: NavHostController, lastMessage: String) {
    Card(
        modifier = Modifier.clickable {
            mainViewModel.friendUserId.value = profile.userMail

            nav.navigate(Screens.ChatScreen.name)
        }
    ){
        Row(){
        Column {
            AsyncImage(model = profile.userImage.toUri() ,
                contentDescription = "IMG",
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                )
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