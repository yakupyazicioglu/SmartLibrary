package mirrket.com.smartlibrary.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yy on 30.04.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final int NEW_DATABASE_VERSION = 2;

    public static final String DB_NAME="kutuphane.db";
    public static final String TABLE_ALLBOOK="Tüm_Kitaplar";
    public static final String TABLE_FAVORI="Favoriler";
    public static final String TABLE_DEFAULT_RAF="Varsayılan";
    public static final String TABLE_DEFAULT_RAF_FK="VarsayılanFK";
    public static final String TABLE_YAZARLAR="yazarlar";
    public static final String TABLE_SILINENLER="silinenler";

    public static String ID= "_id";
    public static String FK= "fk";
    public static String KITAP = "kitap";
    public static String YAZAR = "yazar";
    public static String SAYFA = "sayfa";
    public static String RAF = "raf";
    public static String READ = "read";
    public static String NOTLAR = "notlar";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ALL = "CREATE TABLE " + TABLE_ALLBOOK + "("
                + ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KITAP + " TEXT,"
                + YAZAR + " TEXT,"
                + SAYFA + " TEXT,"
                + RAF   + " TEXT,"
                + READ  + " TEXT,"
                + NOTLAR   + " TEXT,"
                + FK + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE_ALL);

        String CREATE_TABLE_FAV = "CREATE TABLE " + TABLE_FAVORI + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KITAP + " TEXT,"
                + YAZAR + " TEXT,"
                + SAYFA + " TEXT,"
                + FK + " INTEGER NOT NULL,"
                + " FOREIGN KEY ("+FK+") REFERENCES "+TABLE_ALLBOOK+"("+ID+"));";
                //+ FK + " INTEGER" +" FOREIGN KEY ("+FK+") REFERENCES "+TABLE_ALLBOOK+"("+ID+")";
        db.execSQL(CREATE_TABLE_FAV);

        /*String CREATE_TABLE_DEFAULT_RAF = "CREATE TABLE " + TABLE_DEFAULT_RAF + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KITAP + " TEXT,"
                + YAZAR + " TEXT,"
                + SAYFA + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_DEFAULT_RAF);*/

        /*String CREATE_TABLE_DEFAULT_RAF_FK = "CREATE TABLE " + TABLE_DEFAULT_RAF_FK + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FK + " INTEGER," +" FOREIGN KEY ("+FK+") REFERENCES "+TABLE_ALLBOOK+"("+ID+")"
                + KITAP + " TEXT,"
                + YAZAR + " TEXT,"
                + SAYFA + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_DEFAULT_RAF_FK);*/


    }


    public void kitapSil(int id){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_ALLBOOK, ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void kitapRafSil(String rafadi, int id){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(rafadi, ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void favSil(int id){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FAVORI, ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void rafSil(String id){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + id);
        db.close();
    }

    public void rafLast(String id){

        SQLiteDatabase db = getReadableDatabase();
        String lastIndex = "SELECT seq from sqlite_sequence where" +TABLE_ALLBOOK;
        db.execSQL("DROP TABLE IF EXISTS " + id);
        db.close();
    }

    public void allLast(){

        SQLiteDatabase db = getReadableDatabase();
        String lastIndex = "SELECT seq from sqlite_sequence where" +TABLE_ALLBOOK;
        db.execSQL(lastIndex);
        db.close();
        return;
    }

    public HashMap<String, String> kitapDetay(int id){

        HashMap<String,String> kitap = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_ALLBOOK+ " WHERE _id="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            kitap.put(KITAP, cursor.getString(1));
            kitap.put(YAZAR, cursor.getString(2));
            kitap.put(SAYFA, cursor.getString(3));
            kitap.put(RAF, cursor.getString(4));
            kitap.put(READ, cursor.getString(5));
            kitap.put(NOTLAR, cursor.getString(6));
        }
        cursor.close();
        db.close();

        return kitap;
    }

    public HashMap<String, String> rafDetay(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String,String> rafkitaplar = new HashMap<String,String>();
        //ArrayList<String> rafkitaplar = new ArrayList<String>();
        String selectQuery = "SELECT * FROM WHERE _id="+id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            {
            rafkitaplar.put(ID, cursor.getString(1));
            }
        cursor.close();
        db.close();
        return rafkitaplar;
    }

    public void kitapEkle(String kitap, String yazar, String sayfa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KITAP, kitap);
        values.put(YAZAR, yazar);
        values.put(SAYFA, sayfa);

        db.insert(TABLE_ALLBOOK, null, values);
        db.close();
    }

    public void favEkle(String kitap, String yazar, String sayfa, String fk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KITAP, kitap);
        values.put(YAZAR, yazar);
        values.put(SAYFA, sayfa);
        values.put(FK, fk);

        db.insert(TABLE_FAVORI, null, values);
        db.close();
    }

    public void rafaEkle(String kitap, String yazar, String sayfa ,String rafAdi, int fk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KITAP, kitap);
        values.put(YAZAR, yazar);
        values.put(SAYFA, sayfa);
        values.put(FK, fk);
        //values.put(READ, read);
        //values.put(NOTLAR, not);

        db.insert(rafAdi , null, values);
        db.close();
    }


    public ArrayList<HashMap<String, String>> tumkitaplar(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_ALLBOOK;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> kitaplist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                kitaplist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return kitaplist;
    }

    public ArrayList<String> tumraflar(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> raflist = new ArrayList<String>();
        String selectQuery = "SELECT name FROM sqlite_master WHERE type='table'" +
                " AND name NOT LIKE 'android_metadata'" +
                " AND name NOT LIKE 'sqlite_sequence'" +
                " AND name NOT LIKE 'favoriler'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.moveToFirst())
        {
            while ( !cursor.isAfterLast() ){
                raflist.add( cursor.getString( cursor.getColumnIndex("name")));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return raflist;
    }

    public ArrayList<String> rafKitaplar(String rafadi){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> rafkitaplar = new ArrayList<String>();
        String selectQuery = "SELECT fk FROM " + rafadi;
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.moveToFirst())
        {
            while ( !cursor.isAfterLast() ){
                //raflist.add( cursor.getString( cursor.getColumnIndex("name")));
                rafkitaplar.add( cursor.getString( cursor.getColumnIndex("fk")));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return rafkitaplar;
    }

    public ArrayList<HashMap<String, String>> rafKitaplar1(String rafadi){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT kitap,fk FROM " + rafadi;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> rafdakikitaplar = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                rafdakikitaplar.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return rafdakikitaplar;
    }


    public ArrayList<HashMap<String, String>> favorikitaplar(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_FAVORI;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> kitaplist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                kitaplist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return kitaplist;
    }

    public void kitapDuzenle(String kitap, String yazar, String sayfa, String raf, String read, String not, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KITAP, kitap);
        values.put(YAZAR, yazar);
        values.put(SAYFA, sayfa);
        values.put(RAF, raf);
        values.put(READ, read);
        values.put(NOTLAR, not);

        db.update(TABLE_ALLBOOK, values, ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void rafDuzenle(String name, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KITAP, name);

        db.update(TABLE_ALLBOOK, values, ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ALLBOOK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALLBOOK, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALLBOOK + TABLE_FAVORI);
        onCreate(db);

    }

}
