package com.example.doglist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.doglist.database.DogDatabase
import com.example.doglist.database.DogDatabase.Companion.getInstance
import com.example.doglist.viewmodels.DogViewModelFactory


class LandingScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_screen)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentContainerView) as NavHostFragment

        val dao = DogDatabase.getInstance(application).dogDao
        val dogViewModelFactory = DogViewModelFactory(dao)
        val dogViewModel = ViewModelProvider(this, dogViewModelFactory).get(DogViewModel::class.java)
    }
}
