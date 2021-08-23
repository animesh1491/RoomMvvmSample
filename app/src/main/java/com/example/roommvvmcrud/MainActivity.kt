package com.example.roommvvmcrud

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roommvvmcrud.ui.StudentAdapter
import com.example.roommvvmcrud.database.ApplicationDB
import com.example.roommvvmcrud.database.Student
import com.example.roommvvmcrud.databinding.ActivityMainBinding
import com.example.roommvvmcrud.datasource.StudentRepository
import com.example.roommvvmcrud.datasource.StudentViewModel
import com.example.roommvvmcrud.datasource.StudentViewModelFactory
import java.net.URLEncoder

class MainActivity : AppCompatActivity(), StudentRecordClickListener {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var studentAdapter: StudentAdapter
    
    private var url = "https://payuat.csccloud.in/v1/payment/186151581104769"

    private val msgInfo = "31474|vbpv42tmpiTWlfp8HAtkmsZfGNZ9i080fzEXk0F+I4N5MezqWC3t0YQ5bPZakDEEtHW0Qa1PtjerWZgctaAcdYVTHIyEkCHaEKLAP3+sgJR7KHnWd/PxumaZ4z1CzIz/Ffuf7sLFks6UKlToL/JhFvFP8Z5Tux6R9iec2y8o1hyj0ReuxnenIOghq37fURvhzy51kAFXVJNXaC78DDjyLfO1iB2B4PfOLG0iYNSaIGIpQ2tNbPXmsQ0jYpzwLlpKYIKHN43vaI1Mm2kglQzBbtV32ZqYGPeFDA30Lj2tX1PtSE2+vNqe6x9+ggGW1JqZL3hOYlI+V/4coiu3tWZcUwqVOkf/HSppJfZ33KUEP69mnKNYueSuHM0+9wbZajJSwdVHbWuMC/5y8pu01NF7t5xhsjIqQuTJW++DGJVEVDQfjYetLj3nehkyI8kC1g+J/uQTVRSojsMUmQ9Gq75ngecs4wHPC3F49pU8A9AK/Ry0Jij8IrKE+IxeKk1oRjrpJPx1GpwdOpudJvnTYeu4HCU8qI0kuXU8TK8ruOlh7MK8W/HumzKISde0BXrg1O3eYKnhMVpJjeQHMsz1KpVEFN03GIXRd3XzpR+wk3w9yunDTb+zdchKn1GnsL6k1P+UruJmirBvi+WikLw2SH/Eli7NvGaSlMyC+Kqu2L/NURIq2YA/Mgp8RGqkHTCvRNMae9wJftSR9qlWevfvEZxIxkLTEiIjSb3QNIM4m47Gpb6shvTTev/lu0tDDSgI5UkzeIlqm1I8s6tzQhul4LrjbSCjM3UVTNPEK26d9kBvZFehKikU5YTpKRJeE4v+GuTa9emJ2rIud1xWG6EIK/vpWyB3Q29TPCEByykjvgQuNBr+Rs7AR4/v1PkkpTpeHkcq9Eew2nZAx16ZFwtst8+rxJHtvhFMq2P1QD9tBaifo3jr+Msm7+tvExx8kiJm3/DC77in9/VHCIDZAglcK/VfLtZyCHJAzFRIGwNBcArFSQorL6Js0lvK08UGfk5Huhb5"

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initComponents()
    }

    private fun initComponents() {
        ApplicationDB.getInstance(applicationContext)?.let {
            val dao = ApplicationDB.getInstance(applicationContext)!!.StudentDao
            val studentRepository = StudentRepository(dao)
            val factory = StudentViewModelFactory(studentRepository)
            studentViewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)
            studentAdapter = StudentAdapter()
            activityMainBinding.rvStudentData.setHasFixedSize(true)
            activityMainBinding.rvStudentData.adapter = studentAdapter
            studentAdapter.setClickListener(this)
            activityMainBinding.rvStudentData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            activityMainBinding.viewModel = studentViewModel
            activityMainBinding.lifecycleOwner = this
            showData()
        }
    }

    private fun showData() {
        studentViewModel.studentList.observe(this, Observer {
            studentAdapter.setData(it as ArrayList<Student>)
        })
    }

    override fun onStudentRecordClick(student: Student) {
        studentViewModel.showSelectedRecord(student)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onResume() {
        super.onResume()

        with(activityMainBinding) {
            webView.requestFocus()
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.javaScriptCanOpenWindowsAutomatically = true;

            webView.webViewClient = object : WebViewClient() {

                override fun onReceivedError(webView : WebView?, errorCode : Int, description : String, failingUrl : String?) {
                }

                override fun shouldOverrideUrlLoading(webView : WebView, url : String) : Boolean {
                    return false
                }

                override fun onPageStarted(view : WebView?, url : String?, favicon : Bitmap?) {
                    super.onPageStarted(view , url , favicon)
                }

                override fun onPageFinished(view : WebView?, url : String?) {
                    super.onPageFinished(view , url)
                }
            }

            val postData = "message=${URLEncoder.encode(msgInfo, "UTF-8")}"
            webView.postUrl(url, postData.toByteArray())
            
        }

    }

}