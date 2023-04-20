package com.example.doglist

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doglist.database.Dog


class DogAdapter(private val onClick: (Dog) -> Unit) :
    ListAdapter<Dog, DogAdapter.DogViewHolder>(DogDiffItemCallback) {

    class DogViewHolder(itemView: View, val onClick: (Dog) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val dogBreedTextView: TextView = itemView.findViewById(R.id.eachDogBreedTextView)
        private val dogNameTextView: TextView = itemView.findViewById(R.id.eachDogNameTextView)
        private val dogImageView: ImageView = itemView.findViewById(R.id.eachDogImageView)
        private var currentDog: Dog? = null

        init {
            itemView.setOnClickListener {
                currentDog?.let {
                    onClick(it)
                }
            }
        }

        fun bind(dog: Dog) {
            currentDog = dog
            dogBreedTextView.text = dog.breed
            dogNameTextView.text = dog.name

            if (dog.bitMapName != null) {
                val length = dog.bitMapName!!.size
                Log.d("==> length is ", "$length")
                val bmp = BitmapFactory.decodeByteArray(dog.bitMapName, 0, length)
                dogImageView.setImageBitmap(bmp)
            } else {
                if (dog.imageName != null) {
                    dogImageView.setImageResource(dog.imageName)
                } else {
                    dogImageView.setImageResource(R.drawable.dogpaw)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return DogViewHolder(view, onClick)
    }


    override fun onBindViewHolder(holder:DogViewHolder, position: Int) {
        val dog = getItem(position)
        holder.bind(dog)
    }
}
