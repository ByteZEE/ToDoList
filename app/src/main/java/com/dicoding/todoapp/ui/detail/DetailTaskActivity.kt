package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.databinding.ActivityTaskDetailBinding
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO 11 : Show detail task and implement delete action
        val taskId = intent.getIntExtra(TASK_ID, 1)
        val factory = ViewModelFactory.getInstance(this)
        val taskViewModel = ViewModelProvider(this,factory).get(DetailTaskViewModel::class.java)
        taskViewModel.setTaskId(taskId)
        taskViewModel.task.observe(this) {
            binding.detailEdTitle.setText(it.title)
            binding.detailEdDescription.setText(it.description)
            binding.detailEdDueDate.setText(DateConverter.convertMillisToString(it.dueDateMillis))
        }

        binding.btnDeleteTask.setOnClickListener {
            taskViewModel.task.removeObservers(this)
            taskViewModel.deleteTask()

            val intent = Intent(this, TaskActivity::class.java)
            this.startActivity(intent)
        }
    }
}