package org.istbd.IST_Syllabus

import android.app.Application
import org.istbd.IST_Syllabus.db.ISTDatabase

class IstApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ISTDatabase.getInstance(this)
    }
}