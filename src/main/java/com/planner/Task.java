package com.planner;

import javafx.scene.control.CheckBox;

public class Task extends CheckBox {
    private int priority;

    public Task(String text, int priority) {
        super(text);
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
