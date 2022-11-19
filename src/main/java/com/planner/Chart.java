package com.planner;

import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Chart {
    private static int dayOfMonth;
    private static int monthValue;
    private static int yearValue;
    private static String year;
    private static String month;
    private static String day;

    private static void getIntValues(DatePicker datePicker, int currentMonth, int currentYear)
    {
        YearMonth currentMonthLength = YearMonth.of(currentYear, currentMonth);
        if (dayOfMonth <= 0)
        {
            if (currentMonth - 1 == 0) {
                currentMonth = 12;
                currentYear--;
            }
            YearMonth yearMonthObject = YearMonth.of(currentYear, currentMonth - 1);
            dayOfMonth = yearMonthObject.lengthOfMonth() + dayOfMonth + 1;
            monthValue--;
            if (monthValue == 0) {
                monthValue = 12;
                yearValue--;
            }
        }
        if(dayOfMonth > currentMonthLength.lengthOfMonth())
        {
            dayOfMonth -= currentMonthLength.lengthOfMonth();
            monthValue++;
            if (monthValue == 13) {
                monthValue = 1;
                yearValue++;
            }
        }

    }
    private static void getStringValues()
    {
        year = Integer.toString(yearValue);

        if (monthValue<10)
            month = "0"+monthValue;
        else
            month = Integer.toString(monthValue);
        if (dayOfMonth<10)
            day = "0"+dayOfMonth;
        else
            day = Integer.toString(dayOfMonth);
    }

    public static List<Integer> getProductivityDetails(DatePicker datePicker, List<Task> tasks)
    {
        List<Integer> productivityDetails = new ArrayList<>();

        int currentDayValue = datePicker.getValue().getDayOfWeek().getValue();
        dayOfMonth = datePicker.getValue().getDayOfMonth() - currentDayValue + 1;
        monthValue = datePicker.getValue().getMonthValue();
        yearValue = datePicker.getValue().getYear();

        getIntValues(datePicker, datePicker.getValue().getMonthValue(), datePicker.getValue().getYear());
        getStringValues();

        for(int i = 0; i < 7; i++)
        {
            System.out.println(year+"-"+month+"-"+day);
            int completedTasks = 0;
            try {
                Data data = Data.readData(year+"-"+month+"-"+day);
                tasks = data.getTasks();
                Task.setCheckingTasksInList(tasks);
            }
            catch (IOException | ClassNotFoundException e) {
                System.out.println("File Not Found");
                tasks.clear();
            }
            for(Task x : tasks)
                if(x.isSelected())
                    completedTasks++;

            productivityDetails.add(completedTasks);

            dayOfMonth++;
            getIntValues(datePicker, monthValue, yearValue);
            getStringValues();
        }

        return productivityDetails;
    }
}
