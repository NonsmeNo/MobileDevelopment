package com.example.students10

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val tvSurname = findViewById<TextView>(R.id.tvSurname)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvPatronym = findViewById<TextView>(R.id.tvPatronym)
        val tvFaculty = findViewById<TextView>(R.id.tvFaculty)
        val tvGroup = findViewById<TextView>(R.id.tvGroup)
        val tvPhone = findViewById<TextView>(R.id.tvPhone)

        val btnCreate = findViewById<Button>(R.id.btnCreate)
        val btnChange = findViewById<Button>(R.id.btnChange)

        val tvStudent = findViewById<TextView>(R.id.tvStudent)
        val student = Student()



        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                student.surname = data?.getStringExtra("surname")
                student.name = data?.getStringExtra("name")
                student.patronym = data?.getStringExtra("patronym")
                student.faculty = data?.getStringExtra("faculty")
                student.group = data?.getStringExtra("group")
                student.phone = data?.getStringExtra("phone")

                tvSurname.text = student.surname
                tvName.text = student.name
                tvPatronym.text = student.patronym
                tvFaculty.text = "Факультет: " + student.faculty
                tvGroup.text = "Группа: " + student.group
                tvPhone.text = "Телефон: " + student.phone
                tvStudent.text = "Студент"
            }
        }




        btnCreate.setOnClickListener{
            intent = AddStudentsActivity.newIntent(this@MainActivity,
                "", "","","","","")
            resultLauncher.launch(intent)

        }

        student.surname = tvSurname.text.toString()
        student.name = tvSurname.text.toString()
        student.patronym = tvSurname.text.toString()
        student.faculty = tvSurname.text.toString()
        student.group = tvSurname.text.toString()
        student.phone = tvSurname.text.toString()

        btnChange.setOnClickListener{
            intent = AddStudentsActivity.newIntent(this@MainActivity, student.surname,
                student.name ,student.patronym, student.faculty, student.group, student.phone)
            resultLauncher.launch(intent)
        }


    }



}