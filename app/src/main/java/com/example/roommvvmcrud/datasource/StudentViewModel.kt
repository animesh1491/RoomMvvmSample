package com.example.roommvvmcrud.datasource

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roommvvmcrud.database.Student
import kotlinx.coroutines.launch

class StudentViewModel(private val studentRepository: StudentRepository) : ViewModel(), Observable {

    val studentList = studentRepository.studentData

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearOrDeleteButtonText = MutableLiveData<String>()

    private var isUpdateOrDelete = false
    private lateinit var studentToUpdateDelete : Student

    init {
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if(isUpdateOrDelete) {
            studentToUpdateDelete.name = inputName.value!!
            studentToUpdateDelete.email = inputEmail.value!!
            updateStudent(studentToUpdateDelete)
        }
        else {
            insertStudents(Student(0, inputName.value!!, inputEmail.value!!))
            inputName.value = ""
            inputEmail.value = ""
        }
    }

    fun clearAllOrDelete() {
        if(isUpdateOrDelete) {
            deleteStudent(studentToUpdateDelete)
        }
        else {
            deleteAllStudents()
        }
    }

    private fun insertStudents(student: Student) = viewModelScope.launch {
        studentRepository.insertStudentRecord(student)
    }

    private fun updateStudent(student: Student) = viewModelScope.launch {
        studentRepository.updateStudentRecord(student)
        inputName.value = ""
        inputEmail.value = ""
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    private fun deleteStudent(student: Student) = viewModelScope.launch {
        studentRepository.deleteStudentRecord(student)
        inputName.value = ""
        inputEmail.value = ""
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    private fun deleteAllStudents() = viewModelScope.launch {
        studentRepository.deleteAllRecords()
    }

    fun showSelectedRecord(student: Student) {
        inputName.value = student.name
        inputEmail.value = student.email
        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteButtonText.value = "Delete"
        isUpdateOrDelete = true
        studentToUpdateDelete = student
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}