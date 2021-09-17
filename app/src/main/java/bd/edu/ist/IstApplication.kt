package bd.edu.ist

import android.app.Application
import bd.edu.ist.db.ISTDatabase

class IstApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ISTDatabase.getInstance(this)
    }
}