package com.example.firstapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class BaseViewModel: ViewModel() {
    var errorMessage = MutableLiveData<String>()
}