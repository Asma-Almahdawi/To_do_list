package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class Task(
    @PrimaryKey val id : UUID = UUID.randomUUID(),
    var title:String="",
    var description:String="",
    var isCompetled:Boolean=false,
    var date:Date=Date(),
    var dueDate:Date?=null,
    var priority:Int=0

) {



}