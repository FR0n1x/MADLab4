package com.example.madlab4.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.madlab4.models.Task
import com.example.madlab4.repository.TaskRepository
import com.example.madlab4.utils.Resource

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository = TaskRepository(application)

    fun viewTaskList() = taskRepository.getTaskList()
    fun insertTask(task: Task): MutableLiveData<Resource<Long>> {
        return taskRepository.insertTask(task)
    }

    fun deleteTask(taskId: String): MutableLiveData<Resource<Int>> {
        return taskRepository.deleteTask(taskId)
    }
    fun updateTask(task: Task): MutableLiveData<Resource<Int>> {
        return taskRepository.updateTask(task)
    }
    fun updateTaskParticularField(taskId: String, title: String, description: String): MutableLiveData<Resource<Int>> {
        return taskRepository.updateTaskParticularField(taskId, title, description)
    }
}