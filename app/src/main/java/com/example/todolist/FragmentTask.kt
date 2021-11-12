package com.example.todolist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.DatePickerDialogFragment
import java.util.*

const val TASK_DATE_KEY="TaskDate"
class FragmentTask : Fragment(), DatePickerDialogFragment.DatePickerCallBack {
private val fragmentViewModel by lazy { ViewModelProvider(this).get(TaskFragmentViewModel::class.java)}
    private lateinit var titleOfTask:EditText
    private lateinit var description:EditText
    private lateinit var dateBtn:TextView
    private lateinit var dueDate:TextView
    private lateinit var deleteTask:ImageButton
    private lateinit var task: Task
    private lateinit var isCheackBox:CheckBox
    private lateinit var isLessCheckBox: CheckBox
    private lateinit var isNormalCheackBox:CheckBox
    private lateinit var isHighCheackBox:CheckBox
    private  var dateFormat="yyyy-MM-dd"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task= Task()
        val taskId=arguments?.getSerializable(KEY_ID) as UUID
        fragmentViewModel.loadTask(taskId)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment__task, container, false)

        titleOfTask=view.findViewById(R.id.title_task)
        description=view.findViewById(R.id.des_task)
        //dateBtn=view.findViewById(R.id.task_date)
        dueDate=view.findViewById(R.id.due_date)
       deleteTask=view.findViewById(R.id.delete_task)
//        isCheackBox=view.findViewById(R.id.isCheaked)
        isLessCheckBox=view.findViewById(R.id.lessCheack)
        isNormalCheackBox=view.findViewById(R.id.normalCheack)
        isHighCheackBox=view.findViewById(R.id.highCheack)

       // isLessCheckBox.isChecked=task.isCheack
        isLessCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                task.priority = 2
                fragmentViewModel.saveUpdate(task)

            }
        }
//        isLessCheckBox.setOnClickListener {
//
//          task.priority=2
//            fragmentViewModel.saveUpdate(task)
//        }




        isNormalCheackBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
            task.priority=1
            fragmentViewModel.saveUpdate(task)}
        }

        isHighCheackBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
            task.priority=0

            fragmentViewModel.saveUpdate(task)}
        }

//        isNormalCheackBox.setOnClickListener {
//         task.priority=1
//            fragmentViewModel.saveUpdate(task)
//
//        }
//        isHighCheackBox.isChecked=task.isCheack
//
//        isHighCheackBox.setOnClickListener {
//
//        task.priority=0
//            fragmentViewModel.saveUpdate(task)
//        }


//        dateBtn.apply {
//
//            text=task.date.toString()
//            // isEnabled=false//عشان نخلي الزر ماينضغط بس كشكل
//        }

        dueDate.apply {
            text=task.dueDate.toString()

        }
        return view
    }

    override fun onStart() {
        super.onStart()

        dueDate.setOnClickListener {
            val args=Bundle()
            args.putSerializable(TASK_DATE_KEY,task.dueDate)


            val datePicker=DatePickerDialogFragment()
            datePicker.arguments=args
            datePicker.setTargetFragment(this,0)
            datePicker.show(this.parentFragmentManager,"date picker")

        }

        val titleTextWatcher= object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
             task.title=s.toString()

            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        val descrptionTextWatcher= object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                task.description=s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }


        deleteTask.setOnClickListener {

            fragmentViewModel.deleteTasks(task)
            val args=Bundle()
            args.putSerializable(KEY_ID,task.id)
            val fragment=ToDoListFragment()
            fragment.arguments=args
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView,fragment)
                    .addToBackStack(null)
                    .commit()

            }
        }




        titleOfTask.addTextChangedListener(titleTextWatcher)
        description.addTextChangedListener(descrptionTextWatcher)
//        isCheackBox.setOnCheckedChangeListener { _, isChecked ->
//            task.isCheack=isChecked
//
//        }

    }

    override fun onStop() {
        super.onStop()
        fragmentViewModel.saveUpdate(task)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.taskLiveData.observe(

            viewLifecycleOwner, androidx.lifecycle.Observer {
                it?.let {
                   task=it
                    titleOfTask.setText(it.title)
                    description.setText(it.description)
                    //dateBtn.text=it.date.toString()
                    dueDate.text=it.dueDate.toString()

                    when(it.priority){

                        0-> isHighCheackBox.isChecked = true
                        1-> isNormalCheackBox.isChecked = true
                        2-> isLessCheckBox.isChecked = true

                    }
                   // isCheackBox.isChecked=it.isCheack


                }

            }

        )
    }

    override fun onDateSelected(date: Date) {
        task.dueDate=date
       // dueDate.text=date.toString()
        dueDate.text= DateFormat.format(dateFormat,task.dueDate)
    }
}

