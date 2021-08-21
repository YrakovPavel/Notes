package com.example.notes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static WorkNote work;
    private String[] AutoCategories = {"Всё", "Работа", "Дом", "Личное", "Отдых"};
    private Button AddBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.se);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        work = new WorkNote(this, linearLayout);
        work.reload();

        //Добавление новых заметок
        AddBut = (Button)findViewById(R.id.my_but);
        AddBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteAdd.class);
                startActivity(intent);
            }
        });

        //Добавление списка категории заметки
        ArrayAdapter<String> adapter_categories = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, AutoCategories);
        adapter_categories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner_categories = (Spinner)findViewById(R.id.spinner_category);
        spinner_categories.setAdapter(adapter_categories);
        spinner_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                work.setChoice(adapterView.getItemAtPosition(i).toString());
                work.reload();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}