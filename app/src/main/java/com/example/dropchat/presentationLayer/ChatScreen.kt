package com.example.dropchat.presentationLayer

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chat(
    mainViewModel: MainViewModel,
    nav: NavHostController
) {

    Column {
        TopAppBar(title = {
            Text(text = "My Chat")
        },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.LightGray
            )
            )

       /* Button(onClick = {

        }) {
            Text(text = "Get messages")
        }*/

        LazyColumn() {

           // mainViewModel.getMessages()

            items(mainViewModel.listOfMessages.value.Messages)
            {
              //  mainViewModel.lastMessage.value = mainViewModel.listOfMessages.value.Messages.last().toString()
                Card()
        {
                    Text(text = it.message )

                }
            }
        }
    }
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        /*Button(onClick = {

        }) {
            Text(text = "Image")
        }*/
        TextField(value = mainViewModel.messageText.value,
            onValueChange = {
                mainViewModel.messageText.value = it
            },
            maxLines = 30,
            modifier = Modifier.weight(0.7F,true)

        )



        IconButton(onClick = {
            mainViewModel.message.value = mainViewModel.message.value.copy(
                message = mainViewModel.messageText.value,
            )


            mainViewModel.sendMessage()
            mainViewModel.messageText.value = ""
        }) {
            Icon(imageVector = Icons.Default.Send,
                contentDescription = "Send")
        }

    }
}



