package com.example.todolist.database

import androidx.room.TypeConverter
import java.util.*

class TaskTypeConverters {


    @TypeConverter
    fun fromDate(date: Date?):Long?{
    return date?.time
}
    @TypeConverter
    fun toDate(millsecEpoch: Long?):Date?{
        return millsecEpoch?.let { Date(it) }
    }

    @TypeConverter
    fun fromUUID(id:UUID):String{

        return id.toString()
    }
    @TypeConverter
    fun toUUID(id:String?):UUID?{
     return UUID.fromString(id)
    }

}