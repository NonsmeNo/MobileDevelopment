package gas.example.gas

import android.app.Application
import android.content.Context
import com.example.gas.repository.GasStationsRepository

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        GasStationsRepository.getInstance().loadData()
    }


    init {
        instance  = this
    }

    companion object{
        private var instance: MyApplication? = null
        val context
            get() = applicationContext()

        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}