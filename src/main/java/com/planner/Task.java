package com.planner;

import com.jfoenix.controls.JFXCheckBox;

import java.io.Serializable;
import java.util.List;

public class Task extends JFXCheckBox implements Serializable {

    private int priority;
    private String text;

    public Task(String text, int priority) {
        super(text);
        this.text = text;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public String getRealText() {
        return text;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static void setTextToAllTasksInList(List<Task> tasks){
        for (var task : tasks)
            task.setText(task.getRealText());
    }


}
