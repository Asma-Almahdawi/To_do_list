package com.example.todolist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ToDoListFragment : Fragment() {

    private lateinit var listRecyclerView:RecyclerView

    val taskListViewModel by lazy { ViewModelProvider(this) .get(TaskListViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_to_do_list, container, false)
        listRecyclerView=view.findViewById(R.id.list_recycler_view)

        val linearLayoutManager=LinearLayoutManager(context)
        listRecyclerView.layoutManager=linearLayoutManager
         updateUI()
        return view
    }
    private fun updateUI() { //this finalll
        val taskAdapter = TaskAdapter(taskListViewModel.tasks)
        Log.d("ASMA", "the item count is : ${taskAdapter.itemCount}")
        listRecyclerView.adapter = taskAdapter
    }
    private inner class TaskHolder(view: View):RecyclerView.ViewHolder(view){
        private lateinit var task: Task
        private var titleTask:TextView=itemView.findViewById(R.id.task_title)
        private var dueDateView:TextView=itemView.findViewById(R.id.due_date_view)
        private var isCeakedView:ImageView=itemView.findViewById(R.id.is_cheack)

        fun bind(task:Task) {
            this.task = task
            titleTask.text = task.title
            dueDateView.text = task.date.toString()
            isCeakedView.visibility=if (task.isCheack){

                View.VISIBLE
            }else{

                View.GONE
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
            )//parent like conteier

            return TaskHolder(view)

        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)
        }

        override fun getItemCount(): Int = tasks.size
    }

}
