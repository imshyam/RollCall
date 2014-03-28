package com.shyam.rollcall;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shyamsundar on 8/17/13.
 */
public class database {

    public static final String key_rowId="_id";
    public static final String key_enrNO="EnrNo";
    public static final String key_name="name";
    public static final String key_today="today";


    private static final String DATABASE_NAME="MyDB";
    private static final String DATABASE_TABLE1="Class_1";
    private static final String DATABASE_TABLE2="Class_2";
    private static final String DATABASE_TABLE3="Class_3";
    private static final String DATABASE_TABLE4="Class_4";
    private static final String DATABASE_TABLE5="Class_5";
    private static final String DATABASE_TABLE6="Class_6";
    private static final String DATABASE_TABLE7="Class_7";
    private static final String DATABASE_TABLE8="Class_8";
    private static final int DATABASE_VERSION=2;

    private dbHelper ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase;



    private class dbHelper extends SQLiteOpenHelper{


        public dbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE1+" ("+
                    key_rowId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    key_name+" TEXT NOT NULL, "+
                    key_today+" TEXT NOT NULL, "+
                    key_enrNO+" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE2+" ("+
                    key_rowId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    key_name+" TEXT NOT NULL, "+
                    key_today+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE3+" ("+
                    key_rowId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    key_name+" TEXT NOT NULL, "+
                    key_today+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE4+" ("+
                    key_rowId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    key_name+" TEXT NOT NULL, "+
                    key_today+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE5+" ("+
                    key_rowId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    key_name+" TEXT NOT NULL, "+
                    key_today+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE6+" ("+
                    key_rowId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    key_name+" TEXT NOT NULL, "+
                    key_today+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE7+" ("+
                    key_rowId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    key_name+" TEXT NOT NULL, "+
                    key_today+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE8+" ("+
                    key_rowId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    key_name+" TEXT NOT NULL, "+
                    key_today+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE1);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE2);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE3);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE4);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE5);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE6);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE7);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE8);
            onCreate(sqLiteDatabase);
        }
    }
    public database(Context c){
        ourContext=c;
    }
    public database open() throws SQLException{

        ourHelper=new dbHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        ourHelper.close();

    }

    //creating entry with name and enrollment no.
    public long createEntry(String str,String enr, String name,String today) {

        ContentValues cv1=new ContentValues();
        cv1.put(key_enrNO,enr);
        cv1.put(key_name,name);
        cv1.put(key_today,today);
        return ourDatabase.insert(str,null,cv1);
    }

    //getting name for specific enrollment no.
    public String getname(String str,long l) throws SQLException {
        String[] columns=new String[]{key_enrNO,key_name};
        Cursor c=ourDatabase.query(str,columns,key_enrNO + "=" +l ,null,null,null,null);
        if(c!=null){
            c.moveToNext();
            String name=c.getString(c.getColumnIndex("name"));
            return name;
        }
        return  null;
    }

    //Modifying a existing entry
    public void Modify(String str,String enr, String name) {
        ContentValues cvUp=new ContentValues();
        cvUp.put(key_name,name);
        ourDatabase.update(str,cvUp,key_enrNO + "=" + enr,null);//editing using Enrollment no.
    }



    public void deleteByEnr(String str,long l) {
        ourDatabase.delete(str,key_enrNO+"="+l,null);
    }

//returning Enrollment no.


    public List<String> getEnr1(String s) {
        List<String> set = new ArrayList<String>();
        Cursor cursor=ourDatabase.rawQuery("SELECT EnrNo FROM " + s,new String[] {});

        while(cursor.moveToNext()){
            String cls=cursor.getString(cursor.getColumnIndex("EnrNo"));


            set.add(cls);
        }

        cursor.close();

        return set;
    }
    //mofiying today
    public void modifyToday(String clas, long enr, String s1) {
        ContentValues cvUp=new ContentValues();
        cvUp.put(key_today,s1);
        ourDatabase.update(clas,cvUp,key_enrNO + "=" + enr,null);//editing using Enrollment no.
    }

    public String getToday(String clas, long l) {
        String[] columns=new String[]{key_enrNO,key_today};
        Cursor c=ourDatabase.query(clas,columns,key_enrNO + "=" +l ,null,null,null,null);
        if(c!=null){
            c.moveToNext();
            String tod=c.getString(c.getColumnIndex("today"));
            return tod;
        }
        return  null;
    }

}