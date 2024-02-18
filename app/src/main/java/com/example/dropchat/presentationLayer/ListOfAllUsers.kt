package com.example.dropchat.presentationLayer

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.dropchat.dataLayer.remote.Profile
import com.example.dropchat.ui.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfAllUsers(
    mainViewModel: MainViewModel,
    nav : NavHostController
) {

    val  scope = rememberCoroutineScope()

    scope.launch(Dispatchers.Main) {
        mainViewModel.getAllProfiles()
    }

        Column {


            TopAppBar(title = {
                Text(text = "All Users")
            },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.LightGray
                ))


            LazyColumn() {
                items(mainViewModel.listOfAllUsers.value) {
                    profileCard(it, mainViewModel, nav)
                }
            }
        }

}

@Composable
fun profileCard(profile : Profile,
                mainViewModel: MainViewModel,
                nav: NavHostController
                ) {
    Card (
        modifier = Modifier.clickable {
            mainViewModel.friendUserId.value = profile.userMail

            mainViewModel.channel.value = mainViewModel.channel.value.copy(
            members = listOf(
                profile.userMail,
                mainViewModel.currentUserId.value
            )
            )

            nav.navigate(Screens.ChatScreen.name)
        }
    )

    {
        Row(){
            AsyncImage(model = profile.userImage.toUri(),
                contentDescription = "Img",
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                )

            Text(text=profile.userName)

        }
    }
}


