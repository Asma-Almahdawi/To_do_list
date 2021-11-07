package com.example.todolist

import java.util.*

data class Task( val id : UUID = UUID.randomUUID(),var title:String="", var description:String="", var isCheack:Boolean=false, var date:Date=Date(),
                var dueDate:Date=Date()) {



}