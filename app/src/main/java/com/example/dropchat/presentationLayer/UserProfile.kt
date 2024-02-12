package com.example.dropchat.presentationLayer

import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.dropchat.ui.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfile(
    nav: NavHostController,
    mainViewModel: MainViewModel,
    pickImage: ActivityResultLauncher<PickVisualMediaRequest>,
    image: Uri
) {

    Column() {
        TopAppBar(title = {
            Text(text = "User Profile")
            TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Cyan
            )
        })

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            ) {

                Img(mainViewModel.pickImage.value.toString().toUri())

            }
            Button(onClick = {
                //pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                //mainViewModel.pickImage.value = image
                Log.e("Img", mainViewModel.pickImage.value.toString())
                //Log.e("Img", image.toString())

            }) {
                Text(text = "Upload picture")
            }


            TextField(value = mainViewModel.name.value,
                onValueChange = {
                    mainViewModel.name.value = it
                },
                label = { Text(text = "Name") })

            TextField(value = mainViewModel.profile.value.userMail,
                onValueChange = {
                },
                label = { Text(text = "Gmail") })

            TextField(value = mainViewModel.bio.value,
                onValueChange = {
                    mainViewModel.bio.value = it
                },
                label = { Text(text = "Bio") })

            Box() {
                Column() {
                    Row() {
                        Text(text = "Gender")
                    }
                    Row() {

                        Column() {
                            var options = listOf<String>(
                                mainViewModel.gender.value.male,
                                mainViewModel.gender.value.female
                            )
                            options.forEach {
                                Row() {
                                    RadioButton(selected = mainViewModel.genderSelected.value == it,
                                        onClick = {
                                            mainViewModel.genderSelected.value = it
                                        })
                                    Text(text = it)
                                }
                            }
                        }
                    }
                }
            }

            Row() {
                Button(onClick = { mainViewModel.datePicker() }) {
                    Text(text = "DOB")
                }


            }

            Row() {
                Button(onClick = {

                    mainViewModel.profile.value = mainViewModel.profile.value.copy(
                        userName = mainViewModel.name.value,
                        userBio = mainViewModel.bio.value,
                        userGender = mainViewModel.genderSelected.value,
                        userDob = mainViewModel.dob.value,
                        userImage = mainViewModel.pickImage.value.toString()
                    )

                    mainViewModel.sendProfile()

                    nav.navigate(Screens.ListOfAllUsers.name)


                }) {
                    Text(text = "Save")
                }
            }
            Row(){
                Button(onClick = { nav.navigate(Screens.Chats.name) }) {
                    Text(text = "View Chats")
                }
            }


        }
    }
}

@Composable
fun Img(link: Uri) {
    AsyncImage(
        model = link,
        contentDescription = "Google image",
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
    )
    Log.e("Img", link.toString())
}
