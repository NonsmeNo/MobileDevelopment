package com.example.lab3app.fragments

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.lab3app.R
import com.example.lab3app.data.Student
import com.example.lab3app.data.University
import com.example.lab3app.databinding.FragmentStudentBinding
import com.example.lab3app.databinding.FragmentStudentsBinding
import com.example.lab3app.repository.TAG
import com.example.lab3app.repository.UniversityRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat

private const val ARG_PARAM1 = "com.example.app3_352_2024.student_param"
class StudentFragment : Fragment() {

    private lateinit var student: Student
    private lateinit var viewModel: StudentsViewModel
    private lateinit var _binding: FragmentStudentBinding
    var flag : Boolean = true

    val binding
        get()=_binding

//    private lateinit var viewModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val param1 = it.getString(ARG_PARAM1)
            if(param1 == null)
                student = Student()
            else{
                val paramType = object : TypeToken<Student>(){}.type
                student = Gson().fromJson<Student>(param1, paramType)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentStudentBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)

        val sexArray = resources.getStringArray(R.array.SEX)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexArray)
        binding.spSex.adapter=adapter
        binding.spSex.setSelection(student.sex)
        binding.spSex.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                student.sex=position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.cvBirthDate.setOnDateChangeListener{
            view,year,month,dayOfMonth->
            student.birthDate.time=
                SimpleDateFormat("yyyy.MM.dd").parse("$year.$month.$dayOfMonth")?.time ?: student.birthDate.time
        }
        binding.etName.setText(student.firstName)
        binding.etLastname.setText(student.lastName)
        binding.etMiddlename.setText(student.middleName)
        binding.etPhone.setText(student.phone)
        binding.cvBirthDate.date=student.birthDate.time
        binding.btnCancel.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSave.setOnClickListener{
            student.lastName=binding.etLastname.text.toString()
            student.firstName=binding.etName.text.toString()
            student.middleName=binding.etMiddlename.text.toString()
            student.phone=_binding.etPhone.text.toString()
            Log.d(TAG, flag.toString())
            if (flag)
                UniversityRepository.getInstance().newStudent(student)
            else
                UniversityRepository.getInstance().updateStudent(student)
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return  binding.root
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object{
        @JvmStatic
        fun newInstance(student: Student, fl: Boolean) =
            StudentFragment().apply {
                flag = fl
                arguments=Bundle().apply {
                    putString(ARG_PARAM1, Gson().toJson(student))
                }
            }

    }
}