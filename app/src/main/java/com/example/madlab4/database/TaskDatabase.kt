package com.example.madlab4.database

import android.content.Context
import androidx.room.Database
import androidx.room.*
import com.example.madlab4.converters.TypeConverter
import com.example.madlab4.dao.TaskDao
import com.example.madlab4.models.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao : TaskDao
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    name = "task_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }

}