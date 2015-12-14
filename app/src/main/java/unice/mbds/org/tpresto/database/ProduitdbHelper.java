package unice.mbds.org.tpresto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import unice.mbds.org.tpresto.model.Product;

/**
 * Created by Zac on 13/12/2015.
 */
public class ProduitdbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ProductdbContract.ProductEntry.TABLE_NAME + " (" +
                    ProductdbContract.ProductEntry._ID + " INTEGER PRIMARY KEY," +
                    ProductdbContract.ProductEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ProductdbContract.ProductEntry.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    ProductdbContract.ProductEntry.COLUMN_PRIX + TEXT_TYPE + COMMA_SEP +
                    ProductdbContract.ProductEntry.COLUMN_CALORIES + TEXT_TYPE + COMMA_SEP +
                    ProductdbContract.ProductEntry.COLUMN_TYPE + TEXT_TYPE + COMMA_SEP +
                    ProductdbContract.ProductEntry.COLUMN_PICTURE + TEXT_TYPE + COMMA_SEP +
                    ProductdbContract.ProductEntry.COLUMN_DISCOUNT + TEXT_TYPE + COMMA_SEP +
                    ProductdbContract.ProductEntry.COLUMN_CREATEDAT + TEXT_TYPE + COMMA_SEP +
                    ProductdbContract.ProductEntry.COLUMN_UPDATEDAT + TEXT_TYPE + COMMA_SEP +
                    ProductdbContract.ProductEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProductdbContract.ProductEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ProductReader.db";

    public ProduitdbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertProduct(Product p){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ProductdbContract.ProductEntry.COLUMN_NAME, p.getName());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_DESCRIPTION, p.getDescription());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_PRIX, p.getPrice());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_CALORIES, p.getCalories());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_TYPE, p.getType());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_PICTURE, p.getPicture());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_DISCOUNT, p.getDiscount());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_CREATEDAT, p.getCreatedAt());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_UPDATEDAT, p.getUpdatedAt());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_NAME_ENTRY_ID, p.getId());

        db.insert(ProductdbContract.ProductEntry.TABLE_NAME, null, contentValues);

        return true;
    }

    public boolean updateProduct(Product p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ProductdbContract.ProductEntry.COLUMN_NAME, p.getName());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_DESCRIPTION, p.getDescription());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_PRIX, p.getPrice());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_CALORIES, p.getCalories());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_TYPE, p.getType());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_PICTURE, p.getPicture());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_DISCOUNT, p.getDiscount());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_CREATEDAT, p.getCreatedAt());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_UPDATEDAT, p.getUpdatedAt());
        contentValues.put(ProductdbContract.ProductEntry.COLUMN_NAME_ENTRY_ID, p.getId());

        db.update(ProductdbContract.ProductEntry.TABLE_NAME, contentValues, "id = ? ", new String[]{ProductdbContract.ProductEntry.COLUMN_NAME_ENTRY_ID});

        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ProductdbContract.ProductEntry.TABLE_NAME+" where "+ProductdbContract.ProductEntry.COLUMN_NAME_ENTRY_ID+"="+id+"", null );
        return res;
    }

    public ArrayList<Product> getAllProducts(){
        ArrayList<Product> array_list = new ArrayList<Product>();
        Product p = new Product();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ProductdbContract.ProductEntry.TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            p.setName(res.getString(res.getColumnIndex(ProductdbContract.ProductEntry.COLUMN_NAME)));
            p.setDescription(res.getString(res.getColumnIndex(ProductdbContract.ProductEntry.COLUMN_DESCRIPTION)));
            p.setPrice(res.getString(res.getColumnIndex(ProductdbContract.ProductEntry.COLUMN_PRIX)));
            p.setCalories(res.getString(res.getColumnIndex(ProductdbContract.ProductEntry.COLUMN_CALORIES)));
            p.setType(res.getString(res.getColumnIndex(ProductdbContract.ProductEntry.COLUMN_TYPE)));
            p.setPicture(res.getString(res.getColumnIndex(ProductdbContract.ProductEntry.COLUMN_PICTURE)));
            p.setDiscount(res.getString(res.getColumnIndex(ProductdbContract.ProductEntry.COLUMN_DISCOUNT)));
            p.setUpdatedAt(res.getString(res.getColumnIndex(ProductdbContract.ProductEntry.COLUMN_UPDATEDAT)));
            p.setCreatedAt(res.getString(res.getColumnIndex(ProductdbContract.ProductEntry.COLUMN_CREATEDAT)));
            p.setId(res.getString(res.getColumnIndex(ProductdbContract.ProductEntry.COLUMN_NAME_ENTRY_ID)));

            array_list.add(p);
            res.moveToNext();
        }
        return array_list;
    }

}
