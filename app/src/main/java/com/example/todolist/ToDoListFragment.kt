package com.example.todolist

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.CheckBox
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.util.*

const val KEY_ID="myTaskId"
class ToDoListFragment : Fragment() {

    private lateinit var listRecyclerView:RecyclerView



    val taskListViewModel by lazy { ViewModelProvider(this) .get(TaskListViewModel::class.java) }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_list_menu,menu)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.new_task-> {
                val task = Task()
                taskListViewModel.addTask(task)
                val args = Bundle()
                args.putSerializable(KEY_ID, task.id)
                val fragment = FragmentTask()
                fragment.arguments = args
                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_to_do_list, container, false)
        listRecyclerView=view.findViewById(R.id.list_recycler_view)



        val linearLayoutManager=LinearLayoutManager(context)
        listRecyclerView.layoutManager=linearLayoutManager

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskListViewModel.liveDataTasks.observe(
          viewLifecycleOwner, Observer {

              updateUI(it)
            }


        )
    }
    private fun updateUI(tasks:List<Task>) {
        val taskAdapter = TaskAdapter(tasks)
        Log.d("ASMA", "the item count is : ${taskAdapter.itemCount}")
        listRecyclerView.adapter = taskAdapter
    }
    private inner class TaskHolder(view: View):RecyclerView.ViewHolder(view),View.OnClickListener{
        private lateinit var task: Task
        private var titleTask:TextView=itemView.findViewById(R.id.task_title)
        private var dueDateView:TextView=itemView.findViewById(R.id.due_date_view)
        private var isCheckedView:CheckBox=itemView.findViewById(R.id.is_cheack_v)
        private val overDueDate:TextView=itemView.findViewById(R.id.over_dueDate)
        private var checkPriority:ImageView=itemView.findViewById(R.id.check_priority)

        init {
         itemView.setOnClickListener  (this)
        }

        val dateFormat="yyyy-MM-dd"
        fun bind(task:Task) {
            this.task = task
            titleTask.text = task.title




            val currentDate=Date()



            if (task.dueDate !=null){
                dueDateView.text=DateFormat.format(dateFormat,task.dueDate)

                overDueDate.visibility=if (task.dueDate!!.after(currentDate)){
                    View.GONE
                }else{
                    View.VISIBLE
                }


            }else{
                dueDateView.text = ""
            }


     isCheckedView.isChecked=task.isCompetled
            isCheckedView.setOnCheckedChangeListener { _, isChecked ->
                task.isCompetled=isChecked
              //  isCheckedView.setBackgroundColor
                taskListViewModel.saveUpdate(task)

            }


//          isCeakedView.visibility=if (task.isCheack){
//             overDueDate.visibility=View.GONE
//                View.VISIBLE
//           }else{
//
//               View.GONE
//           }



        }

        override fun onClick(v: View?) {
            if (v == itemView){
                val args=Bundle()
                args.putSerializable(KEY_ID,task.id)
                val fragment=FragmentTask()
                fragment.arguments=args
                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView,fragment)
                        .addToBackStack(null)
                        .commit()
                    Log.d("ASAMA","NO")
                }

            }
        }


    }
    private inner class TaskAdapter(var tasks: List<Task>) :
        RecyclerView.Adapter<TaskHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {

            val view = layoutInflater.inflate(
                R.layout.list_item_task,
                parent,
                false
            )

            return TaskHolder(view)

        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)

        }

        override fun getItemCount(): Int = tasks.size
    }



}
