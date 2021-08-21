package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NoteChange extends AppCompatActivity {
    private Integer[] AutoImportance = {0, 1, 2, 3, 4, 5};
    private String[] AutoCategories = {"Работа", "Дом", "Личное", "Отдых"};
    private int id;
    private int importance;
    private String categories;
    private EditText name, text;
    private Button but;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_changes);


        name = (EditText) findViewById(R.id.note_name);
        text = (EditText) findViewById(R.id.Note_text);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        importance = intent.getIntExtra("importance", 0);
        categories = intent.getStringExtra("categories");
        name.setText(intent.getStringExtra("name"));
        text.setText(intent.getStringExtra("text"));

        //Добавление списка приоритета заметки
        ArrayAdapter<Integer> adapter_importance = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, AutoImportance);
        adapter_importance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner_importence = (Spinner)findViewById(R.id.spin_important2);
        spinner_importence.setAdapter(adapter_importance);
        spinner_importence.setSelection(importance);

        //Добавление списка категории заметки
        ArrayAdapter<String> adapter_categories = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, AutoCategories);
        adapter_categories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner_categories = (Spinner)findViewById(R.id.spin_categories2);
        spinner_categories.setAdapter(adapter_categories);
        spinner_categories.setSelection(adapter_categories.getPosition(categories));

        db = new DB(this);

        but = (Button)findViewById(R.id.button_create);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note(id, name.getText().toString(), text.getText().toString(),
                                        Integer.parseInt(spinner_importence.getSelectedItem().toString()),
                                        spinner_categories.getSelectedItem().toString());
                db.changeNote(note);
                db.close();
                MainActivity.work.reload();
                finish();
            }});
    }

}