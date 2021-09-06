package org.istbd.IST_Syllabus.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CourseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ISTDatabase : RoomDatabase() {
    abstract val courseDao: CourseDao

    companion object {
        @Volatile
        var INSTANCE: ISTDatabase? = null

        fun getInstance(context: Context): ISTDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ISTDatabase::class.java,
                        "ist_db"
                    )
                        .createFromAsset("ist.db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        fun getInstance(): ISTDatabase {
            return INSTANCE!!
        }
    }
}