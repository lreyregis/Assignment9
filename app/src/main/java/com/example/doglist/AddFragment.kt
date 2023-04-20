package com.example.doglist


import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.doglist.database.Dog
import com.example.doglist.databinding.FragmentAddBinding
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


class AddFragment: Fragment() {
    val REQUEST_CODE = 100
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val dogViewModel: DogViewModel by activityViewModels()
    private var replacePlaceholder : Boolean = false
    private lateinit var bitmap : Bitmap

    @Deprecated("")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root
        val navController = findNavController()


        val addSaveButton = binding.addSaveButton
        val addCancelButton = binding.addCancelButton
        val addPhotoButton = binding.addPhotoButton


        addPhotoButton.setOnClickListener {
            openGalleryForImage()
        }


        addSaveButton.setOnClickListener {
            val userInputBreed: String = binding.dogBreedTextView.text.toString()
            val userInputName: String = binding.nameTextView.text.toString()
            val userInputAge: Int = binding.ageTextView.text.toString().toInt()

            var userInputImage = byteArrayOf()
            if (replacePlaceholder) {
                binding.dogPhotoImageView.setImageBitmap(bitmap)
                userInputImage = convertBitmapToByteArray(bitmap)
            }

            val newDog = Dog(0,userInputBreed, userInputName, userInputAge, 0, userInputImage)
            dogViewModel.addDog(newDog)

            navController.navigate(R.id.action_addFragment_to_listFragment)
        }

        addCancelButton.setOnClickListener {
            navController.navigate(R.id.action_addFragment_to_listFragment)
        }

        return view
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

            val dogPhoto = binding.dogPhotoImageView
            val image = data?.data
            val context = context
            bitmap = getBitmap(context!!.contentResolver,image)
            binding.dogPhotoImageView.setImageBitmap(bitmap)
            replacePlaceholder = true

    }

    fun convertBitmapToByteArray(bitmap: Bitmap) : ByteArray {
        val out = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, out)
        return out.toByteArray()
    }

    fun getBitmap(contentResolver: ContentResolver, fileUri: Uri?): Bitmap{
        val bmap: Bitmap
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            bmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, fileUri!!))
        } else {
            bmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
        }
        return bmap
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment AddFragment.
         */

        @JvmStatic
        fun newInstance() =
            AddFragment().apply {
            }
    }
}