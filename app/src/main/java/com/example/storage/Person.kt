package com.example.storage

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize


@Parcelize
data class Person
    (
    val id: Int?,
    val firstName: String,
    val secondName: String,
    val age: String
): Parcelable {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person) =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.firstName == newItem.firstName &&
                        oldItem.secondName == newItem.secondName &&
                        oldItem.age == newItem.age
            }

        }
    }
}


