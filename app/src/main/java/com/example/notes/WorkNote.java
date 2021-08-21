package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class WorkNote {
    private Context context;
    private LinearLayout layout;
    private DB db;
    private String choice = "Всё"; //Категория отображения списка
    private ArrayList<Integer> arrayId = new ArrayList<Integer>();

    WorkNote(Context context, LinearLayout layout){
        this.context = context;
        this.layout = layout;
        this.db = new DB(context);
    }

    //Прикрепить список заметок, полученных из базы данных
    private  void setAllNotes(){
        ArrayList ar = db.getNotes();
        int count = 0;
        ArrayList<Note> array = new ArrayList<Note>();
        for (int i = 0; i < ar.size(); i++){
            Note note = (Note)ar.get(i);
            if (choice.equals("Всё") || note.getCategories().equals(choice)) {
                array.add(note);
                count++;
            }
        }
        Note.setAll(array);
        Note.setCount(count);
    }

    //Присвоить каждой заметке кнопку
    private void showNotes(){
        arrayId = new ArrayList<Integer>();
        for (int i = 0; i < Note.getAll().size(); i++){
            Note obj = (Note) Note.getAll().get(i);
            Button but = new Button(context);
            but.setId(obj.getNumber());
            arrayId.add(obj.getNumber());
            but.setText(obj.getName());
            layout.addView(but);
        }
    }

    //Привязка перехода на следующую страницу
    private void MakeSwitch(){
        for (int i = 0; i < Note.getAll().size(); i++){
            Button but = (Button)layout.findViewById(arrayId.get(i));
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Note note = (Note)Note.getAll().get(view.getId());
                    Intent intent = new Intent(context, ActivityContent.class);
                    intent.putExtra("id", note.getId());
                    intent.putExtra("name", note.getName());
                    intent.putExtra("text", note.getText());
                    intent.putExtra("dateWrite", note.getDateWrite());
                    intent.putExtra("dateChange", note.getDateChange());
                    intent.putExtra("importance", note.getImportance());
                    intent.putExtra("categories", note.getCategories());
                    context.startActivity(intent);
                }
            });
        }
    }

    //Очищает список заметок
    private void deleteNotes(){
        for (int i = 0; i < arrayId.size(); i++){
            Button but = (Button) layout.findViewById(arrayId.get(i));
            layout.removeView(but);
        }
        Note.setCount(0);
    }

    // Перезагрузить список заметок. Следует применять после каждого изменения заметок
    public void reload(){
        deleteNotes();
        setAllNotes();
        showNotes();
        MakeSwitch();
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
