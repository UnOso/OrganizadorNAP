package com.example.naplanner.utils;

import com.example.naplanner.models.TaskModel;

import java.util.Comparator;

public class TasksSorter implements Comparator<TaskModel> {

    @Override
    public int compare(TaskModel task1, TaskModel task2) {
        return task1.getType().compareTo(task2.getType());
    }
}
