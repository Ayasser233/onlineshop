package com.example.shoppingsystem.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.shoppingsystem.Model.CategoryModel;
import com.example.shoppingsystem.Model.ProductModel;

public class MyDataBase extends SQLiteOpenHelper {
    final static String dataName = "shoppingsystem";
    SQLiteDatabase data;
    Context context;

    public MyDataBase(@Nullable Context context) {
        super(context, dataName, null, 3);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table customer (id INTEGER primary key AUTOINCREMENT,username TEXT,email TEXT,password TEXT,birthdate TEXT)");

        db.execSQL("create table category (id integer primary key  autoincrement , name text not null , count integer )");

        db.execSQL("create table rating (id integer primary key  autoincrement , ratenum REAL, nametxt text )");

        db.execSQL("create table product (id integer primary key autoincrement, name text not null ,image blob ," +
                "price real not null , quantity integer not null , quantitySelected integer not null , cate_id integer not null ," +
                "foreign key (cate_id)references category (id))");

        db.execSQL("create table transactions (id integer primary key  autoincrement , customername text, productname text , catgoryname text ,image blob ,date TEXT ,price real ,quantity integer) ");

        db.execSQL("create table cost (id integer primary key  autoincrement , costproducts REAL)");
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
        data=getReadableDatabase();
        String[] arg={prodName};
        Cursor cursor=data.rawQuery("Select quantitySelected from product where name like ?",arg);
        cursor.moveToFirst();
        data.close();
        return cursor.getInt(0);
    }
    public String getCatId(String name ){
        data=getReadableDatabase();
        String[]args={name};
        Cursor cursor=data.rawQuery("select id from category where name =?",args);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            data.close();
            return cursor.getString(0);
        }
        data.close();
        cursor.close();
        return null;
    }
    public Cursor getCategory(){
        data=getReadableDatabase();
        String []fields={"id","name","count"};
        Cursor cursor= data.query("category",fields,null,null,null,null,null);
        if (cursor.getCount()>0)
            cursor.moveToFirst();
        data.close();
        return cursor;
    }
    public void insertCategory(CategoryModel cate, int count){
        data=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",cate.getCat_name());
        values.put("count",count);
        data.insert("category",null,values);
        data.close();
    }

    public void insertCost(float cost){
        data=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("costproducts",cost);
        data.insert("cost",null,values);
        data.close();
    }
    public String insertProduct(ProductModel product){
        data=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",product.getProName());
        values.put("image",product.getProImage());
        values.put("price",product.getPrice());
        values.put("quantity",product.getPro_quantity());
        values.put("cate_id",product.getCatId());
        values.put("quantitySelected",0);
        long re=data.insert("product",null,values);
        data.close();
        if(re==-1)
            return "error quantitySelected";
        else
            return "inserted quantitySelected";
    }
    public Cursor getProductById(String id){
        data=getReadableDatabase();
        String []args={id};
        Cursor c=data.rawQuery("select * from product where id=?",args);
        if(c!=null){
            c.moveToFirst();
        }
        data.close();
        return c;
    }
    public String delete(String id){
        data=getWritableDatabase();
        long re=data.delete("product","id=?",new String[]{id});
        data.close();
        if(re==-1)
            return "error ";
        else
            return "product deleted";
    }

    public String updateProduct(ProductModel product, String id) {
        data=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",product.getProName());
        values.put("image",product.getProImage());
        values.put("price",product.getPrice());
        values.put("quantity",product.getPro_quantity());
        values.put("cate_id",product.getCatId());

       long re =data.update("product",values,"id=?",new String[]{id});

        data.close();
        if(re==-1)
            return "error ";
        else
            return "product deleted";
    }
}
