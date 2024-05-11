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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.madlab4.databinding.ActivityMainBinding
import com.example.madlab4.utils.setupDialog
import com.example.madlab4.utils.validateEditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
            addTaskDialog.show()
        }

        val saveTaskButton = addTaskDialog.findViewById<Button>(R.id.saveTaskBtn)
        saveTaskButton.setOnClickListener {
            if (validateEditText(addETTitle, addETTitleL)
                && validateEditText(addETDesc, addETDescL)
            ) {
                addTaskDialog.dismiss()
                Toast.makeText(this, "Validated!", Toast.LENGTH_SHORT).show()
                loadingDialog.show()
            }
        }

        //        Add task end


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

        mainBinding.addTaskFAbtn.setOnClickListener {
            addTaskDialog.show()
        }

        val updateCLoseImg = updateTaskDialog.findViewById<ImageView>(R.id.closeImg)
        updateCLoseImg.setOnClickListener { (updateTaskDialog.dismiss()) }

        val updateTaskButton = updateTaskDialog.findViewById<Button>(R.id.updateTaskBtn)
        updateTaskButton.setOnClickListener {
            if (validateEditText(updateETTitle, updateETTitleL) && validateEditText(
                    updateETdesc, updateETdescL
                )
            ) {
                updateTaskDialog.dismiss()
                Toast.makeText(this, "Validated!", Toast.LENGTH_SHORT).show()
                loadingDialog.show()
            }
        }


    }
}