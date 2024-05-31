package com.example.lab3app.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.preference.PreferenceManager
import com.example.lab3app.API.APPEND_FACULTY
import com.example.lab3app.API.APPEND_GROUP
import com.example.lab3app.API.APPEND_STUDENT
import com.example.lab3app.API.APPEND_UNIVERSITY
import com.example.lab3app.API.DELETE_FACULTY
import com.example.lab3app.API.DELETE_GROUP
import com.example.lab3app.API.DELETE_STUDENT
import com.example.lab3app.API.DELETE_UNIVERSITY
import com.example.lab3app.API.FacultyPost
import com.example.lab3app.API.FacultyResponse
import com.example.lab3app.API.GroupPost
import com.example.lab3app.API.GroupResponse
import com.example.lab3app.API.PostResult
import com.example.lab3app.API.StudentPost
import com.example.lab3app.API.StudentResponse
import com.example.lab3app.API.UPDATE_FACULTY
import com.example.lab3app.API.UPDATE_GROUP
import com.example.lab3app.API.UPDATE_STUDENT
import com.example.lab3app.API.UPDATE_UNIVERSITY
import com.example.lab3app.API.UniversityAPI
import com.example.lab3app.API.UniversityConnection
import com.example.lab3app.API.UniversityPost
import com.example.lab3app.API.UniversityResponse
import com.example.lab3app.Application352
import com.example.lab3app.R
import com.example.lab3app.data.Faculty
import com.example.lab3app.data.FacultyList
import com.example.lab3app.data.Group
import com.example.lab3app.data.Student
import com.example.lab3app.data.University
import com.example.lab3app.data.UniversityList
import com.example.lab3app.database.UniversityDB
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

const val TAG = "com.example.lab3app.TAG"

class UniversityRepository private constructor() {
    companion object {
        private var INSTANCE: UniversityRepository? = null

        fun getInstance(): UniversityRepository {
            if (INSTANCE == null) {
                INSTANCE = UniversityRepository()
            }
            return INSTANCE
                ?: throw IllegalStateException("UniversityRepository: Репозиторий не инициализирован")
        }
    }

    private val universityDB by lazy {DBRepository(UniversityDB.getDatabase(Application352.context).universityDAO())}
    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)

//    var universityList: MutableLiveData<UniversityList?> = MutableLiveData()
    var university: MutableLiveData<University> = MutableLiveData()
    val universityList: LiveData<List<University>> = universityDB.getUniversities().asLiveData()


    fun onDestroy(){
        myCoroutineScope.cancel()
    }

//    fun newUniversity(university: University){
//        myCoroutineScope.launch {
//            universityDB.insertUniversity(university)
//            setCurrentUniversity(university)
//        }
//    }
//
//    fun updateUniversity(university: University){
//        myCoroutineScope.launch {
//            universityDB.insertUniversity(university)
//        }
//    }

//    fun deleteUniversity(university: University) {
//        myCoroutineScope.launch {
//            universityDB.deleteUniversity(university)
//        }
//        setCurrentFaculty(0)
//        setCurrentUniversity(0)
//    }

//    fun newUniversity(university: University) {
//        var listTmp = (universityList.value ?: UniversityList()).apply {
//            items.add(university)
//        }
//        universityList.postValue(listTmp)
//        setCurrentUniversity(university)
//    }

    fun setCurrentUniversity(_university: University) {
        university.postValue(_university)
    }

    fun setCurrentUniversity(position: Int) {
        if (universityList.value == null || position < 0 || (universityList.value?.size!! <= position))
            return
        setCurrentUniversity(universityList.value!![position])
    }

    fun getUniversityPosition(university: University): Int =
        universityList.value?.indexOfFirst {
            it.id == university.id
        } ?: 1

    fun getUniversityPosition() = getUniversityPosition(university.value ?: University())

