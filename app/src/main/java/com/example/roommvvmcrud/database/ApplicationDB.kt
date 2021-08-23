package com.example.roommvvmcrud.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class ApplicationDB : RoomDatabase() {

    abstract val StudentDao: StudentDao

    companion object {
        private var INSTANCE: ApplicationDB? = null

        @JvmStatic
        fun getInstance(context: Context): ApplicationDB? {
            if (INSTANCE == null) {
                synchronized(ApplicationDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ApplicationDB::class.java, "student_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}