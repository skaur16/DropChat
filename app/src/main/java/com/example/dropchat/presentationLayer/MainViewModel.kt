package com.example.dropchat.presentationLayer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dropchat.dataLayer.remote.Profile
import com.example.dropchat.domainLayer.remote.ServerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var serverRepoRef : ServerRepo
)
    : ViewModel()
{

        var profile = mutableStateOf(Profile())



}