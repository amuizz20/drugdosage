package com.amtechnology.drugdosage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLDatabase extends SQLiteOpenHelper {
    private static final int DB_Version = 1;
    private static final String DB_Name = "DrugReminder";
    private static final String ID = "id";
    private static final String Medicine = "MedicineName";
    private static final String Dosage = "dosage";
    private static final String Time = "time";
    private static final String Days = "days";
    private static final String email = "email";

    public SQLDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+DB_Name+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+Medicine+" TEXT,"
                +Dosage+" TEXT,"+Time+" TEXT,"+Days+" TEXT,"+email+" TEXT"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DB_Name);
        onCreate(sqLiteDatabase);

    }
    public boolean insertintotable(String medicine,String dosage,String time,String days,String Email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Medicine,medicine);
        contentValues.put(Dosage,dosage);
        contentValues.put(Time,time);
        contentValues.put(Days,days);
        contentValues.put(email,Email);
        long no = sqLiteDatabase.insert(DB_Name,null,contentValues);
        return (no == 1);

    }


    public boolean deletevalue(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int no = sqLiteDatabase.delete(DB_Name,"id="+id,null);
        return (no==1);

    }
    public ArrayList<ReminderDetails> GetAllData(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("Select * from "+DB_Name+" Where email="+""+email+" order by id",null);
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select * from "+DB_Name +" where email = ? "+" order by id", new String[]{email});
        ArrayList<ReminderDetails> CompleteList = new ArrayList<>();

        while (cursor.moveToNext()){
         CompleteList.add(new ReminderDetails(cursor.getString(0),cursor.getString(1),
                 cursor.getString(2),cursor.getString(3),
                 cursor.getString(4)));

        }

        return CompleteList;

    }


}
