package com.example.lab3app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.lab3app.data.Student
import com.example.lab3app.fragments.FacultyListFragment
import com.example.lab3app.fragments.GroupsFragment
import com.example.lab3app.fragments.StudentFragment
import com.example.lab3app.fragments.UniversityListFragment
import com.example.lab3app.fragments.UpdateActivity
import com.example.lab3app.repository.UniversityRepository

class MainActivity : AppCompatActivity(), UpdateActivity {

    interface Edit {
        fun append()
        fun update()
        fun delete()
    }

    companion object {
        const val universityID = 0
        const val facultyId = 1
        const val groupID = 2
        const val studentID = 3
    }


    private var _miNewUniversity: MenuItem? = null
    private var _miUpdateUniversity: MenuItem? = null
    private var _miDeleteUniversity: MenuItem? = null

    private var _miNewGroup: MenuItem? = null
    private var _miUpdateGroup: MenuItem? = null
    private var _miDeleteGroup: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(this){
            if(supportFragmentManager.backStackEntryCount > 0){
                supportFragmentManager.popBackStack()
                when(currentFragmentID){
                    universityID ->{
                        finish()
                    }
                    facultyId ->{
                        currentFragmentID= universityID
                        setTitle("Список университетов")
                    }
                    groupID->{
                        currentFragmentID= facultyId
                        setTitle("Список факультетов")
                    }
                    studentID->{
                        currentFragmentID= groupID
                        setTitle("Список групп")
                    }
                    else ->{}
                }
                updateMenuView()
            }
            else {
                finish()
            }
        }
        setFragment(universityID)
//        updateMenuView()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fcwMain, UniversityListFragment.getInstance()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        _miNewUniversity = menu?.findItem(R.id.miNewUniversity)
        _miUpdateUniversity = menu?.findItem(R.id.miUpdateUniversity)
        _miDeleteUniversity = menu?.findItem(R.id.miDeleteUniversity)

        _miNewGroup = menu?.findItem(R.id.miNewGroup)
        _miUpdateGroup = menu?.findItem(R.id.miUpdateGroup)
        _miDeleteGroup = menu?.findItem(R.id.miDeleteGroup)
        _miNewGroup?.isVisible = false
        _miUpdateGroup?.isVisible = false
        _miDeleteGroup?.isVisible = false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miNewUniversity -> {
                val fedit: Edit = UniversityListFragment.getInstance()
                fedit.append()
                true
            }

            R.id.miUpdateUniversity -> {
                val fedit: Edit = UniversityListFragment.getInstance()
                fedit.update()
                true
            }

            R.id.miDeleteUniversity -> {
                val fedit: Edit = UniversityListFragment.getInstance()
                fedit.delete()
                true
            }
            R.id.miNewGroup -> {
                val fedit: Edit = GroupsFragment.getInstance()
                fedit.append()
                true
            }

            R.id.miUpdateGroup -> {
                val fedit: Edit = GroupsFragment.getInstance()
                fedit.update()
                true
            }

            R.id.miDeleteGroup -> {
                val fedit: Edit = GroupsFragment.getInstance()
                fedit.delete()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

//    override fun onStop() {
//        UniversityRepository.getInstance().saveData()
//        super.onStop()
//    }

    override fun setTitle(_title: String) {
        title = _title
    }

    private var currentFragmentID = 0
    override fun setFragment(fragmentID: Int, student: Student?, flag: Boolean) {
        currentFragmentID = fragmentID
        when (fragmentID) {
            universityID -> setFragment(UniversityListFragment.getInstance())
            facultyId -> setFragment(FacultyListFragment.getInstance())
            groupID -> setFragment(GroupsFragment.getInstance())
            studentID -> setFragment(StudentFragment.newInstance(student ?: Student(), flag))
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fcwMain, fragment).addToBackStack(null).commit()
        updateMenuView()
    }

    private fun updateMenuView(){
        _miNewUniversity?.isVisible=(currentFragmentID==universityID)
        _miDeleteUniversity?.isVisible=(currentFragmentID==universityID)
        _miUpdateUniversity?.isVisible=(currentFragmentID==universityID)
        _miNewGroup?.isVisible=(currentFragmentID==groupID)
        _miDeleteGroup?.isVisible=(currentFragmentID==groupID)
        _miUpdateGroup?.isVisible=(currentFragmentID==groupID)
    }
}