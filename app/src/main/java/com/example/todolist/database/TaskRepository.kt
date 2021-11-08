package com.example.todolist.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import com.example.todolist.Task
import java.lang.IllegalStateException
import java.time.Instant
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

const val DATABASE_NAME="task-database"
class TaskRepository private constructor(context: Context){
    private var database:TaskDatabase= Room.databaseBuilder(
        context.applicationContext,
        TaskDatabase::class.java,
        DATABASE_NAME
    ).build()

private val executor= Executors.newSingleThreadExecutor()

    private val taskDao=database.taskDao()

    fun getAllTask(): LiveData<List<Task>> =taskDao.getAllTask()

    fun getTask(id: UUID):LiveData<Task?>{

        return taskDao.getTask(id)
    }

    fun updateTask(task: Task){
        executor.execute {
            taskDao.updateTask(task)
        }
    }
    fun addTask(task: Task){

        executor.execute {

            taskDao.addTask(task)
        }
    }

    fun deleteTask(task: Task){
        executor.execute {

            taskDao.deleteTask(task)
        }

    }

companion object{

private var INSTANT:TaskRepository?=null

    fun initialize(context: Context){

        if (INSTANT==null){

            INSTANT= TaskRepository(context)
        }

    }

    fun get():TaskRepository{

        return INSTANT?:
        throw IllegalStateException("TaskRepository must be initialize")
    }

}


}