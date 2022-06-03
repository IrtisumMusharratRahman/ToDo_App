package com.example.android.todo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.todo.MainActivity;
import com.example.android.todo.Model.ToDoModel;
import com.example.android.todo.R;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> toDoModelList;
    private MainActivity mainActivity;

    public ToDoAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setToDoModelList(List<ToDoModel> toDoModelList) {
        this.toDoModelList = toDoModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ToDoModel toDoModel = toDoModelList.get(position);
        holder.checkBox.setText(toDoModel.getTask());
        holder.checkBox.setChecked(toBoolean(toDoModel.getStatus()));
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
}
