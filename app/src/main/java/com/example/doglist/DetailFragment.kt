package com.example.doglist

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.doglist.database.Dog
import com.example.doglist.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar



class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val dogViewModel: DogViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val binding = _binding!!
        val view = binding.root

        binding.dogViewModel = dogViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val navController = findNavController()

        // Let's observe the current dog values
        dogViewModel.dog.observe(viewLifecycleOwner, Observer { newValue ->
        })

        // Buttons for the detail section
        // ----------------------------------------------------------------------------------------
        val editButton = binding.detailEditButton
        val backToListButton = binding.detailBackToListButton

        // Buttons to edit or cancel
        editButton.setOnClickListener() {
            // Turn the edit form's visibility on
            setFieldsVisibility(VISIBLE)

            val currentDog = dogViewModel.dog.value!!
            binding.editTextBreed.setText(currentDog.breed.toString())
            binding.editTextDogName.setText(currentDog.name.toString())
            binding.editTextAge.setText(currentDog.age.toString())

        }
        backToListButton.setOnClickListener() {
            navController.navigate(R.id.action_detailFragment_to_listFragment)
        }

        // Buttons for the edit section
        // ----------------------------------------------------------------------------------------
        val saveButton2 = binding.saveChangesButton
        val cancelButton2 = binding.cancelChangesButton

        saveButton2.setOnClickListener() {
            // The user wants to save the information.  Create a SnackBar verifying that they
            // do indeed want to save the data.
            val snackBar =                                   // Create a SnackBar
                Snackbar.make(
                    saveButton2, R.string.dialogue_text,
                    BaseTransientBottomBar.LENGTH_INDEFINITE
                )
                    .setBackgroundTint(Color.DKGRAY)
                    .setTextColor(Color.WHITE)
                    .setAction(R.string.yes) {
                        // The user wants to save the data so retrieve it from the form
                        val id = binding.idTextView.text.toString().toInt()
                        val dogBreed = binding.editTextBreed.text.toString()
                        val name = binding.editTextDogName.text.toString()
                        val age = binding.editTextAge.text.toString().toInt()
                        val image = dogViewModel.dog.value!!.imageName

                        //Update the values in the dog List as well as the LiveData variables
                        val updatedDog: Dog = Dog(id, dogBreed, name, age, image)
                        dogViewModel.updateDog(updatedDog)

                        // Turn the edit form's visibility off
                        setFieldsVisibility(INVISIBLE)
                    }
                    .show()
        }

        cancelButton2.setOnClickListener() {
            setFieldsVisibility(INVISIBLE)
        }

        return view
    }
    fun setFieldsVisibility (state:Int) {
        // Turn the edit form's visibility on
        binding.titleEditTextView.setVisibility(state)
        binding.editTextBreed.setVisibility(state)
        binding.editTextDogName.setVisibility(state)
        binding.editTextAge.setVisibility(state)
        binding.dividerDetailPage.setVisibility(state)
        binding.saveChangesButton.setVisibility(state)
        binding.cancelChangesButton.setVisibility(state)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment DetailFragment.
         */

        @JvmStatic
        fun newInstance() =
            DetailFragment().apply {

            }
    }
}