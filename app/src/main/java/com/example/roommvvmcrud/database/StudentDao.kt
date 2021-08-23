package com.example.roommvvmcrud.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDao {

    @Insert
    suspend fun insertStudent(student: Student) : Long

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAllRecords()

    @Query("Select * from student_table")
    fun getAllRecords() : LiveData<List<Student>>
}