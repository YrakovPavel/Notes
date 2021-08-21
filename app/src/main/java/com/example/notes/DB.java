package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class DB extends SQLiteOpenHelper{

    public DB(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTable = "CREATE TABLE notes (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            " name TEXT NOT NULL, text TEXT, " +
                            "dateWrite TEXT NOT NULL DEFAULT (datetime(current_timestamp, 'localtime')), " +
                            "dateChange TEXT, importance INTEGER, categories TEXT)";
        db.execSQL(CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Добавить заметку
    public boolean addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", note.getName());
        cv.put("text", note.getText());
        cv.put("importance", note.getImportance());
        cv.put("categories", note.getCategories());
        long insert = db.insert("notes", null, cv);
        db.close();
        if (insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //Получить список заметок
    public ArrayList getNotes(){
        ArrayList list = new ArrayList();
        String query = "SELECT * FROM notes";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                int NoteId = cursor.getInt(0);
                String NoteName = cursor.getString(1);
                String NoteText = cursor.getString(2);
                String NoteDateWrite= cursor.getString(3);
                String NoteDateChange = cursor.getString(4);
                int NoteImportance = cursor.getInt(5);
                String NoteCategories = cursor.getString(6);
                Note note = new Note(NoteId, NoteName, NoteText, NoteDateWrite, NoteDateChange,
                                    NoteImportance, NoteCategories);
                list.add(note);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    //Изменить заметку
    public boolean changeNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        String CreateTable = "UPDATE notes SET name='" + note.getName() + "',text='" + note.getText() +
                            "',dateChange= datetime(current_timestamp, 'localtime')," +
                            "importance='" + note.getImportance() + "',categories='" + note.getCategories() +
                            "' WHERE id=" + note.getId();
        db.execSQL(CreateTable);
       return true;
    }

    //Удалить заметку из списка
    public boolean delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long insert = db.delete("notes", "id = ?",
                                new String[]{Integer.toString(id)});
        if (insert == -1){
            return false;
        }
        else{
            return true;
        }
    }
}
