package com.example.roommvvmcrud.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roommvvmcrud.R
import com.example.roommvvmcrud.StudentRecordClickListener
import com.example.roommvvmcrud.database.Student
import com.example.roommvvmcrud.databinding.RowStudentBinding

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentDataViewHolder>() {

    private var listOfObjects: ArrayList<Student> = ArrayList()
    private var studentRecordClickListener: StudentRecordClickListener? = null

    fun setData(data: ArrayList<Student>) {
        listOfObjects = run {
            listOfObjects.clear()
            data
        }
        notifyDataSetChanged()
    }

    fun setClickListener(studentRecordClickListener: StudentRecordClickListener)
    {
        this.studentRecordClickListener = studentRecordClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentDataViewHolder {
        val binding = DataBindingUtil.inflate<RowStudentBinding>(LayoutInflater.from(parent.context), R.layout.row_student, parent, false)
        return StudentDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentDataViewHolder, position: Int) {
        listOfObjects[position].let {
            holder.bindUsersVpa(it)
        }
    }

    override fun getItemCount(): Int {
        return listOfObjects.size
    }

    inner class StudentDataViewHolder(private val binding: RowStudentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindUsersVpa(student : Student) {
            binding.student = student
            binding.callBack = studentRecordClickListener
            binding.executePendingBindings()
        }
    }

}