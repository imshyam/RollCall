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
public class dbAttendance {

    public static final String key_enrNO="EnrNo";
    public static final String key_total="total";
    public static final String key_attend="attend";


    private static final String DATABASE_NAME="MyDB_2";
    private static final String DATABASE_TABLE1="Class_1";
    private static final String DATABASE_TABLE2="Class_2";
    private static final String DATABASE_TABLE3="Class_3";
    private static final String DATABASE_TABLE4="Class_4";
    private static final String DATABASE_TABLE5="Class_5";
    private static final String DATABASE_TABLE6="Class_6";
    private static final String DATABASE_TABLE7="Class_7";
    private static final String DATABASE_TABLE8="Class_8";
    private static final int DATABASE_VERSION=1;

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
                    key_attend+" TEXT NOT NULL, "+
                    key_total+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE2+" ("+
                    key_attend+" TEXT NOT NULL, "+
                    key_total+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE3+" ("+
                    key_attend+" TEXT NOT NULL, "+
                    key_total+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE4+" ("+
                    key_attend+" TEXT NOT NULL, "+
                    key_total+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE5+" ("+
                    key_attend+" TEXT NOT NULL, "+
                    key_total+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE6+" ("+
                    key_attend+" TEXT NOT NULL, "+
                    key_total+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE7+" ("+
                    key_attend+" TEXT NOT NULL, "+
                    key_total+" TEXT NOT NULL, "+
                    key_enrNO +" TEXT NOT NULL);"

            );
            sqLiteDatabase.execSQL("CREATE TABLE "+DATABASE_TABLE8+" ("+
                    key_attend+" TEXT NOT NULL, "+
                    key_total+" TEXT NOT NULL, "+
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
    public dbAttendance(Context c){
        ourContext=c;
    }
    public dbAttendance open() throws SQLException{

        ourHelper=new dbHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        ourHelper.close();

    }
    public long createEntry1(String clas, String s) {
        ContentValues cv1=new ContentValues();
        cv1.put(key_enrNO,s);
        cv1.put(key_attend,"0");
        cv1.put(key_total,"0");
        return ourDatabase.insert(clas,null,cv1);
    }
    //--------------------creating new column--------------------------------------
    /*public void insertColumn(String column){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MODULE, column);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }*/
    //adding attendance
    public void modifyAtten(String clas, long l, long l1) {
        ContentValues cvUp=new ContentValues();
        String[] columns=new String[]{key_enrNO,key_attend,key_total};
        Cursor c=ourDatabase.query(clas,columns,key_enrNO + "=" +l ,null,null,null,null);
        if(c!=null){
            c.moveToNext();
            String tod=c.getString(c.getColumnIndex("attend"));
            String tat=c.getString(c.getColumnIndex("total"));
            long newAtten=Long.parseLong(tod);
            long total=Long.parseLong(tat);
            if(l1==1)
            {
                newAtten+=1;
                total+=1;
                cvUp.put(key_total,total);
                cvUp.put(key_attend,newAtten);
            }
            else{
                total+=1;
                cvUp.put(key_total,total);
                cvUp.put(key_attend,newAtten);
            }

        }
        ourDatabase.update(clas,cvUp,key_enrNO + "=" + l,null);//editing attendance
    }
//getting attendance
    public String getAtten(String str, long enr) {
        String[] columns=new String[]{key_enrNO,key_attend};
        Cursor c=ourDatabase.query(str,columns,key_enrNO + "=" +enr ,null,null,null,null);
        if(c!=null){
            c.moveToNext();
            String atten=c.getString(c.getColumnIndex("attend"));
            return atten;
        }
        return  null;
    }
    public String getTotal(String str, long enr) {
        String[] columns=new String[]{key_enrNO,key_total};
        Cursor c=ourDatabase.query(str,columns,key_enrNO + "=" +enr ,null,null,null,null);
        if(c!=null){
            c.moveToNext();
            String tot=c.getString(c.getColumnIndex("total"));
            return tot;
        }
        return  null;
    }

}