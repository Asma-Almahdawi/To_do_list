package com.example.todolist

import androidx.lifecycle.ViewModel
import com.example.todolist.database.TaskRepository

class TaskListViewModel:ViewModel() {
    val taskRepository=TaskRepository.get()

    val liveDataTasks=taskRepository.getAllTask()

    fun addTask(task: Task){

        taskRepository.addTask(task)
    }
}