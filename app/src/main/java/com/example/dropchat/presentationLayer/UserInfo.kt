package com.example.dropchat.presentationLayer

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfo(
    mainViewModel: MainViewModel,
    nav:NavHostController
) {
    val scope = rememberCoroutineScope()
    scope.launch(Dispatchers.Main) {
        mainViewModel.getAllProfiles()

        mainViewModel.listOfAllUsers.value.forEach {
            if(it.userMail == mainViewModel.currentUserId.value){
                mainViewModel.profile.value = it
            }
        }

    }

    Column(

    ) {


        TopAppBar(title = { Text(text = mainViewModel.profile.value.userName) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.LightGray
            )
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            Box (
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            ){
                AsyncImage(model = mainViewModel.profile.value.userImage.toUri(),
                    contentDescription = "Image",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                    )
            }

            TextField(value = mainViewModel.profile.value.userBio,
                onValueChange ={},
                readOnly = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Info,
                    contentDescription = "Bio")}
                )

            TextField(value = mainViewModel.profile.value.userName,
                onValueChange ={},
                readOnly = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Face,
                    contentDescription = "Name")}
                )

            TextField(value = mainViewModel.profile.value.userDob,
                onValueChange ={},
                readOnly = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.DateRange,
                    contentDescription = "Dob")}
                )



        }
    }
}