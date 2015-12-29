package unice.mbds.org.tpresto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import unice.mbds.org.tpresto.model.Order;

/**
 * Created by Zac on 14/12/2015.
 */
public class OrderDbHelper  extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + OrderDbContract.OrderEntry.TABLE_NAME + " (" +
                    OrderDbContract.OrderEntry._ID + " INTEGER PRIMARY KEY," +
                    OrderDbContract.OrderEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    OrderDbContract.OrderEntry.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    OrderDbContract.OrderEntry.COLUMN_PRIX + TEXT_TYPE + COMMA_SEP +
                    OrderDbContract.OrderEntry.COLUMN_CALORIES + TEXT_TYPE + COMMA_SEP +
                    OrderDbContract.OrderEntry.COLUMN_TYPE + TEXT_TYPE + COMMA_SEP +
                    OrderDbContract.OrderEntry.COLUMN_PICTURE + TEXT_TYPE + COMMA_SEP +
                    OrderDbContract.OrderEntry.COLUMN_DISCOUNT + TEXT_TYPE + COMMA_SEP +
                    OrderDbContract.OrderEntry.COLUMN_CREATEDAT + TEXT_TYPE + COMMA_SEP +
                    OrderDbContract.OrderEntry.COLUMN_UPDATEDAT + TEXT_TYPE + COMMA_SEP +
                    OrderDbContract.OrderEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    OrderDbContract.OrderEntry.COLUMN_QUANTITE+ TEXT_TYPE +
    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + OrderDbContract.OrderEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "OrderReader.db";

    public OrderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public boolean deleteOrder(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db.delete(OrderDbContract.OrderEntry.TABLE_NAME, OrderDbContract.OrderEntry.COLUMN_NAME_ENTRY_ID + " LIKE '" + id + "'", null) <= 0)
            return false;
        else
            return true;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
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

    public boolean insertOrder(Order o){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(OrderDbContract.OrderEntry.COLUMN_NAME, o.getName());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_DESCRIPTION, o.getDescription());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_PRIX, o.getPrice());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_CALORIES, o.getCalories());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_TYPE, o.getType());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_PICTURE, o.getPicture());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_DISCOUNT, o.getDiscount());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_CREATEDAT, o.getCreatedAt());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_UPDATEDAT, o.getUpdatedAt());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_NAME_ENTRY_ID, o.getId());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_QUANTITE, o.getQuantite());
        db.insert(OrderDbContract.OrderEntry.TABLE_NAME, null, contentValues);

        return true;
    }

    public boolean updateQuantite(Order o){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_QUANTITE,o.getQuantite());
        db.update(OrderDbContract.OrderEntry.TABLE_NAME, contentValues,OrderDbContract.OrderEntry.COLUMN_NAME_ENTRY_ID +" LIKE '"+o.getId()+"'",null);
        return true;
    }
    public boolean updateOrder(Order o){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(OrderDbContract.OrderEntry.COLUMN_NAME, o.getName());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_DESCRIPTION, o.getDescription());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_PRIX, o.getPrice());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_CALORIES, o.getCalories());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_TYPE, o.getType());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_PICTURE, o.getPicture());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_DISCOUNT, o.getDiscount());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_CREATEDAT, o.getCreatedAt());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_UPDATEDAT, o.getUpdatedAt());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_NAME_ENTRY_ID, o.getId());
        contentValues.put(OrderDbContract.OrderEntry.COLUMN_QUANTITE, o.getQuantite());
        db.update(OrderDbContract.OrderEntry.TABLE_NAME, contentValues,OrderDbContract.OrderEntry.COLUMN_NAME_ENTRY_ID +" LIKE '"+o.getId()+"'",null);

        return true;
    }

    public Cursor getData(String id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + OrderDbContract.OrderEntry.TABLE_NAME + " where " + OrderDbContract.OrderEntry.COLUMN_NAME_ENTRY_ID + " LIKE '" + id + "'", null);
        return res;
    }
    public Order getOrderDb(String id){
        Order order = new Order();
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.print("\n\ndb name Order : "+db.toString());

        Cursor res =  db.rawQuery( "select * from "+OrderDbContract.OrderEntry.TABLE_NAME+" where "+OrderDbContract.OrderEntry.COLUMN_NAME_ENTRY_ID+" LIKE '"+id+"'", null );
        res.moveToFirst();
        if(res.getCount()>0){
            order.setName(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_NAME)));
            order.setDescription(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_DESCRIPTION)));
            order.setPrice(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_PRIX)));
            order.setCalories(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_CALORIES)));
            order.setType(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_TYPE)));
            order.setPicture(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_PICTURE)));
            order.setDiscount(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_DISCOUNT)));
            order.setUpdatedAt(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_UPDATEDAT)));
            order.setCreatedAt(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_CREATEDAT)));
            order.setId(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_NAME_ENTRY_ID)));
            order.setQuantite(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_QUANTITE)));
            return order;
        }
        return null;

    }
    public boolean existeOrder(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+OrderDbContract.OrderEntry.TABLE_NAME+" where "+OrderDbContract.OrderEntry.COLUMN_NAME_ENTRY_ID+" LIKE '"+id+"'", null );
        if(res!=null) return true;
        else return false;
    }
    public List<Order> getAllOrders(){
        List<Order> list = new ArrayList<Order>();
        Order order;
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+OrderDbContract.OrderEntry.TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            order = new Order();
            order.setName(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_NAME)));
            order.setDescription(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_DESCRIPTION)));
            order.setPrice(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_PRIX)));
            order.setCalories(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_CALORIES)));
            order.setType(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_TYPE)));
            order.setPicture(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_PICTURE)));
            order.setDiscount(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_DISCOUNT)));
            order.setUpdatedAt(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_UPDATEDAT)));
            order.setCreatedAt(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_CREATEDAT)));
            order.setId(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_NAME_ENTRY_ID)));
            order.setQuantite(res.getString(res.getColumnIndex(OrderDbContract.OrderEntry.COLUMN_QUANTITE)));
            list.add(order);
            res.moveToNext();
        }
        return list;
    }
}
