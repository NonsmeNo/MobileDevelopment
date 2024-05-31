package com.example.gas.fragments

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gas.R
import com.example.gas.data.GasStations
import com.example.gas.databinding.FragmentGasStationsBinding
import com.example.gas.repository.GasStationsRepository
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import gas.example.gas.MainActivity

class GasStationsFragment : Fragment(), MainActivity.Edit {

    companion object {
        private var INSTANCE: GasStationsFragment? = null

        fun getInstance(): GasStationsFragment {
            if (INSTANCE == null) INSTANCE = GasStationsFragment()
            return INSTANCE ?: throw Exception("GasStationsFragment не создан")
        }
    }

    private lateinit var viewModel: GasStationsViewModel
    private var tabPosition : Int = 0
    private lateinit var _binding: FragmentGasStationsBinding
    val binding get() = _binding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGasStationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(GasStationsViewModel::class.java)
        val ma = (requireActivity() as UpdateActivity)
        ma.setTitle("Мои заправки", Color.GREEN)
        viewModel.gasStationsList.observe(viewLifecycleOwner)
        {
            createUI(it)
        }

    }

    private inner class GasStationPageAdapter(fa: FragmentActivity, private val gasStations: List<GasStations>?): FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return (gasStations?.size ?: 0)
        }

        override fun createFragment(position: Int): Fragment {
            return CarsFragment.newInstance(gasStations!![position])
        }
    }

    private fun createUI (gasStationsList : List<GasStations>){
        binding.tGasStations.clearOnTabSelectedListeners()
        binding.tGasStations.removeAllTabs()

        for (i in 0 until (gasStationsList.size)){
            binding.tGasStations.addTab(binding.tGasStations.newTab().apply {
                text = gasStationsList.get(i).name
            })
        }

        val adapter = GasStationPageAdapter(requireActivity(), viewModel.gasStationsList.value)
        binding.vpCars.adapter=adapter
        TabLayoutMediator(binding.tGasStations,binding.vpCars, true, true){
                tab,pos -> tab.text = gasStationsList.get(pos).name
        }.attach()

        tabPosition = 0
        if (viewModel.gasStations != null)
            tabPosition = if(viewModel.getGasStationsListPosition>=0)
                viewModel.getGasStationsListPosition
            else
                0

        viewModel.setCurrentGasStation(tabPosition)
        binding.tGasStations.selectTab(binding.tGasStations.getTabAt(tabPosition), true)
        binding.tGasStations.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabPosition = tab?.position!!
                viewModel.setCurrentGasStation(gasStationsList[tabPosition])
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun append() {
        newGasStation()
    }

    override fun delete() {
        deleteGasStation()
    }

    override fun update() {
        updateGasStation()

    }

    private fun newGasStation() {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.etName)
        AlertDialog.Builder(requireContext())
            .setTitle("Добавить заправку")
            .setView(mDialogView)
            .setPositiveButton("Добавить") { _, _ ->
                if (inputName.text.isNotBlank()) {
                    viewModel.appendGasStation(inputName.text.toString())
                }
            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }

    private fun updateGasStation() {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.etName)
        inputName.setText(viewModel.gasStations?.name)
        AlertDialog.Builder(requireContext())
            .setTitle("Изменить заправку")
            .setView(mDialogView)
            .setPositiveButton("Изменить") { _, _ ->
                if (inputName.text.isNotBlank()) {
                    viewModel.updateGasStation(inputName.text.toString())
                }
            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }

    private fun deleteGasStation(){
        if(viewModel.gasStations == null) return
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление!")
            .setMessage("Вы действительно хотите удалить заправку ${viewModel.gasStations?.name ?: ""}?")
            .setPositiveButton("Да"){_, _ ->
                viewModel.deleteGasStation()
            }
            .setNegativeButton("Нет", null)
            .setCancelable(true)
            .create()
            .show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as UpdateActivity).setTitle("Мои заправки", Color.GREEN)
    }

}