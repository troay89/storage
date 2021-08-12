package com.example.storage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.storage.databinding.FragmentBlankBinding


class BlankFragment : Fragment() {


    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!

    private val adapter: AdapterPerson = AdapterPerson()
    private var database: SQLiteOpen? = null

    private var listener: ButtonListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = SQLiteOpen(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        database = SQLiteOpen(context)
        adapter.submitList(database?.getListOfTopics())

        binding.create.setOnClickListener {
            val action =
                BlankFragmentDirections.actionBlankFragmentToEditAndAddPerson(null)
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class AdapterPerson : ListAdapter<Person, ViewHolderPerson>(Person.DiffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPerson {
            val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_person, parent, false)
            return ViewHolderPerson(layout)
        }

        override fun onBindViewHolder(holder: ViewHolderPerson, position: Int) {

            val item = getItem(position)

            holder.firstNameText.text = getString(R.string.name, item.firstName)
            holder.secondNameText.text = getString(R.string.name, item.secondName)
            holder.ageText.text = getString(R.string.name, item.age)

            holder.deleteButton.setOnClickListener {
                listener?.delete(item.id)
                adapter.submitList(database?.getListOfTopics())
            }

            holder.edit.setOnClickListener {
                val action = BlankFragmentDirections.actionBlankFragmentToEditAndAddPerson(item)
                holder.view.findNavController().navigate(action)
            }
        }
    }

    private inner class ViewHolderPerson(val view: View) :
        RecyclerView.ViewHolder(view) {

        val firstNameText: TextView = view.findViewById(R.id.first_name)
        val secondNameText: TextView = view.findViewById(R.id.second_name)
        val ageText: TextView = view.findViewById(R.id.age)
        val deleteButton: ImageButton = view.findViewById(R.id.delete)
        val edit: ImageButton = view.findViewById(R.id.edit)
    }
}



