package com.example.doglist

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.doglist.database.Dog

object DogDiffItemCallback : DiffUtil.ItemCallback<Dog>() {
    init {
       // Log.d("==>"," in DogDiffItemCallback")
    }

    override fun areItemsTheSame(oldDog: Dog, newDog: Dog) : Boolean {
       // Log.d("==> items same?", "oldDog is $oldDog.toString() and newDog is  $newDog.toString()")
        return oldDog == newDog
    }
    override fun areContentsTheSame(oldDog: Dog, newDog: Dog) : Boolean {
       // Log.d("==> ids same?", "oldDog is $oldDog.id.toString() and newDog is  $newDog.id.toString()")
        return oldDog.id == newDog.id
    }
}