//    fun updateUniversity(university: University) {
//        val position = getUniversityPosition(university)
//        if (position < 0) newUniversity(university)
//        else {
//            val listTmp = universityList.value!!
//            listTmp.items[position] = university
//            universityList.postValue(listTmp)
//        }
//    }


//    fun saveData() {
//        val context = Application352.context
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//        sharedPreferences.edit().apply() {
//            val gson = Gson()
//            val lst = universityList.value?.items ?: listOf<University>()
//            val jsonString = gson.toJson(lst)
//            Log.d(TAG, "Сохранение $jsonString")
//            putString(context.getString(R.string.preference_key_university_list), jsonString)
//            putString(
//                context.getString(R.string.preference_key_faculty_list),
//                gson.toJson(facultyList.value?.items ?: listOf<Faculty>())
//            )
//            apply()
//        }
//
//    }
//
//    fun loadData() {
//        val context = Application352.context
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//        sharedPreferences.apply {
//            var jsonString =
//                getString(context.getString(R.string.preference_key_university_list), null)
//            if (jsonString != null) {
//                Log.d(TAG, "Чтение University $jsonString")
//                val listType = object : TypeToken<List<University>>() {}.type
//                val tempList = Gson().fromJson<List<University>>(jsonString, listType)
//                val temp = UniversityList()
//                temp.items = tempList.toMutableList()
//                Log.d(TAG, "Загрузка University ${temp.toString()}")
//                universityList.value = temp
//                setCurrentUniversity(0)
//            }
//
//            jsonString =
//                getString(context.getString(R.string.preference_key_faculty_list), null)
//            if (jsonString != null) {
//                Log.d(TAG, "Чтение Faculty $jsonString")
//                val listType = object : TypeToken<List<Faculty>>() {}.type
//                val tempList = Gson().fromJson<List<Faculty>>(jsonString, listType)
//                val temp = FacultyList()
//                temp.items = tempList.toMutableList()
//                Log.d(TAG, "Загрузка Faculty ${temp.toString()}")
//                facultyList.value = temp
//                setCurrentFaculty(0)
//            }
//        }
//
//    }


    var facultyList: LiveData<List<Faculty>> = universityDB.getFaculties().asLiveData()
    var faculty: MutableLiveData<Faculty> = MutableLiveData()


    fun setCurrentFaculty(_faculty: Faculty) {
        faculty.postValue(_faculty)
    }

    fun setCurrentFaculty(position: Int) {
        if (facultyList.value == null || position < 0 || (facultyList.value?.size!! <= position))
            return
        setCurrentFaculty(facultyList.value!![position])
    }

    fun getFacultyPosition(faculty: Faculty): Int = facultyList.value?.indexOfFirst {
        it.id == faculty.id
    } ?: 1

    fun getFacultyPosition() = getFacultyPosition(faculty.value ?: Faculty())


//    fun newFaculty(faculty: Faculty) {
//        myCoroutineScope.launch {
//            universityDB.insertFaculty(faculty)
//            setCurrentFaculty(faculty)
//        }
//    }
//    fun updateFaculty(faculty: Faculty) {
//        newFaculty(faculty)
//    }
//
//    fun deleteFaculty(faculty: Faculty) {
//        myCoroutineScope.launch {
//            universityDB.deleteFaculty(faculty)
//        }
//        setCurrentFaculty(0)
//    }








    var groupList: LiveData<List<Group>> = universityDB.getAllGroups().asLiveData()
    var group: MutableLiveData<Group> = MutableLiveData()

//    fun newGroup(group: Group) {
//        myCoroutineScope.launch {
//            universityDB.insertGroup(group)
//            setCurrentGroup(group)
//        }
//    }

    fun setCurrentGroup(_group: Group) {
        group.postValue(_group)
    }

    fun setCurrentGroup(position: Int) {
        if (groupList.value == null || position < 0 || (groupList.value?.size!! <= position))
            return
        setCurrentGroup(groupList.value!![position])
    }

    fun getGroupPosition(group: Group): Int = groupList.value?.indexOfFirst {
        it.id == group.id
    } ?: 1

    fun getGroupPosition() = getGroupPosition(group.value ?: Group())

