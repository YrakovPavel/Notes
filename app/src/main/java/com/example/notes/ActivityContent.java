package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityContent extends AppCompatActivity {
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        //Получение отправленных данных с прошлой страницы
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        String text = intent.getStringExtra("text");
        String dateWrite = intent.getStringExtra("dateWrite");
        String dateChange = intent.getStringExtra("dateChange");
        int importance = intent.getIntExtra("importance", -1);
        String categories = intent.getStringExtra("categories");

        //Присвоение значений объектам на экране
        TextView textImportance = (TextView) findViewById(R.id.text_importance);
        textImportance.setText("Важноcть: " + String.valueOf(importance));
        TextView textCategories = (TextView)findViewById(R.id.text_categories);
        textCategories.setText("Категория: " + categories);
        TextView textText = (TextView) findViewById(R.id.textText);
        textText.setText(text);
        TextView textDateWrite = (TextView) findViewById(R.id.dateWrite);
        textDateWrite.setText("Дата создания: " + dateWrite);
        TextView textDateChange = (TextView) findViewById(R.id.dateChange);
        if (dateChange != null) {
            textDateChange.setText("Дата редактирования: " + dateChange);
        }

        Button but_del = (Button)findViewById(R.id.but_del);
        Button but_edit = (Button)findViewById(R.id.but_edit);

        but_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityContent.this, NoteChange.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("text", text);
                intent.putExtra("importance", importance);
                intent.putExtra("categories", categories);
                startActivity(intent);
                finish();
            }
        });

        but_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB db = new DB(ActivityContent.this);
                db.delete(id);
                db.close();
                MainActivity.work.reload();
                finish();
            }
        });
    }
}