package com.example.storage

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.storage.databinding.FragmentEditAndAddPersonBinding


class EditAndAddPerson : Fragment() {

    interface BlankCallBack {
        fun result()
    }

    private var callback: BlankCallBack? = null
    private var _binding: FragmentEditAndAddPersonBinding? = null
    private val binding get() = _binding!!
    private var database: SQLiteOpen? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as BlankCallBack
    }

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

        val id = arguments?.getInt(ID)
        val oldName = SpannableStringBuilder(arguments?.getString(FIRST_NAME)?: "")
        val oldSecondName = SpannableStringBuilder(arguments?.getString(SECOND_NAME)?: "")
        val oldAge = SpannableStringBuilder(arguments?.getString(AGE)?: "")


        binding.editName.text = oldName
        binding.editSecondName.text = oldSecondName
        binding.age.text = oldAge

        fun back() {

            val name = binding.editName.text.toString()
            val secondName = binding.editSecondName.text.toString()
            val age = binding.age.text.toString()

            if (oldName.isBlank() && oldSecondName.isBlank() && oldAge.isBlank()) {
                database?.savePerson(name, secondName, age)
            }else {
                database?.edit(id, name, secondName, age)
            }
                callback?.result()
        }

        binding.save.setOnClickListener {
            back()
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(id:Int?, name: String?, secondName: String?, age: String?): EditAndAddPerson {
            val fragment = EditAndAddPerson()
            val args = Bundle()
            if (id != null) {
                args.putInt(ID, id)
            }
            args.putString(FIRST_NAME, name)
            args.putString(SECOND_NAME, secondName)
            args.putString(AGE, age)
            fragment.arguments = args
            return fragment
        }
        private const val ID = "ID"
        private const val FIRST_NAME = "FIRST_NAME"
        private const val SECOND_NAME = "SECOND_NAME"
        private const val AGE = "AGE"
    }
}