package com.example.notes;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Note {
    private String name;
    private String text;
    private final String dateWrite;
    private String dateChange;
    private int importance;
    private String categories;
    private int id;
    private int number;
    private static int count = 0;
    private static ArrayList all = new ArrayList();

    Note (int id, String name, String text, int importance, String categories){
        this.number = count;
        count++;
        this.id = id;
        this.name = name;
        this.text = text;
        this.dateWrite = null;
        this.dateChange = "";
        this.importance = importance;
        this.categories = categories;
        all.add(this);
    }

    Note (int id, String name, String text, String dateWrite, String dateChange,
          int importance, String categories){
        this.number = count;
        count++;
        this.id = id;
        this.name = name;
        this.text = text;
        this.dateWrite = dateWrite;
        this.dateChange = dateChange;
        this.importance = importance;
        this.categories = categories;
        all.add(this);
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getDateWrite() {
        return dateWrite;
    }

    public String getDateChange() {
        return dateChange;
    }

    public int getImportance() {
        return importance;
    }

    public String getCategories() {
        return categories;
    }


    public static ArrayList getAll() {
        return all;
    }

    public static void setAll(ArrayList all) {
        Note.all = all;
    }

    public static void setCount(int _count){
        count = _count;
    }
}
