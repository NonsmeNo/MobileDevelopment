package gas.example.gas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.gas.R
import com.example.gas.data.Cars
import com.example.gas.fragments.CarFragment
import com.example.gas.fragments.GasStationsFragment
import com.example.gas.fragments.UpdateActivity
import com.example.gas.repository.GasStationsRepository
import kotlinx.coroutines.flow.Flow

class MainActivity : AppCompatActivity(), UpdateActivity {

    interface Edit {
        fun append()
        fun update()
        fun delete()
    }

    companion object {
        const val gasstationID = 0
        const val carID = 1
    }


    private var _miNewGasStation: MenuItem? = null
    private var _miUpdateGasStation: MenuItem? = null
    private var _miDeleteGasStation: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(this){
            if(supportFragmentManager.backStackEntryCount > 0){
                supportFragmentManager.popBackStack()
                when(currentFragmentID){
                    gasstationID ->{

                        finish()
                    }
                    carID -> {
                        currentFragmentID = gasstationID
                    }
                    else ->{}
                }
                updateMenuView()
            }
            else {
                finish()
            }
        }
        setFragment(gasstationID)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miNewGasStation -> {
                val fedit: Edit = GasStationsFragment.getInstance()
                fedit.append()
                true
            }

            R.id.miUpdateGasStation -> {
                val fedit: Edit = GasStationsFragment.getInstance()
                fedit.update()
                GasStationsRepository.getInstance().loadData()
                true
            }

            R.id.miDeleteGasStation -> {
                val fedit: Edit = GasStationsFragment.getInstance()
                fedit.delete()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }




    private var currentFragmentID = -1

    override fun setTitle(_title: String, _color: Int) {
        // Устанавливаем заголовок с указанным цветом
        supportActionBar?.title = Html.fromHtml("<font color='#${Integer.toHexString(_color).substring(2)}'>$_title</font>")
    }



    override fun setFragment(fragmentID: Int, car: Cars?, flag: Boolean) {
        currentFragmentID = fragmentID
        when (fragmentID) {
            gasstationID -> {setFragment(GasStationsFragment.getInstance())}
            carID -> {setFragment(CarFragment.newInstance(car ?: Cars(), flag))}
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcwMain, fragment)
            .addToBackStack(null)
            .commit()
        updateMenuView()
    }

    private fun updateMenuView(){
        _miNewGasStation?.isVisible=currentFragmentID == gasstationID
        _miUpdateGasStation?.isVisible=currentFragmentID == gasstationID
        _miDeleteGasStation?.isVisible=currentFragmentID == gasstationID
    }
}

