package com.example.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storage.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity(), BlankFragment.SavesCallBack, EditAndAddPerson.BlankCallBack {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.container, BlankFragment()).commit()
    }

    override fun newPerson(id: Int?, name: String?, secondName: String?, age: String?) {
        supportFragmentManager.beginTransaction().replace(R.id.container, EditAndAddPerson.newInstance(id, name, secondName, age)).commit()
    }

    override fun result() {
        supportFragmentManager.beginTransaction().replace(R.id.container, BlankFragment()).commit()
    }
}