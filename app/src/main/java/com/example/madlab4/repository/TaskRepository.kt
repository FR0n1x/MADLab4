package com.example.madlab4.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.madlab4.dao.TaskDao
import com.example.madlab4.database.TaskDatabase
import com.example.madlab4.models.Task
import com.example.madlab4.utils.Resource
import com.example.madlab4.utils.Resource.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TaskRepository(application: Application) {

    private val taskDao: TaskDao = TaskDatabase.getInstance(application).taskDao

    fun getTaskList() = flow {
        emit(Loading())
        try {
            val result = taskDao.getTaskList()
            emit(Success(result))
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }

    }

    fun insertTask(task: Task) = MutableLiveData<Resource<Long>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.insertTask(task)
                postValue(Success(result))
            }
        } catch (e: Exception) {
            postValue(Error(e.message.toString()))
        }
    }
    fun deleteTask(taskId: String) = MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTask(taskId)
                postValue(Success(result))
            }
        } catch (e: Exception) {
            postValue(Error(e.message.toString()))
        }
    }

    fun updateTask(task: Task) = MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.updateTask(task)
                postValue(Success(result))
            }
        } catch (e: Exception) {
            postValue(Error(e.message.toString()))
        }
    }
    fun updateTaskParticularField(taskId: String, title: String, description: String) = MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.updateTaskParticularField(taskId, title, description)
                postValue(Success(result))
            }
        } catch (e: Exception) {
            postValue(Error(e.message.toString()))
        }
    }

}