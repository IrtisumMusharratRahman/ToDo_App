package com.example.android.todo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.todo.AddNewTask;
import com.example.android.todo.MainActivity;
import com.example.android.todo.Model.ToDoModel;
import com.example.android.todo.R;
import com.example.android.todo.Utils.DBHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> toDoModelList;
    private MainActivity mainActivity;
    private DBHandler dbHandler;

    public ToDoAdapter(DBHandler dbHandler, MainActivity mainActivity) {
        this.dbHandler=dbHandler;
        this.mainActivity = mainActivity;
    }

    public void setToDoModelList(List<ToDoModel> toDoModelList) {
        this.toDoModelList = toDoModelList;
        notifyDataSetChanged();
    }

    public void editItem(int position){
        ToDoModel toDoModel = toDoModelList.get(position);
        Bundle bundle =new Bundle();
        bundle.putInt("id",toDoModel.getId());
        bundle.putString("task", toDoModel.getTask());

        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(mainActivity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public void deleteItem(int position){
        ToDoModel toDoModel = toDoModelList.get(position);
        dbHandler.deleteTask(toDoModel.getId());
        toDoModelList.remove(position);
        notifyItemRemoved(position);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        dbHandler.openDb();
        ToDoModel toDoModel = toDoModelList.get(position);
        holder.checkBox.setText(toDoModel.getTask());
        holder.checkBox.setChecked(toBoolean(toDoModel.getStatus()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    dbHandler.updateStatus(toDoModel.getId(),1);
                }else {
                    dbHandler.updateStatus(toDoModel.getId(),0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return toDoModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox.findViewById(R.id.todoCheckBox);
        }
    }

    private boolean toBoolean(int status){return status!=0;}

    public Context getContext() {
        return mainActivity;
    }
}