//    fun updateGroup(group: Group) {
//        newGroup(group)
//    }
//
//    fun deleteGroup(group: Group) {
//        myCoroutineScope.launch {
//            universityDB.deleteGroup(group)
//        }
//        setCurrentGroup(0)
//    }





    var studentList: LiveData<List<Student>> = universityDB.getAllStudents().asLiveData()
    var student: MutableLiveData<Student> = MutableLiveData()

//    fun newStudent(student: Student) {
//        myCoroutineScope.launch {
//            universityDB.insertStudent(student)
//            setCurrentStudent(student)
//        }
//    }

    fun setCurrentStudent(_student: Student) {
        student.postValue(_student)
    }

    fun setCurrentStudent(position: Int) {
        if (studentList.value == null || position < 0 || (studentList.value?.size!! <= position))
            return
        setCurrentStudent(studentList.value!![position])
    }

    fun getStudentPosition(student: Student): Int = studentList.value?.indexOfFirst {
        it.id == student.id
    } ?: 1

    fun getStudentPosition() = getStudentPosition(student.value ?: Student())

//    fun updateStudent(student: Student) {
//        newStudent(student)
//    }
//
//    fun deleteStudent(student: Student) {
//        myCoroutineScope.launch {
//            universityDB.deleteStudent(student)
//            setCurrentStudent(0)
//        }
//    }



    private var universityAPI = UniversityConnection.getClient().create(UniversityAPI::class.java)

    fun fetchUniversity(){
        universityAPI.getUniversities().enqueue(object : retrofit2.Callback<UniversityResponse>{
            override fun onFailure(call: Call<UniversityResponse>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка университетов", t)
            }

            override fun onResponse(call: Call<UniversityResponse>, response: Response<UniversityResponse>) {
                if (response.code() == 200){
                    val universities = response.body()
                    val items = universities?.items ?: emptyList()
                    Log.d(TAG, "Получен список университетов $items")
                    myCoroutineScope.launch {
                        universityDB.deleteAllUniversities()
                        for(f in items){
                            universityDB.insertUniversity(f)
                        }
                    }
                }
            }

        })
    }

    private fun updateUniversity(universityPost: UniversityPost){
        universityAPI.postUniversity(universityPost)
            .enqueue(object: retrofit2.Callback<PostResult>{
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    if(response.code() == 200) {
                        fetchUniversity()
                        Log.d(TAG, "Данные получены")
                    }
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d(TAG, "Ошибка изменения списка университетов", t)
                }
            })
    }

    fun newUniversity(university: University){
        updateUniversity(UniversityPost(APPEND_UNIVERSITY, university))
    }
    fun deleteUniversity(university: University){
        updateUniversity(UniversityPost(DELETE_UNIVERSITY, university))
    }
    fun updateUniversity(university: University){
        updateUniversity(UniversityPost(UPDATE_UNIVERSITY, university))
    }



    fun fetchFaculty(){
        universityAPI.getFaculties().enqueue(object : retrofit2.Callback<FacultyResponse>{
            override fun onFailure(call: Call<FacultyResponse>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка факультетов", t)
            }

            override fun onResponse(call: Call<FacultyResponse>, response: Response<FacultyResponse>) {
                if (response.code() == 200){
                    val faculties = response.body()
                    val items = faculties?.items ?: emptyList()
                    Log.d(TAG, "Получен список факультетов $items")
                    myCoroutineScope.launch {
                        universityDB.deleteAllFaculties()
                        for(f in items){
                            universityDB.insertFaculty(f)
                        }
                    }
                }
            }

        })
    }
    private fun updateFaculty(facultyPost: FacultyPost){
        universityAPI.postFaculty(facultyPost)
            .enqueue(object: retrofit2.Callback<PostResult>{
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    if(response.code() == 200) {
                        fetchFaculty()
                        Log.d(TAG, "Данные получены")
                    }
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d(TAG, "Ошибка изменения списка факультетов", t)
                }
            })
    }

    fun newFaculty(faculty: Faculty){
        updateFaculty(FacultyPost(APPEND_FACULTY, faculty))
    }
    fun deleteFaculty(faculty: Faculty){
        updateFaculty(FacultyPost(DELETE_FACULTY, faculty))
    }
    fun updateFaculty(faculty: Faculty){
        updateFaculty(FacultyPost(UPDATE_FACULTY, faculty))
    }


    fun fetchGroup(){
        universityAPI.getGroups().enqueue(object : retrofit2.Callback<GroupResponse>{
            override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка групп", t)
            }

            override fun onResponse(call: Call<GroupResponse>, response: Response<GroupResponse>) {
                if (response.code() == 200){
                    val groups = response.body()
                    val items = groups?.items ?: emptyList()
                    Log.d(TAG, "Получен список групп $items")
                    myCoroutineScope.launch {
                        universityDB.deleteAllGroups()
                        for(f in items){
                            universityDB.insertGroup(f)
                        }
                    }
                }else{
                    Log.d(TAG, "Ошибка получения списка групп ${response.body()}")
                }
            }

        })
    }
    private fun updateGroup(facultyPost: GroupPost){
        universityAPI.postGroup(facultyPost)
            .enqueue(object: retrofit2.Callback<PostResult>{
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    if(response.code() == 200) {
                        fetchGroup()
                        Log.d(TAG, "Данные получены")
                    }
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d(TAG, "Ошибка изменения списка групп", t)
                }
            })
    }

    fun newGroup(group: Group){
        updateGroup(GroupPost(APPEND_GROUP, group))
    }
    fun deleteGroup(group: Group){
        updateGroup(GroupPost(DELETE_GROUP, group))
    }
    fun updateGroup(group: Group){
        updateGroup(GroupPost(UPDATE_GROUP, group))
    }



    fun fetchStudent(){
        universityAPI.getStudents().enqueue(object : retrofit2.Callback<StudentResponse>{
            override fun onFailure(call: Call<StudentResponse>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка студентов", t)
            }

            override fun onResponse(call: Call<StudentResponse>, response: Response<StudentResponse>) {
                if (response.code() == 200){
                    val students = response.body()
                    val items = students?.items ?: emptyList()
                    Log.d(TAG, "Получен список студентов $items")
                    myCoroutineScope.launch {
                        universityDB.deleteAllStudents()
                        for(f in items){
                            universityDB.insertStudent(f)
                        }
                    }
                }
            }

        })
    }
    private fun updateStudent(studentPost: StudentPost){
        universityAPI.postStudent(studentPost)
            .enqueue(object: retrofit2.Callback<PostResult>{
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    if(response.code() == 200) {
                        fetchStudent()
                        Log.d(TAG, "Данные получены")
                    }
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d(TAG, "Ошибка изменения списка студентов", t)
                }
            })
    }

    fun newStudent(student: Student){
        updateStudent(StudentPost(APPEND_STUDENT, student))
    }
    fun deleteStudent(student: Student){
        updateStudent(StudentPost(DELETE_STUDENT, student))
    }
    fun updateStudent(student: Student){
        updateStudent(StudentPost(UPDATE_STUDENT, student))
    }


    fun loadData(){
//        myCoroutineScope.launch {
//            universityDB.deleteAllStudents()
//            universityDB.deleteAllGroups()
//            universityDB.deleteAllFaculties()
//            universityDB.deleteAllUniversities()
//        }
        fetchUniversity()
//        fetchFaculty()
//        fetchGroup()
//        fetchStudent()
    }

}