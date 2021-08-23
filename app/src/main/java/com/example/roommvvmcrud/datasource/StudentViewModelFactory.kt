package com.example.roommvvmcrud.datasource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class StudentViewModelFactory(private val studentRepository: StudentRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            StudentViewModel(this.studentRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}