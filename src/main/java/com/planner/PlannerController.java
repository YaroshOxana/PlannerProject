package com.planner;

import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.CheckBoxListCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlannerController implements Initializable {

    @FXML
    private JFXListView<Task> tasksListView;

    private List<Task> tasks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tasks = new ArrayList<>();
        CheckBoxListCell cell = new CheckBoxListCell();

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
//        tasksListView.getItems().add(task);
//        tasksListView.getItems().add(task1);
//        tasksListView.getItems().add(task2);
//        tasksListView.getItems().add(task3);
//        refreshAndSortTasksListView();

        tasksListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task task, Task selectedTask) {
                var currentTask = selectedTask;
                System.out.println(currentTask.getPriority());
            }
        });


    }

    public void refreshAndSortTasksListView(){
        if (tasksListView.getItems() == null)
            return;
        sortTasksListByPriority(tasks);
        tasksListView.getItems().clear();
        for (var song : tasks)
            tasksListView.getItems().add(song);
    }

    private void sortTasksListByPriority(List<Task> tasks){
        tasks.sort((task1, task2)->(task1.getPriority() < task2.getPriority())?1:-1);
    }
}