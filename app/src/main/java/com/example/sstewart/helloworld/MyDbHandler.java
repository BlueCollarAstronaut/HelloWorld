package com.example.sstewart.helloworld;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by sstewart on 3/11/2015.
 */
public class MyDbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    private static final String TABLE_PRODUCTS = "products";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_QUANTITY = "quantity";


    public MyDbHandler(
            Context context,
            String name,
            SQLiteDatabase.CursorFactory cursorFactory,
            int version) {
        super(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        String CREATE_PRODUCT_TABLE =
                "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_PRODUCTNAME + " TEXT, " +
                    COLUMN_QUANTITY + " INTEGER "
                + ")";
       database.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // For our purposes, an upgrate will just drop the old database and create a new one
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(database);
    }

    public void addProduct(String productName){

        // See if the product already exists.   If it does, update with an incremented quantity.
        // Otherwise, insert a new record (w/ quantity = 1);

        Product product = findProduct(productName);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PRODUCTNAME,  productName);
        if (product == null) {
            values.put(COLUMN_QUANTITY, 1);
            db.insert(TABLE_PRODUCTS, null, values);
        } else {
            values.put(COLUMN_QUANTITY, product.getQuantity() + 1);
            String productIdAsString = Integer.toString(product.getId());
            db.update(TABLE_PRODUCTS, values, COLUMN_ID + "=?", new String[]{productIdAsString});
        }
        db.close();
    }

    public void removeProduct(String productName){

        // See if the product already exists with a quantity > 1.   If it does, reduce inventory; otherwise, delete the item

        Product product = findProduct(productName);

        // If product doesn't exist, do nothing
        if (product != null) {

            SQLiteDatabase db = this.getWritableDatabase();
            int quantity = product.getQuantity();
            String productIdAsString = Integer.toString(product.getId());

            if (quantity > 1) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_PRODUCTNAME, productName);
                values.put(COLUMN_QUANTITY, product.getQuantity() - 1);
                db.update(TABLE_PRODUCTS, values, COLUMN_ID + "=?", new String[]{ productIdAsString });
            }else {
                db.delete(TABLE_PRODUCTS, COLUMN_ID + "=?", new String[]{ productIdAsString });
            }
            db.close();
        }
    }
    private String getProductQueryString(String productName) {
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " = \"" + productName + "\"";
        return query;
    }


    public Product findProduct(String productName) {
        String query = getProductQueryString(productName);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Product product = new Product();

        if  (cursor.moveToFirst()) {
            //cursor.moveToFirst();
            product.setId(Integer.parseInt(cursor.getString((0))));
            product.setProductName(cursor.getString(1));
            product.setQuantity(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else {
            product = null;
        }
        db.close();
        return product;
    }

}
