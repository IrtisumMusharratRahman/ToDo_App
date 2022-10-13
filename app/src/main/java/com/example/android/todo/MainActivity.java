package com.example.android.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.android.todo.Adapter.ToDoAdapter;
import com.example.android.todo.Model.ToDoModel;
import com.example.android.todo.Utils.DBHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements DialogCloseListner {

    private RecyclerView recyclerView;
    private ToDoAdapter toDoAdapter;
    private ArrayList<ToDoModel> taskList;
    private DBHandler dbHandler;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        dbHandler = new DBHandler(this);
        dbHandler.openDb();

        taskList = new ArrayList<>();
//        taskList.add(new ToDoModel(1,0,"First Task"));
        dbHandler.insertTask(new ToDoModel(1,0,"First Task"));

        recyclerView = findViewById(R.id.tasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toDoAdapter = new ToDoAdapter(dbHandler, this);
        recyclerView.setAdapter(toDoAdapter);

        taskList = dbHandler.getAllTask();
        Collections.reverse(taskList);
        toDoAdapter.setToDoModelList(taskList);

        floatingActionButton = findViewById(R.id.tasksFAB);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(toDoAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {

        taskList = dbHandler.getAllTask();
        Collections.reverse(taskList);
        toDoAdapter.setToDoModelList(taskList);
        toDoAdapter.notifyDataSetChanged();

    }
}