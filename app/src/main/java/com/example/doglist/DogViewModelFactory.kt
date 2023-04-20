package com.example.doglist.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doglist.DogViewModel
import com.example.doglist.database.DogDao


class DogViewModelFactory(private val dao: DogDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DogViewModel::class.java)) {
            return DogViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }

}