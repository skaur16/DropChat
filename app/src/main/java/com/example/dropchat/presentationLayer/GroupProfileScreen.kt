package com.example.dropchat.presentationLayer

import android.graphics.drawable.Icon
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.dropchat.dataLayer.remote.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupProfile(
    mainViewModel: MainViewModel,
    nav: NavHostController,
    groupImg : ActivityResultLauncher<PickVisualMediaRequest>
) {

    val scope = rememberCoroutineScope()

    Column {
        TopAppBar(
            title = { Text(text = "Create a Group") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.LightGray
            )
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            Row() {
                Box (
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                ){
                    AsyncImage(
                        model = mainViewModel.groupImage.value,
                        contentDescription = "group Image",
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                    )
                }
                IconButton(onClick = {
                    groupImg.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "GroupProfile"
                    )
                }
            }
            Column {


                TextField(value = mainViewModel.groupName.value,
                    onValueChange = {
                        mainViewModel.groupName.value = it
                    },
                    label = { Text(text = "Group Name") }
                )

                TextField(value = mainViewModel.groupBio.value,
                    onValueChange = {
                        mainViewModel.groupBio.value = it
                    },
                    label = { Text(text = "Group Description") }
                )

                Column {

                    Text(text = "Members")

                    LazyColumn() {
                        scope.launch(Dispatchers.Main) {
                            mainViewModel.getAllProfiles()
                        }

                        items(mainViewModel.listOfAllUsers.value) {
                            Row() {
                                Checkbox(checked = mainViewModel.isChecked.value,
                                    onCheckedChange = {
                                        mainViewModel.isChecked.value = it
                                    })

                                profileCard(it, mainViewModel.isChecked.value, mainViewModel)
                            }
                        }

                    }
                }
            }
        }
        Row (verticalAlignment = Alignment.Bottom){
            Button(onClick = {

                mainViewModel.groupProfile.value = mainViewModel.groupProfile.value.copy(
                        groupName = mainViewModel.groupName.value,
                        groupBio = mainViewModel.groupBio.value,
                        groupDisplay = mainViewModel.groupImage.value.toString(),
                        groupMembers = mainViewModel.groupMembers.value
                    )

                mainViewModel.sendGroupInfo()




            }) {
                Text(text = "Create")
            }
        }

    }
}

@Composable
fun profileCard(profile: Profile , isChecked : Boolean, mainViewModel: MainViewModel) {

    if(isChecked){
        mainViewModel.groupMembers.value.add(profile)
    }

    Card() {
        Row() {
         Column {
             AsyncImage(
                 model = profile.userImage.toUri(),
                 contentDescription = "Profile Image"
             )
         }
            Column {
                Row {
                    Text(text = profile.userName)
                }
            }
        }
    }
}