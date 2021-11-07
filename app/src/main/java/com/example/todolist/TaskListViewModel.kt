package com.example.todolist

import androidx.lifecycle.ViewModel

class TaskListViewModel:ViewModel() {

    val tasks= mutableListOf<Task>()

    init {

        for (i in 0..25){

            val task =Task()
            task.title="the title is $i"
            task.isCheack =i % 2 == 0
            tasks.add(task)

        }
    }
}