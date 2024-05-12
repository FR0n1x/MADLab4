package com.example.madlab4

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.madlab4.adapters.TaskRVVBListAdapter
import com.example.madlab4.databinding.ActivityMainBinding
import com.example.madlab4.models.Task
import com.example.madlab4.utils.Status
import com.example.madlab4.utils.clearEditText
import com.example.madlab4.utils.longToastShow
import com.example.madlab4.utils.setupDialog
import com.example.madlab4.utils.validateEditText
import com.example.madlab4.viewmodels.TaskViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val addTaskDialog: Dialog by lazy {
        Dialog(this).apply {
            setupDialog(R.layout.add_task_dialog)
        }
    }
    private val updateTaskDialog: Dialog by lazy {
        Dialog(this).apply {
            setupDialog(R.layout.update_task_dialog)
        }
    }
    private val loadingDialog: Dialog by lazy {
        Dialog(this).apply {
            setupDialog(R.layout.loading_dialog)
        }
    }

    private val taskViewModel: TaskViewModel by lazy {
        ViewModelProvider(this)[TaskViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mainBinding.root)


//        Add task start
        val addCLoseImg = addTaskDialog.findViewById<ImageView>(R.id.closeImg)
        addCLoseImg.setOnClickListener { (addTaskDialog.dismiss()) }

        val addETTitle = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskTitle)
        val addETTitleL = addTaskDialog.findViewById<TextInputLayout>(R.id.edTaskTitleL)

        addETTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addETTitle, addETTitleL)
            }

        })

        val addETDesc = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskDesc)
        val addETDescL = addTaskDialog.findViewById<TextInputLayout>(R.id.edTaskDescL)

        addETDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addETDesc, addETDescL)
            }

        })

        mainBinding.addTaskFAbtn.setOnClickListener {
            clearEditText(addETTitle, addETTitleL)
            clearEditText(addETDesc, addETDescL)
            addTaskDialog.show()
        }

        val saveTaskButton = addTaskDialog.findViewById<Button>(R.id.saveTaskBtn)
        saveTaskButton.setOnClickListener {
            if (validateEditText(addETTitle, addETTitleL) && validateEditText(
                    addETDesc,
                    addETDescL
                )
            ) {
                addTaskDialog.dismiss()
                val newTask = Task(
                    UUID.randomUUID().toString(),
                    addETTitle.text.toString().trim(),
                    addETDesc.text.toString().trim(),
                    Date()
                )
                taskViewModel.insertTask(newTask).observe(this) {
                    when (it.status) {
                        Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Status.SUCCESS -> {
                            loadingDialog.dismiss()
                            if (it.data?.toInt() != -1) {
                                longToastShow("Task Added Successfully")
                            }
                        }

                        Status.ERROR -> {
                            loadingDialog.dismiss()
                            it.message?.let { it1 -> longToastShow(it1) }

                        }
                    }
                }
            }
        }

        //        Add task end

//        update start

        val updateETTitle = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskTitle)
        val updateETTitleL = updateTaskDialog.findViewById<TextInputLayout>(R.id.edTaskTitleL)

        updateETTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(updateETTitle, updateETTitleL)
            }

        })

        val updateETdesc = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskDesc)
        val updateETdescL = updateTaskDialog.findViewById<TextInputLayout>(R.id.edTaskDescL)

        updateETdesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(updateETdesc, updateETdescL)
            }

        })

        val updateCLoseImg = updateTaskDialog.findViewById<ImageView>(R.id.closeImg)
        updateCLoseImg.setOnClickListener { (updateTaskDialog.dismiss()) }

        val updateTaskButton = updateTaskDialog.findViewById<Button>(R.id.updateTaskBtn)


//        update end

        val taskRecyclerViewAdapter = TaskRVVBListAdapter { type, position, task ->
            if (type == "delete") {
                taskViewModel.deleteTask(task.id).observe(this) {
                        when (it.status) {
                            Status.LOADING -> {
                                loadingDialog.show()
                            }

                            Status.SUCCESS -> {
                                loadingDialog.dismiss()
                                if (it.data?.toInt() != -1) {
                                    longToastShow("Task Deleted Successfully")
                                }
                            }

                            Status.ERROR -> {
                                loadingDialog.dismiss()
                                it.message?.let { it1 -> longToastShow(it1) }

                            }
                        }

                    }
            } else if (type == "update") {
                updateETTitle.setText(task.title)
                updateETdesc.setText(task.description)
                updateTaskButton.setOnClickListener {
                    if (validateEditText(updateETTitle, updateETTitleL) && validateEditText(
                            updateETdesc,
                            updateETdescL
                        )
                    ) {
                        val updateTask = Task(
                            task.id,
                            updateETTitle.text.toString().trim(),
                            updateETdesc.text.toString().trim(),
//                           here i Date updated
                            Date()
                        )
                        updateTaskDialog.dismiss()
                        loadingDialog.show()
                        taskViewModel.updateTask(updateTask)
//                            .updateTaskParticularField(task.id,
//                                updateETTitle.text.toString().trim(),
//                                updateETdesc.text.toString().trim(),)
                            .observe(this) {
                                when (it.status) {
                                    Status.LOADING -> {
                                        loadingDialog.show()
                                    }

                                    Status.SUCCESS -> {
                                        loadingDialog.dismiss()
                                        if (it.data?.toInt() != -1) {
                                            longToastShow("Task Updated Successfully")
                                        }
                                    }

                                    Status.ERROR -> {
                                        loadingDialog.dismiss()
                                        it.message?.let { it1 -> longToastShow(it1) }

                                    }
                                }

                            }
                    }
                }
                updateTaskDialog.show()
            }
        }


        mainBinding.taskRV.adapter = taskRecyclerViewAdapter

        callGetTaskList(taskRecyclerViewAdapter)

    }

    private fun callGetTaskList(taskRecyclerViewAdapter: TaskRVVBListAdapter) {
        CoroutineScope(Dispatchers.Main).launch {
            taskViewModel.viewTaskList().collect {
                when (it.status) {
                    Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        it.data?.collect { taskList ->
//                            taskRecyclerViewAdapter.submitList(taskList)
//                        }
                        it.data?.collect { taskList ->
                            loadingDialog.dismiss()
                            taskRecyclerViewAdapter.addAllTask(taskList)
                        }

                    }

                    Status.ERROR -> {
                        loadingDialog.dismiss()
                        it.message?.let { it1 -> longToastShow(it1) }
                    }
                }
            }
        }
    }
}