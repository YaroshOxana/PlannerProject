package com.planner;

import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.CheckBoxListCell;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlannerController implements Initializable {

    @FXML
    private JFXListView<Task> tasksListView;

    public static List<Task> tasks;
    private Task selectedTask;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        tasks = new ArrayList<>();
//        CheckBoxListCell cell = new CheckBoxListCell();
//
//        Task task = new Task("Hello",2);
//        Task task1 = new Task("Worlds", 1);
//        Task task2 = new Task("Hehe", 1);
//        Task task3 = new Task("Some Text", 3);
//
//        tasks.add(task);
//        tasks.add(task1);
//        tasks.add(task2);
//        tasks.add(task3);
//
//        tasksListView.getItems().addAll(tasks);
//        sortTasksListByPriority(tasks);
//        refreshTasksListView();


//        // Reading data
//        try {
//            Data data1 = Data.readData();
//            tasks = data1.getTasks();
//            Task.setTextToAllTasksInList(tasks);
//            refreshTasksListView();
//        }
//        catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        tasksListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task task, Task currentTask) {
                selectedTask = currentTask;
            }
        });
    }

    public void addTask() {

    }

    public void editTask() {

    }

    public void deleteTask() {
        tasks.remove(selectedTask);
        refreshTasksListView();
    }

    public void refreshTasksListView(){
        if (tasksListView.getItems() == null)
            return;

        tasksListView.getItems().clear();
        for (var task : tasks)
            tasksListView.getItems().add(task);
    }

    private void sortTasksListByPriority(List<Task> tasks){
        tasks.sort((task1, task2)->(task1.getPriority() < task2.getPriority())?1:-1);
    }
}