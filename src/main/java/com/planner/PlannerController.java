package com.planner;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class PlannerController implements Initializable {

    @FXML
    private StackPane pane;
    @FXML
    private JFXListView<Task> tasksListView;
    @FXML
    private Label priorityLabel;
    @FXML
    private JFXSlider prioritySlider;
    @FXML
    private JFXDialog jfxDialog;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label dateLabel;

    private Stage stage;
    private Scene scene;

    public static List<Task> tasks;
    private Task selectedTask;

    public static String path;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tasks = new ArrayList<>();

        datePicker.setOnMouseEntered(value-> {
            Data data = new Data(tasks);
            System.out.println("Enter" + path);
            try {
                Data.writeData(data, path);
            } catch (IOException e) {
                System.out.println("No Data Written");
            }
        });

        readAllData();

        tasksListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task task, Task currentTask) {
                selectedTask = currentTask;
                if (currentTask != null){
                    priorityLabel.setText("Priority: " + Integer.toString(currentTask.getPriority()));
                    prioritySlider.setValue(currentTask.getPriority());
                }
            }
        });
    }
    public void writeAllData()
    {
        Data data = new Data(tasks);
        try {
            Data.writeData(data, path);
            Data.saveDate(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readAllData()
    {
        try {
            path = Data.readDate();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        datePicker.setValue(LocalDate.parse(path));
        setDate();
        try {
            Data data = Data.readData(path);
            for (Task x: data.getTasks())
                x.setOnAction(value->TaskIsDone(x));
            tasks = data.getTasks();
            Task.reloadAllParameters(tasks);
            refreshTasksListView();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void showDialog(String information)
    {
        Text textHeader = new Text();
        textHeader.setFill(Color.color(0.38,0,0));
        textHeader.setText("Something went wrong");
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(textHeader);
        Text text = new Text();
        text.setFill(Color.color(0.38,0,0));
        text.setText(information);
        layout.setBody(text);
        jfxDialog = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Ok");
        button.setOnAction(value->jfxDialog.close());
        layout.setActions(button);
        jfxDialog.show();
    }
    public void priority() {
        if (selectedTask == null)
        {
            showDialog("Choose item");
            return;
        }
        if (selectedTask.isSelected())
        {
            showDialog("Choose unchecked item");
            return;
        }
        selectedTask.setPriority((int)prioritySlider.getValue());
        priorityLabel.setText("Priority: " + Integer.toString((int)prioritySlider.getValue()));
        sortTasksListByPriority(tasks);
        refreshTasksListView();
    }
    public void TaskIsDone(Task task)
    {
        if(task.isSelected()) {
            task.setPriority(6);
        }
        else{
            task.setPriority(5);
        }
        sortTasksListByPriority(tasks);
        refreshTasksListView();

    }
    public String setTaskName(String action) throws FileNotFoundException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(action);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setGraphic(null);
        dialog.getDialogPane().setPrefWidth(350);
        dialog.getDialogPane().setPrefHeight(150);
        dialog.getDialogPane().getStylesheets().add(getClass().getResource(Data.readTheme()).toExternalForm());
        Optional<String> result = dialog.showAndWait();
        return result.get();
    }
    public void addTask() {
        String taskName;
        try{
            taskName = setTaskName("Enter your task:");
        }catch(NoSuchElementException | FileNotFoundException e) {
            System.out.println("No value present");
            return;
        }
        Task task = new Task(taskName, 5);
        task.setOnAction(value->TaskIsDone(task));
        tasks.add(task);

        tasksListView.getItems().clear();
        tasksListView.getItems().addAll(tasks);

        sortTasksListByPriority(tasks);
        refreshTasksListView();
    }

    public void editTask() {
        String taskName;
        try{
            selectedTask.setPriority((int)prioritySlider.getValue());
        }catch(NullPointerException e) {
            showDialog("Choose item to edit");
            return;
        }
        try{
            taskName = setTaskName("Edit task '" + selectedTask.getRealText() + "'");
        }catch(NoSuchElementException | FileNotFoundException e) {
            System.out.println("No value present");
            return;
        }
        selectedTask.setText(taskName);
        refreshTasksListView();
    }

    public void deleteTask() {
        if (selectedTask == null){
            showDialog("Choose item to delete");
            return;
        }
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
        tasks.sort((task1, task2)->(task1.getPriority() > task2.getPriority())?1:-1);
    }
    public void changeTheme(String source) throws IOException {
        pane = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = (Stage)tasksListView.getScene().getWindow();
        scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource(source).toExternalForm());
        stage.setTitle("Planner");
        stage.setScene(scene);
        stage.show();
    }
    public void defaultTheme() throws IOException {
        writeAllData();
        changeTheme("view.css");
        Data.saveTheme("view.css");
        readAllData();
    }
    public void blackTheme() throws IOException {
        writeAllData();
        changeTheme("black.css");
        Data.saveTheme("black.css");
        readAllData();

    } public void whiteTheme() throws IOException {
        writeAllData();
        changeTheme("white.css");
        Data.saveTheme("white.css");
        readAllData();
    }

    public void setDate()
    {
        String day = datePicker.getValue().getDayOfWeek().toString() + " "
                + Integer.toString(datePicker.getValue().getDayOfMonth()) + "-"
                + Integer.toString(datePicker.getValue().getMonthValue()) + "-"
                + Integer.toString(datePicker.getValue().getYear());
        dateLabel.setText(day);
    }
    public void changeDate()
    {
        tasksListView.getItems().clear();
        tasks.clear();
        setDate();
        path = datePicker.getValue().toString();
        try {
            Data data = Data.readData(path);
            tasks = data.getTasks();
            Task.reloadAllParameters(tasks);
            refreshTasksListView();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("File not found");
            //e.printStackTrace();
        }
    }
    public void close()
    {
        writeAllData();
        Platform.exit();
        System.exit(0);
    }
}