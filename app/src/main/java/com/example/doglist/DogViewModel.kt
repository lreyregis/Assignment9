package com.example.doglist

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.doglist.database.Dog
import com.example.doglist.database.DogDao
import com.example.doglist.database.DogDatabase
import com.example.doglist.viewmodels.DogViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext
import com.example.doglist.DogAdapter

public class DogViewModel(var dao: DogDao) : ViewModel() {

    var dogBreeds = arrayListOf<String>()

    private var _fullDogData = MutableLiveData<List<Dog>>(null)
    val fullDogData: LiveData<List<Dog>>
        get() = _fullDogData

    private var _dog = MutableLiveData<Dog>(null)
    val dog: LiveData<Dog>
        get() = _dog


    init {
        initDogData()
    }

    fun initDogData() {
        val dogData = mutableListOf<Dog>()
        deleteAll() // Remove all the data in the database so we don't have duplicate
        // sets of data added to the database when testing

        dogData.add(Dog(breed="Golden Retriever", name="Marty", age=2, imageName = R.drawable.golden))
        dogData.add(Dog(breed="German Shepherd", name="Scruffy", age=4, imageName = R.drawable.germanshepard))
        dogData.add(Dog(breed="Basset Hound", name="Lucy", age=8, imageName = R.drawable.bassethound))
        dogData.add(Dog(breed="Labrador Retriever", name="Angust", age=1, imageName = R.drawable.labradorretriever))
        dogData.add(Dog(breed="Cocker Spaniel", name="Kobe",age= 5, imageName = R.drawable.cockerspaniel))
        dogData.add(Dog(breed="Poodle", name="Rolo", age=2, imageName = R.drawable.poodle))
        dogData.add(Dog(breed="Irish Setter", name="Damian",age=1, imageName = R.drawable.irishsetter))
        dogData.add(Dog(breed="Bulldog", name="Sam", age=9, imageName = R.drawable.bulldog))
        dogData.add(Dog(breed="Mastiff", name="Mac", age=6, imageName = R.drawable.mastiff))
        dogData.add(Dog(breed="Pug", name="Snidely",age=8, imageName = R.drawable.pug))
        dogData.add(Dog(breed="Irish Wolfhound", name="Swift",age=4, imageName = R.drawable.irishwolfhound))
        dogData.add(Dog(breed="Teacup Poodle", name="Tinkerbell", age=5, imageName = R.drawable.teacuppoodle))
        dogData.add(Dog(breed="Shitzu", name="Trevor", age=7, imageName = R.drawable.shitzu))
        dogData.add(Dog(breed="Mixed Breed", name="Alex",age=9, imageName = R.drawable.mutt))
        dogData.add(Dog(breed="Beagle", name="Bella", age=3, imageName = R.drawable.beagle))
        loadDogs(dogData)
        _fullDogData.value = getAll()
        setBreedList()
    }

    fun loadDogs(initialDogData: MutableList<Dog>) {
        runBlocking {
            launch {
                for (eachDog in initialDogData) {
                    dao.insert(eachDog)
                }
            }
        }
    }

    fun getAll(): MutableList<Dog> {
        var dogs = mutableListOf<Dog>()
        runBlocking {
            launch {
                dogs = dao.getAll()
            }
        }
        return dogs
    }

    fun setCurrentDog(dog: Dog) {
        runBlocking {
            viewModelScope.launch {
                val currentDog = dao.get(dog.id)
                _dog.value = currentDog
            }
        }
    }

    fun addDog(newDog: Dog) {
        _dog.value = newDog
            viewModelScope.launch {
                dao.insert(newDog)
                _fullDogData.value = dao.getAll()
                //Log.d("==> in addDog", "{${fullDogData.value.toString()}")
            }
    }

    fun updateDog(updatedDog:Dog) {
            viewModelScope.launch {
                dao.update(updatedDog)
                _fullDogData.value = dao.getAll()
            }
        _dog.value = updatedDog
    }

    private fun deleteAll(){
        runBlocking {
            viewModelScope.launch {
                val rowsDeleted: Int = dao.deleteAll()
                Log.d("==> rowsDeleted is ", rowsDeleted.toString())
            }
        }
    }


    fun delete(dog: Dog){
        viewModelScope.launch {
            dao.delete(dog)
            _fullDogData.value = dao.getAll()
        }
    }

    fun setBreedList() {
        dogBreeds.clear()
        val dogsIterator = fullDogData.value?.iterator()
        if (dogsIterator != null) {
            while (dogsIterator.hasNext()) {
                dogBreeds.add(dogsIterator.next().breed)
            }
        }
    }



}





