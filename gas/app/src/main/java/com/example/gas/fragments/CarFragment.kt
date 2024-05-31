package com.example.gas.fragments

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
import android.widget.Toast
import com.example.gas.R
import com.example.gas.data.Cars
import com.example.gas.databinding.FragmentCarBinding
import com.example.gas.repository.GasStationsRepository
import com.example.gas.repository.TAG
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat

private const val ARG_PARAM1 = "com.example.gas.car_param"
class CarFragment : Fragment() {

    private lateinit var car: Cars
    private lateinit var viewModel: CarsViewModel
    private lateinit var  _binding : FragmentCarBinding
    var flag : Boolean = true

    val binding get()=_binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val param1 = it.getString(ARG_PARAM1)
            if (param1==null)
                car=Cars()
            else {
                val paramType = object : TypeToken<Cars>() {}.type
                car = Gson().fromJson<Cars>(param1, paramType)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCarBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(CarsViewModel::class.java)

        val gasTypeArray = resources.getStringArray(R.array.TypeGAS)
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, gasTypeArray)
        binding.spTypeGas.adapter = adapter
        binding.spTypeGas.setSelection(car.gas)
        binding.spTypeGas.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                car.gas=position
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        binding.cvGasDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
            car.date.time =
                SimpleDateFormat("yyyy.MM.dd").parse("$year.${month + 1}.$dayOfMonth")?.time ?: car.date.time
        }

        binding.etBrand.setText(car.brand)
        binding.etModel.setText(car.model)
        binding.etNumber.setText(car.num)
        binding.etColor.setText(car.color)
        binding.etFIO.setText(car.owner)
        binding.etVol.setText(car.volume)
        binding.cvGasDate.date = car.date.time
        binding.buttonCancel.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.buttonSave.setOnClickListener {
            car.brand = binding.etBrand.text.toString()
            car.model = binding.etModel.text.toString()
            car.num = binding.etNumber.text.toString()
            car.color = binding.etColor.text.toString()
            car.owner = binding.etFIO.text.toString()
            car.volume = binding.etVol.text.toString()
            Log.d(TAG, flag.toString())
            if(car.brand.isNotBlank() && car.model.isNotBlank() && car.num.isNotBlank() &&
                car.color.isNotBlank() && car.owner.isNotBlank()
                && car.volume.isNotBlank()) {
                if (flag)
                    GasStationsRepository.getInstance().newCar(car)
                else
                    GasStationsRepository.getInstance().updateCar(car)
                GasStationsRepository.getInstance().setCurrentCar(car)
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            else
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()

        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(car: Cars, fl: Boolean) =
            CarFragment().apply {
                flag = fl
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, Gson().toJson(car))
                }
            }
    }



}