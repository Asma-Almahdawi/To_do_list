package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment= supportFragmentManager.findFragmentById(R.id.fragmentContainerView)//create fragment
        if (currentFragment == null){
            val fragment=ToDoListFragment()
            //  val fragment=CrimeListFragment()
            supportFragmentManager
                .beginTransaction()//we have fragment come beReady 2
                .add(R.id.fragmentContainerView,fragment)
                .commit()
    }
}}