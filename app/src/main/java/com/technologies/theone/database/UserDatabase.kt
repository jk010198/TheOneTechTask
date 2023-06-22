package com.technologies.theone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.technologies.theone.dao.UserDao
import com.technologies.theone.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null

        fun roomInstance(context: Context): UserDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java,
                        "UserDB").build()
                }
            }
            return instance!!
        }
    }
}