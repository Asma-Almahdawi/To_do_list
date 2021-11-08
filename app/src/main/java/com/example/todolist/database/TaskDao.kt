package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.Task
import java.util.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task")
    fun getAllTask():LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE id =(:id)")
    fun getTask(id:UUID):LiveData<Task?>

    @Update
    fun updateTask(task: Task)

    @Insert
    fun addTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}