package com.example.kouqiang;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserData extends SQLiteOpenHelper{
	private static final String DATABASE_NAME="whuss.db";
	private static final String TABLE_NAME="user";
	private static final String TABLE_NAME_PASSWORD ="password";
	private static final int DATABASE_VERSION=1;
	
	public UserData(Context ctx) {		
		super(ctx,DATABASE_NAME,null,DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSql = "CREATE TABLE ["+TABLE_NAME+"] ("
							+"[id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,"							
							+"[username] TEXT  NULL"
							+")";
		db.execSQL(createSql);
		
		createSql = 	   "CREATE TABLE ["+TABLE_NAME_PASSWORD+"] ("
				+"[id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,"							
				+"[password] TEXT  NULL"
				+")";
		db.execSQL(createSql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//db.execSQL("drop table if exists " + TABLE_NAME);
		//onCreate(db);
	}
	
	private static String[] FROM = {"id", "username"};		
	public String getUserName(){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME,FROM,null,null, null, null, null);		
		String rstr = "";
		while(cursor.moveToNext()){
			rstr = cursor.getString(1);			
		}	
		cursor.close();
		return rstr;		
	}
	
	public void saveUserName(String value){
		String key = getUserName();
		SQLiteDatabase db = getReadableDatabase();
		if(key.equals("")){
			db.execSQL("INSERT INTO `"+TABLE_NAME+"` (`username`) VALUES ('"+value+"')");
		}else{
			db.execSQL("update `"+TABLE_NAME+"` set  username = '"+value+"'");
		}		
	}
	
	private static String[] FROMTEPIRES = {"id", "password"};		
	public String getPassword(){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME_PASSWORD,FROMTEPIRES,null,null, null, null, null);		
		String rstr   = "";
		while(cursor.moveToNext()){
			rstr = cursor.getString(1);			
		}	
		cursor.close();
		return rstr;		
	}	
	
	public void savePassword(String value){
		String key = getPassword();
		SQLiteDatabase db = getReadableDatabase();
		if(key.equals("")){
			db.execSQL("INSERT INTO `" + TABLE_NAME_PASSWORD + "` (`password`) VALUES ('"+value+"')");
		}else{
			db.execSQL("update `" + TABLE_NAME_PASSWORD + "` set  password = '"+value+"'");
		}		
	}

}
