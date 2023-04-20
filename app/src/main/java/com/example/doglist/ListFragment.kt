package com.example.doglist


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.doglist.database.Dog
import com.example.doglist.database.SwipeController
import com.example.doglist.databinding.FragmentListBinding


class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val dogViewModel: DogViewModel by activityViewModels()
    private lateinit var dogAdapter: DogAdapter
    private lateinit var dogList: ArrayList<Dog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    // Adding stuff to change file.
    // Adding more stuff to branch!
    // Adding into demobranch
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        val navController = findNavController()
        val dogAdapter = DogAdapter { dog -> adapterOnClick(dog) }
        dogViewModel.setBreedList()

        // Get the pointer to the recyclerView
        val dogRecyclerView = binding.dogRecyclerView
        dogRecyclerView.adapter = dogAdapter

        dogViewModel.fullDogData.observe(viewLifecycleOwner) {
            it?.let {
                Log.d("==> in ListFrag",dogViewModel.fullDogData.value.toString())
                dogAdapter.submitList(it as MutableList<Dog>)
            }
        }

        val addButton = binding.addButton
        addButton.setOnClickListener {
            navController.navigate(R.id.action_listFragment_to_addFragment)
        }


        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    dogRecyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val dogs = dogViewModel.getAll()
                    val dog = dogs[viewHolder.adapterPosition]
                    dogViewModel.delete(dog)
                }

            }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(dogRecyclerView)

    return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun adapterOnClick(dog: Dog) {
        dogViewModel.setCurrentDog(dog)
        val navController = findNavController()
        navController.navigate(R.id.action_listFragment_to_detailFragment)
    }

companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ListFragment.
     */
    @JvmStatic
    fun newInstance() =
        ListFragment().apply {

        }
    }
}
