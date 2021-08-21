package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NoteAdd extends AppCompatActivity {
    private Integer[] AutoImportance = {0, 1, 2, 3, 4, 5};
    private String[] AutoCategories = {"Работа", "Дом", "Личное", "Отдых"};
    private int id;
    private EditText name, text;
    private Button but;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);

        name = (EditText) findViewById(R.id.note_name);
        text = (EditText) findViewById(R.id.Note_text);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        db = new DB(this);

        //Добавление списка приоритета заметки
        ArrayAdapter<Integer> adapter_importance = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, AutoImportance);
        adapter_importance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner_importence = (Spinner)findViewById(R.id.spin_important);
        spinner_importence.setAdapter(adapter_importance);

        //Добавление списка категории заметки
        ArrayAdapter<String> adapter_categories = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, AutoCategories);
        adapter_categories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner_categories = (Spinner)findViewById(R.id.spin_categories);
        spinner_categories.setAdapter(adapter_categories);

        //Привязка кнопке функции добавления заметок
        but = (Button)findViewById(R.id.button_create);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note(id, name.getText().toString(), text.getText().toString(),
                        Integer.parseInt(spinner_importence.getSelectedItem().toString()),
                                    spinner_categories.getSelectedItem().toString());
                db.addNote(note);
                db.close();
                MainActivity.work.reload();
                finish();
            }
        });
    }
}