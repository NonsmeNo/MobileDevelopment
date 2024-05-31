package com.example.gas.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gas.R
import com.example.gas.data.Cars
import com.example.gas.data.GasStations
import com.example.gas.databinding.FragmentCarsBinding
import gas.example.gas.MainActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class CarsFragment : Fragment() {

    private lateinit var gasStation: GasStations

    companion object {
        fun newInstance(gasStation: GasStations): CarsFragment {
            return CarsFragment().apply { this.gasStation = gasStation }
        }
    }

    private lateinit var viewModel: CarsViewModel
    private lateinit var _binding : FragmentCarsBinding
    val binding
        get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentCarsBinding.inflate(inflater, container, false)
        binding.rvCars.layoutManager=
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarsViewModel::class.java)
        viewModel.setGasStationValue(gasStation)
        viewModel.carsList.observe(viewLifecycleOwner){
            binding.rvCars.adapter = CarAdapter(it)
        }
        binding.fabNewStudent.setOnClickListener{
            viewModel.isNew = true
            editCar(Cars().apply { gasStationID = viewModel.gasStation!!.id }, true)
        }
    }


    private fun deleteDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление!")
            .setMessage("Вы действительно хотите удалить автомобиль под номером ${viewModel.car?.num ?: ""}?")
            .setPositiveButton("Да") {_, _ ->
                viewModel.deleteCar()
            }
            .setNegativeButton("Нет", null)

            .setCancelable(true)
            .create()
            .show()
    }



    private fun editCar(car: Cars?, flag: Boolean){
        (requireActivity() as UpdateActivity).setFragment(MainActivity.carID, car, flag)
        (requireActivity() as UpdateActivity).setTitle("Заправка ${viewModel.gasStation!!.name}", Color.WHITE)
    }





    private inner class CarAdapter(private val items: List<Cars>)
        : RecyclerView.Adapter<CarAdapter.ItemHolder>(){
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CarAdapter.ItemHolder {
            val view = layoutInflater.inflate(R.layout.element_list, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount(): Int = items.size
        override fun onBindViewHolder(holder: CarAdapter.ItemHolder, position: Int) {
            holder.bind(viewModel.carsList.value!![position])
        }

        private var lastView: View? = null

        private fun updateCurrentView(view: View){


            val ll=lastView?.findViewById<LinearLayout>(R.id.llButtons) //кнопки удаления и изменения
            ll?.visibility=View.INVISIBLE
            ll?.layoutParams=ll?.layoutParams.apply { this?.width=1 }


            val ib = lastView?.findViewById<ImageButton>(R.id.ibInfo) //кнопка полной инфы
            ib?.visibility=View.INVISIBLE
            ib?.layoutParams=ib?.layoutParams.apply { this?.width=1 }

            lastView?.findViewById<ConstraintLayout>(R.id.clCar)?.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.my_black))
            view.findViewById<ConstraintLayout>(R.id.clCar).setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.my_green))
            lastView=view
        }

        private inner class ItemHolder(view: View): RecyclerView.ViewHolder(view){
            private lateinit var car: Cars

            @OptIn(DelicateCoroutinesApi::class)
            fun bind(car: Cars){
                this.car = car
                if (car == viewModel.car)
                    updateCurrentView(itemView)




                val tvNum = itemView.findViewById<TextView>(R.id.tvCarNumber)
                tvNum.text=car.num
                tvNum.setOnLongClickListener {
                    viewModel.sortByNumber()
                    true
                }

                val tvGasType = itemView.findViewById<TextView>(R.id.tvCarGasType)
                tvGasType.setOnLongClickListener {
                    viewModel.sortByTypeGas()
                    true
                }

                val gasTypes = resources.getStringArray(R.array.TypeGAS)
                tvGasType.text = gasTypes[car.gas]

                val cl=itemView.findViewById<ConstraintLayout>(R.id.clCar)


                cl.setOnClickListener {
                    viewModel.setCurrentCar(car)
                    updateCurrentView(itemView)
                }
                tvGasType.setOnClickListener {
                    viewModel.setCurrentCar(car)
                    updateCurrentView(itemView)
                }
                tvNum.setOnClickListener {
                    viewModel.setCurrentCar(car)
                    updateCurrentView(itemView)
                }


                itemView.findViewById<ImageButton>(R.id.ibInfo).setOnClickListener{
                    Toast.makeText(context, "Объём: " + car.volume + "л", Toast.LENGTH_SHORT).show()
                }

                itemView.findViewById<ImageButton>(R.id.ibEditCar).setOnClickListener{
                    viewModel.isNew = false
                    viewModel.setCurrentCar(car)
                    updateCurrentView(itemView)
                    editCar(car, false)
                    //(requireActivity() as UpdateActivity).setTitle("Заправка ${viewModel.gasStation!!.name}", Color.WHITE)
                }

                itemView.findViewById<ImageButton>(R.id.ibDeleteCar).setOnClickListener{
                    deleteDialog()
                }

                val llb = itemView.findViewById<LinearLayout>(R.id.llButtons)
                llb.visibility=View.INVISIBLE
                llb?.layoutParams= llb?.layoutParams?.apply { this.width=1 }

                val ib=itemView.findViewById<ImageButton>(R.id.ibInfo)
                ib.visibility=View.INVISIBLE


                cl.setOnLongClickListener{
                    cl.callOnClick()
                    llb.visibility=View.VISIBLE
                    ib.visibility=View.VISIBLE

                    MainScope().launch {
                        val lp=llb?.layoutParams
                        lp?.width=1
                        val ip = ib.layoutParams
                        ip.width=1
                        while (lp?.width!! < 350){
                            lp?.width=lp?.width!!+35
                            llb?.layoutParams=lp
                            ip.width=ip.width+17
                            if(ib.visibility==View.VISIBLE)
                                ib.layoutParams=ip
                            delay(50)
                        }
                    }
                    true
                }
            }

        }

    }

}