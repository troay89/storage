package com.example.storage

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.storage.databinding.FragmentEditAndAddPersonBinding


class EditAndAddPerson : Fragment() {

    private var _binding: FragmentEditAndAddPersonBinding? = null
    private val binding get() = _binding!!
    private var database: SQLiteOpen? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAndAddPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = SQLiteOpen(context)

        val _id:String = arguments?.getString(ID)!!
        var id: Int? = null
        if (_id.isNotBlank()){
            id = _id.toInt()
        }
        val oldName = SpannableStringBuilder(arguments?.getString(FIRST_NAME) ?: "")
        val oldSecondName = SpannableStringBuilder(arguments?.getString(SECOND_NAME) ?: "")
        val oldAge = SpannableStringBuilder(arguments?.getString(AGE) ?: "")


        binding.editName.text = oldName
        binding.editSecondName.text = oldSecondName
        binding.age.text = oldAge

        fun back() {
            val name = binding.editName.text.toString()
            val secondName = binding.editSecondName.text.toString()
            val age = binding.age.text.toString()

            if (oldName.isBlank() && oldSecondName.isBlank() && oldAge.isBlank()) {
                database?.savePerson(name, secondName, age)
            } else {
                database?.edit(id, name, secondName, age)
            }
        }

        binding.save.setOnClickListener {
            back()
            findNavController().navigate(R.id.action_editAndAddPerson_to_blankFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
            findNavController().navigate(R.id.action_editAndAddPerson_to_blankFragment)
        }
    }

    companion object {
        private const val ID = "ID"
        private const val FIRST_NAME = "FIRST_NAME"
        private const val SECOND_NAME = "SECOND_NAME"
        private const val AGE = "AGE"
    }
}