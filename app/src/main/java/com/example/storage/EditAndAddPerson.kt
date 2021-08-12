package com.example.storage

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.storage.databinding.FragmentEditAndAddPersonBinding


class EditAndAddPerson : Fragment() {

    private var _binding: FragmentEditAndAddPersonBinding? = null
    private val binding get() = _binding!!
    private var database: SQLiteOpen? = null
    private val args by navArgs<EditAndAddPersonArgs>()

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
        val person = args.person

        val oldName = SpannableStringBuilder(person?.firstName ?: "")
        val oldSecondName = SpannableStringBuilder(person?.secondName ?: "")
        val oldAge = SpannableStringBuilder(person?.age ?: "")


        binding.editName.text = oldName
        binding.editSecondName.text = oldSecondName
        binding.age.text = oldAge

        fun back() {
            val name = binding.editName.text.toString()
            val secondName = binding.editSecondName.text.toString()
            val age = binding.age.text.toString()

            if (person == null) {
                val newPerson = Person(null, name, secondName, age)
                database?.savePerson(newPerson)
            } else {
                val editPerson = Person(person.id, name, secondName, age)
                database?.edit(editPerson)
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
}