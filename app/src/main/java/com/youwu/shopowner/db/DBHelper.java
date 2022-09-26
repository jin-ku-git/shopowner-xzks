package com.youwu.shopowner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author LzCode@qq.com
 * @date on 2018-8-11 08:23:30
 * @describe
 **/

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "youwuu_db";
    public static final String GOODS_TABLE_NAME = "goods";

    public static final String RESTING0RDER_TABLE_NAME = "RESTINGORDER";


    public static final String INVENTORY_TABLE_NAME = "inventory";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String goods_sql = "create table if not exists " + GOODS_TABLE_NAME + " (goods_id_sku text primary key,goods_id integer,goods_sku text,goods_name text,goods_price text,goods_cost_price text,goods_img text" +
                ",stock integer,group_id integer,group_name text,group_sort integer,group_img text,goods_number integer,com_number_state integer,details text,package_id integer,type integer)";
        sqLiteDatabase.execSQL(goods_sql);

        String order_sql = "create table if not exists " + RESTING0RDER_TABLE_NAME + " (id integer primary key,creat_time text,ordernumber_bean text,vip_bean text,shop_car_goods text,order_number text)";
        sqLiteDatabase.execSQL(order_sql);


        String inventory_sql = "create table if not exists " + INVENTORY_TABLE_NAME + " (goods_id integer primary key,goods_sku text,goods_name text,goods_price text,goods_img text" +
                ",stock integer,category_id integer,category_name text,goods_price text)";
        sqLiteDatabase.execSQL(inventory_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String goods_sql = "DROP TABLE IF EXISTS " + GOODS_TABLE_NAME;
        sqLiteDatabase.execSQL(goods_sql);
        onCreate(sqLiteDatabase);


        String order_sql = "DROP TABLE IF EXISTS " + RESTING0RDER_TABLE_NAME;
        sqLiteDatabase.execSQL(order_sql);
        onCreate(sqLiteDatabase);


        String inventory_sql = "DROP TABLE IF EXISTS " + INVENTORY_TABLE_NAME;
        sqLiteDatabase.execSQL(inventory_sql);
        onCreate(sqLiteDatabase);

    }

}
