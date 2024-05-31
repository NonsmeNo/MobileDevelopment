package com.example.gas.fragments

import com.example.gas.data.Cars

interface UpdateActivity {
    fun setTitle(_title: String, _color: Int)
    fun setFragment(fragmentID: Int, car: Cars?=null, flag: Boolean=true)
}
