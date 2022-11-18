package com.planner;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Data implements Serializable {
    private List<Task> tasks;

    public Data(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public static void writeData(Data savedData, String path) throws IOException {
        FileOutputStream f = new FileOutputStream(new File("savedData/"+path+".bin"));
        ObjectOutputStream o = new ObjectOutputStream(f);

        o.writeObject(savedData);
        o.close();
        f.close();
    }

    public static Data readData(String path) throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File("savedData/"+path+".bin"));
        ObjectInputStream oi = new ObjectInputStream(fi);

        Data readData = (Data) oi.readObject();
        oi.close();
        fi.close();

        return readData;
    }
    public static void saveDate(String date) throws IOException {
        File file = new File("savedData/date.txt");
        FileWriter fr = new FileWriter(file);
        fr.write(date);

        fr.close();
    }
    public static String readDate() throws FileNotFoundException {
        File file = new File("savedData/date.txt");
        Scanner scan = new Scanner(file);
        String date = scan.nextLine();
        scan.close();

        return date;
    }
    public static void saveTheme(String theme) throws IOException {
        File file = new File("savedData/theme.txt");
        FileWriter fr = new FileWriter(file);
        fr.write(theme);

        fr.close();
    }
    public static String readTheme() throws FileNotFoundException {
        File file = new File( "savedData/theme.txt");
        Scanner scan = new Scanner(file);
        String theme = scan.nextLine();
        scan.close();

        return theme;
    }
}
