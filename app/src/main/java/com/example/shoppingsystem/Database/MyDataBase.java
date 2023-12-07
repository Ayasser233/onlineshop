package com.example.shoppingsystem.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.shoppingsystem.Model.ProductModel;

public class MyDataBase extends SQLiteOpenHelper {
    final static String dataName = "shoppingsystem";
    SQLiteDatabase sqLiteDatabase;
    public MyDataBase(@Nullable Context context) {
        super(context, dataName, null, 3);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLiteDatabase.execSQL("create table customer (id INTEGER primary key AUTOINCREMENT,username TEXT,email TEXT unique,password TEXT,birthdate TEXT)");

        sqLiteDatabase.execSQL("create table category (id integer primary key  autoincrement , name text not null , count integer )");

        sqLiteDatabase.execSQL("create table rating (id integer primary key  autoincrement , ratenum REAL, nametxt text )");

        sqLiteDatabase.execSQL("create table product (id integer primary key autoincrement, name text not null ,image blob ," +
                "price real not null , quantity integer not null , quantitySelected integer not null , category_id integer not null ," +
                "foreign key (category_id)references category (id))");

        sqLiteDatabase.execSQL("create table transactions (id integer primary key  autoincrement , customername text, productname text , catgoryname text ,image blob ,date TEXT ,price real ,quantity integer) ");

        sqLiteDatabase.execSQL("create table cost (id integer primary key  autoincrement , costproducts REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists customer");
        db.execSQL("drop table if exists category");
        db.execSQL("drop table if exists product");
        db.execSQL("drop table if exists rating");
        db.execSQL("drop table if exists transactions");
        db.execSQL("drop table if exists cost");
        onCreate(db);
    }

    public int getProductSelected(String prodName) {
        return 0;
    }
    public String getCatId(String name ){
        sqLiteDatabase=getReadableDatabase();
        String[]args={name};
        Cursor cursor=sqLiteDatabase.rawQuery("select id from category where name =?",args);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            sqLiteDatabase.close();
            return cursor.getString(0);
        }
        sqLiteDatabase.close();
        cursor.close();
        return null;
    }
    public String  insertProduct(ProductModel product){
        sqLiteDatabase=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",product.getProName());
        values.put("image",product.getProImage());
        values.put("price",product.getPrice());
        values.put("quantity",product.getPro_quantity());
        values.put("cate_id",product.getCatId());
        values.put("quantitySelected",0);
        long re=sqLiteDatabase.insert("product",null,values);
        sqLiteDatabase.close();
        if(re==-1)
            return "error quantitySelected";
        else
            return "inserted quantitySelected";
    }
}
