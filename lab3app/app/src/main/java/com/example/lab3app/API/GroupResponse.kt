package com.example.lab3app.API

import com.example.lab3app.data.Faculty
import com.example.lab3app.data.Group
import com.example.lab3app.data.University
import com.google.gson.annotations.SerializedName

class GroupResponse {
    @SerializedName ("items") lateinit var items: List<Group>
}