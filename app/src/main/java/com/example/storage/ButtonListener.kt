package com.example.storage

interface ButtonListener {

    fun delete(id: Int)
    fun edit(id: Int?, name: String?, secondName: String?, age: String?)
}