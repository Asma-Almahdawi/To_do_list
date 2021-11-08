package com.example.todolist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText


class FragmentTask : Fragment() {

    private lateinit var titleOfTask:EditText
    private lateinit var description:EditText
    private lateinit var dateBtn:Button
    private lateinit var dueDate:Button
    private lateinit var addTask:Button
    private lateinit var task: Task
    private lateinit var isCheackBox:CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task= Task()


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment__task, container, false)
        titleOfTask=view.findViewById(R.id.title_task)
        description=view.findViewById(R.id.des_task)
        dateBtn=view.findViewById(R.id.task_date)
        dueDate=view.findViewById(R.id.due_date)
        addTask=view.findViewById(R.id.add_task)
        isCheackBox=view.findViewById(R.id.isCheaked)
        dateBtn.apply {

            text=task.date.toString()
            // isEnabled=false//عشان نخلي الزر ماينضغط بس كشكل
        }

        dueDate.apply {
            text=task.dueDate.toString()

        }
        return view
    }

    override fun onStart() {
        super.onStart()

        val textWatcher= object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
             task.title=s.toString()
                task.description=s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        titleOfTask.addTextChangedListener(textWatcher)
        description.addTextChangedListener(textWatcher)
        isCheackBox.setOnCheckedChangeListener { _, isChecked ->
            task.isCheack=isChecked

        }

    }
}

