package com.planner;

import java.io.*;
import java.util.List;

public class Data implements Serializable {
    private List<Task> tasks;

    public Data(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public static void writeData(Data savedData) throws IOException {
        FileOutputStream f = new FileOutputStream(new File("savedData.bin"));
        ObjectOutputStream o = new ObjectOutputStream(f);

        o.writeObject(savedData);
        o.close();
        f.close();
    }

    public static Data readData() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File("savedData.bin"));
        ObjectInputStream oi = new ObjectInputStream(fi);

        Data readData = (Data) oi.readObject();
        oi.close();
        fi.close();

        return readData;
    }
}
