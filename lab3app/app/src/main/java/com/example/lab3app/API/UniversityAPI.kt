package com.example.lab3app.API

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


const val GET_UNIVERSITY = 10
const val APPEND_UNIVERSITY = 11
const val UPDATE_UNIVERSITY = 12
const val DELETE_UNIVERSITY = 13

const val GET_FACULTY = 20
const val APPEND_FACULTY = 21
const val UPDATE_FACULTY = 22
const val DELETE_FACULTY = 23

const val GET_GROUP = 30
const val APPEND_GROUP = 31
const val UPDATE_GROUP = 32
const val DELETE_GROUP = 33

const val GET_STUDENT = 40
const val APPEND_STUDENT = 41
const val UPDATE_STUDENT = 42
const val DELETE_STUDENT = 43

interface UniversityAPI {
    @GET("?code=$GET_UNIVERSITY")
//    @GET("universities")
    fun getUniversities(): Call<UniversityResponse>
    @Headers("Content-Type: application/json")
    @POST("university")
    fun postUniversity(@Body postUniversity: UniversityPost): Call<PostResult>

    @GET("?code=$GET_FACULTY")
    fun getFaculties(): Call<FacultyResponse>
    @Headers("Content-Type: application/json")
    @POST("faculty")
    fun postFaculty(@Body postFaculty: FacultyPost): Call<PostResult>


    @GET("?code=$GET_GROUP")
    fun getGroups(): Call<GroupResponse>
    @Headers("Content-Type: application/json")
    @POST("group")
    fun postGroup(@Body postGroup: GroupPost): Call<PostResult>

    @GET("?code=$GET_STUDENT")
    fun getStudents(): Call<StudentResponse>
    @Headers("Content-Type: application/json")
    @POST("student")
    fun postStudent(@Body postStudent: StudentPost): Call<PostResult>
}
