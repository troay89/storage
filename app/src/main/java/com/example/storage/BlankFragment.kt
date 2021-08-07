package com.example.storage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.storage.databinding.FragmentBlankBinding
import com.example.storage.databinding.ItemPersonBinding


class BlankFragment: Fragment() {

    interface SavesCallBack {
        fun newPerson(id: Int?, name: String?, secondName: String?, age: String?)
    }

    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!

    private val adapter: AdapterPerson = AdapterPerson()
    private var database: SQLiteOpen? = null

    private var listener: ButtonListener? = null
    private var callback: SavesCallBack? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as SavesCallBack
        listener = SQLiteOpen(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = SQLiteOpen(context)
        adapter.submitList(database?.getListOfTopics())
        binding.create.setOnClickListener {
            callback?.newPerson(null, null, null, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        callback = null
        super.onDestroy()
    }

    private inner class AdapterPerson : ListAdapter<Person, ViewHolderPerson>(Person.DiffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPerson {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = ItemPersonBinding.inflate(layoutInflater, parent, false)
            return ViewHolderPerson(view)
        }

        override fun onBindViewHolder(holder: ViewHolderPerson, position: Int) {
            holder.bind(getItem(position))
        }

    }

    private inner class ViewHolderPerson(val view: ItemPersonBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(person: Person) {
            view.firstName.text = getString(R.string.name, person.firstName)
            view.secondName.text = getString(R.string.secondName, person.secondName)
            view.age.text = getString(R.string.age, person.age)
            view.delete.setOnClickListener {
                listener?.delete(person.id)
                adapter.submitList(database?.getListOfTopics())
            }
            view.edit.setOnClickListener {
                callback?.newPerson(person.id, person.firstName, person.secondName, person.age)
            }
        }
    }
}



