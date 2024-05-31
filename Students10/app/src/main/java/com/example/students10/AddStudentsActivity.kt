package com.example.students10

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddStudentsActivity : AppCompatActivity() {

    companion object {
        fun newIntent(packageContext: Context, surname: String?, name: String?, patronym: String?, faculty: String?
                      , group: String?, phone: String?): Intent {
            return Intent(packageContext,   AddStudentsActivity::class.java).apply {
                putExtra("surname", surname)
                putExtra("name", name)
                putExtra("patronym", patronym)
                putExtra("faculty", faculty)
                putExtra("group", group)
                putExtra("phone", phone)

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_students)

        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val etSurname = findViewById<EditText>(R.id.etSurname)
        val etName = findViewById<EditText>(R.id.etName)
        val etPatronym = findViewById<EditText>(R.id.etPatronym)
        val etFaculty = findViewById<EditText>(R.id.etFaculty)
        val etGroup = findViewById<EditText>(R.id.etGroup)
        val etPhone = findViewById<EditText>(R.id.etPhone)



        val intentSurname: String? = intent?.getStringExtra("surname")
        etSurname.setText(intentSurname)

        val intentName: String? = intent?.getStringExtra("name")
        etName.setText(intentName)

        val intentPatronym: String? = intent?.getStringExtra("patronym")
        etPatronym.setText(intentPatronym)

        val intentFaculty: String? = intent?.getStringExtra("faculty")
        etFaculty.setText(intentFaculty)

        val intentGroup: String? = intent?.getStringExtra("group")
        etGroup.setText(intentGroup)

        val intentPhone: String? = intent?.getStringExtra("phone")
        etPhone.setText(intentPhone)


        btnSave.setOnClickListener{
            if (etSurname.text.toString().isNotBlank() && etName.text.toString().isNotBlank() &&
                etPatronym.text.toString().isNotBlank() && etFaculty.text.toString().isNotBlank() &&
                etGroup.text.toString().isNotBlank() && etPhone.text.toString().isNotBlank() ) {


                val data = Intent().apply {
                    putExtra("surname", etSurname.text.toString())
                    putExtra("name", etName.text.toString())
                    putExtra("patronym", etPatronym.text.toString())
                    putExtra("faculty", etFaculty.text.toString())
                    putExtra("group", etGroup.text.toString())
                    putExtra("phone", etPhone.text.toString())
                }
                setResult(Activity.RESULT_OK, data)
                finish()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_LONG).show()
            }
        }

        btnCancel.setOnClickListener{
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}