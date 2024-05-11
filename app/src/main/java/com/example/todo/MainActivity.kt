package com.example.todo

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.Model.ToDoModel
import com.example.todo.Utils.DataBaseHelper
import com.example.todo.Adapter.ToDoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Collections


class MainActivity : AppCompatActivity(), OnDialogCloseListner {
    private var mRecyclerview: RecyclerView? = null
    private var fab: FloatingActionButton? = null
    private var myDB: DataBaseHelper? = null
    private var mList: List<ToDoModel?>? = null
    private var adapter: ToDoAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerview = findViewById(R.id.recyclerview)
        fab = findViewById(R.id.fab)
        myDB = DataBaseHelper(this@MainActivity)
        mList = ArrayList()
        adapter = ToDoAdapter(myDB, this@MainActivity)
        mRecyclerview?.setHasFixedSize(true)
        mRecyclerview?.setLayoutManager(LinearLayoutManager(this))
        mRecyclerview?.setAdapter(adapter)
        mList = myDB!!.allTasks
        Collections.reverse(mList)
        adapter?.setTasks(mList)
        fab?.setOnClickListener(View.OnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        })
        val itemTouchHelper = ItemTouchHelper(RecyclerViewTouchHelper(adapter))
        itemTouchHelper.attachToRecyclerView(mRecyclerview)
    }

    override fun onDialogClose(dialogInterface: DialogInterface?) {
        mList = myDB!!.allTasks
        Collections.reverse(mList)
        adapter?.setTasks(mList)
        adapter?.notifyDataSetChanged()
    }

}