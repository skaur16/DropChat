package com.example.dropchat.presentationLayer

import android.app.Application
import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.dropchat.R
import com.example.dropchat.ui.Screens
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfile(
    nav: NavHostController,
    mainViewModel: MainViewModel,
    pickImage: ActivityResultLauncher<PickVisualMediaRequest>

) {

    Column() {
        TopAppBar(
            title = {
            Text(text = "User Profile")
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                                    containerColor = Color.LightGray)
            )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                ) {

                    mainViewModel.pickImage.value?.let {
                        Img(it)
                    }

                }
                IconButton(onClick = {
                    pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "ImagePicker"
                    )
                }

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

            Box(

            ) {
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
                var context = LocalContext.current
                var date: Int
                var month: Int
                var year: Int

                var calender = Calendar.getInstance()
                date = calender.get(Calendar.DAY_OF_MONTH)
                month = calender.get(Calendar.MONTH)
                year = calender.get(Calendar.YEAR)

                val datePicker = DatePickerDialog(
                    context,
                    { _: DatePicker, year: Int, month: Int, date: Int ->
                        mainViewModel.dob.value = "${date}/${month + 1}/${year}"
                    }, year, month, date
                )




                TextField(value = mainViewModel.dob.value,
                    onValueChange = {},
                    label = { Text(text = "DOB") }

                )

                IconButton(onClick = {
                    datePicker.show()
                }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "DatePicker"
                    )
                }


            }

            Spacer(modifier = Modifier.height(30.dp))

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

                    //nav.navigate(Screens.Chats.name)
                    nav.navigate(Screens.ListOfAllUsers.name)


                }) {
                    Text(text = "Save")
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
