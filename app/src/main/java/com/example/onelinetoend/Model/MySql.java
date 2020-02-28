package com.example.onelinetoend.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.onelinetoend.Model.Bean.Bean_Road;
import com.example.onelinetoend.Util.ValueUtil;

public class MySql extends SQLiteOpenHelper {

    public MySql(Context context){
        super(context, "Yibi.db3", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table passedYibi(_no Integer primary key autoincrement," +
                "rows Integer not null," +
                "columns Integer not null," +
                "difficulties Integer not null," +
                "road Text not null)");
        db.execSQL("create table savedYibi(_no Integer primary key autoincrement," +
                "rows Integer not null," +
                "columns Integer not null," +
                "difficulties Integer not null," +
                "road Text not null)");
        db.execSQL("create table errorYibi(_no Integer primary key autoincrement," +
                "rows Integer not null," +
                "columns Integer not null," +
                "difficultiesStr Text not null," +
                "startPosition Integer not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS passedYibi");
        db.execSQL("DROP TABLE IF EXISTS savedYibi");
        db.execSQL("DROP TABLE IF EXISTS errorYibi");
        onCreate(db);
    }

    public boolean checkErrorYibi(int rows, int columns, String difficultiesStr,int startPosition){
        if(ValueUtil.isEmpty(difficultiesStr)) return true;
        Cursor cursor=getReadableDatabase().query("errorYibi",null,"rows=? and columns=? and difficultiesStr=? and startPosition=?"
                ,new String[]{rows+"",columns+"",difficultiesStr,startPosition+""},null,null,null);
        boolean f=cursor.getCount()>0;
        cursor.close();
        return f;
    }

    public void insertErrorYibi(int rows, int columns, String difficultiesStr,int startPosition){
        if(checkErrorYibi(rows,columns,difficultiesStr,startPosition)) return;
        ContentValues values=new ContentValues();
        values.put("rows",rows);
        values.put("columns",columns);
        values.put("difficultiesStr",difficultiesStr);
        values.put("startPosition",startPosition);
        getReadableDatabase().insert("errorYibi",null,values);
    }

    public boolean checkPassedYibiWithRoad(String roadstring){
        Cursor cursor=getReadableDatabase().query("passedYibi",null,"road=?"
                ,new String[]{roadstring},null,null,null);
        boolean f=cursor.getCount()>0;
        cursor.close();
        return f;
    }

    public boolean checkPassedYibi(Bean_Road road){
        if(road==null) return false;
        Cursor cursor=getReadableDatabase().query("passedYibi",null,"rows=? and columns=? and difficulties=? and road=?"
                ,new String[]{road.getRows()+"",road.getColumns()+"",road.getDifficulties()+"",road.getRoadString()},null,null,null);
        boolean f=cursor.getCount()>0;
        cursor.close();
        return f;
    }

    public void insertPassedYibi(Bean_Road road){
        if(checkPassedYibi(road) || road==null) return;
        ContentValues values=new ContentValues();
        values.put("rows",road.getRows());
        values.put("columns",road.getColumns());
        values.put("difficulties",road.getDifficulties());
        values.put("road",road.getRoadString());
        getReadableDatabase().insert("passedYibi",null,values);
    }

    public Cursor getAllPassedYibi(){
        return getReadableDatabase().query("passedYibi",null,null
                ,null,null,null,null);
    }

    public void cleanPassedYibi(){
        getReadableDatabase().delete("passedYibi",null,null);
    }

    public boolean checkSavedYibi(Bean_Road road){
        Cursor cursor=getReadableDatabase().query("savedYibi",null,"rows=? and columns=? and difficulties=? and road=?"
                ,new String[]{road.getRows()+"",road.getColumns()+"",road.getDifficulties()+"",road.getRoadString()},null,null,null);
        boolean f=cursor.getCount()>0;
        cursor.close();
        return f;
    }

    public void insertSavedYibi(Bean_Road road){
        if(checkSavedYibi(road)) return;
        ContentValues values=new ContentValues();
        values.put("rows",road.getRows());
        values.put("columns",road.getColumns());
        values.put("difficulties",road.getDifficulties());
        values.put("road",road.getRoadString());
        getReadableDatabase().insert("savedYibi",null,values);
    }

    public int getSavedCount( int rows, int columns, int difficulties){
        Cursor cursor=getReadableDatabase().query("savedYibi",null,"rows=? and columns=? and difficulties=?"
                ,new String[]{rows+"",columns+"",difficulties+""},null,null,null);
        int c=cursor.getCount();
        cursor.close();
        return c;
    }

    public Cursor getSavedYibi( int rows, int columns, int difficulties){
        return getReadableDatabase().query("savedYibi",null,"rows=? and columns=? and difficulties=?"
                ,new String[]{rows+"",columns+"",difficulties+""},null,null,null);
    }
}
