package com.example.roommvvmcrud.datasource

import com.example.roommvvmcrud.database.Student
import com.example.roommvvmcrud.database.StudentDao

class StudentRepository(private val studentDao: StudentDao){

    val studentData = studentDao.getAllRecords()

    suspend fun insertStudentRecord(student: Student) {
        studentDao.insertStudent(student)
    }

    suspend fun updateStudentRecord(student: Student) {
        studentDao.updateStudent(student)
    }

    suspend fun deleteStudentRecord(student: Student) {
        studentDao.deleteStudent(student)
    }

    suspend fun deleteAllRecords() {
        studentDao.deleteAllRecords()
    }
}