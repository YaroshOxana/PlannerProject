package com.planner;

import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;
import java.util.List;

public class Task extends JFXCheckBox implements Serializable {

    private int priority;
    private String text;
    private Boolean isChecked;

    public Task(String Text, int priority) {
        super(Text);
        this.text = Text;
        this.priority = priority;

        selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                isChecked = newValue;
            }
        });


        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                text = t1;
            }
        });
    }

    // Getters

    public int getPriority() {
        return priority;
    }
    public String getRealText() {
        return text;
    }
    private Boolean getIsChecked() {
        if (isChecked == null)
            return false;
        return isChecked;
    }

    // Setters

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static void setTextToAllTasksInList(List<Task> tasks){
        for (var task : tasks)
            task.setText(task.getRealText());
    }
    public static void setCheckingTasksInList(List<Task> tasks){
        for (var task : tasks)
            task.setSelected(task.getIsChecked());
    }
    public static void setSelectedPropertyListenerForAllTaskInList(List<Task> tasks){
        for (var task : tasks)
            task.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                    task.isChecked = newValue;
                }
            });
    }

    public static void setTextPropertyListenerForAllTaskInList(List<Task> tasks){
        for (var task : tasks)
            task.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    task.text = t1;
                }
            });
    }

    public static void reloadAllParameters(List<Task> tasks){
        setTextToAllTasksInList(tasks);
        setCheckingTasksInList(tasks);
        setSelectedPropertyListenerForAllTaskInList(tasks);
        setTextPropertyListenerForAllTaskInList(tasks);
    }

}
