package app.myapp.findlocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import app.myapp.findlocation.Model.ListItem;

public class Database extends SQLiteOpenHelper {


    public static final String DATA_BASE = "myconcat";
    public static final int version = 1;
    public static final String TABLE = "concat";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String LATITUDE = "latitude";
    public static final String LONGTUDE ="longtude";
    public static final String IMAGE = "image";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+ NAME + " TEXT ," + PHONE + " TEXT ," + LATITUDE+ " TEXT,"+LONGTUDE+" TEXT,"+IMAGE+" Text);";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;


    public Database(@Nullable Context context) {
        super(context,DATA_BASE,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
    }





    public long insertData(ListItem itemFoodUser) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,itemFoodUser.name);
        contentValues.put(PHONE,itemFoodUser.phone);
        contentValues.put(LATITUDE,itemFoodUser.lati);
        contentValues.put(LONGTUDE,itemFoodUser.log);
        contentValues.put(IMAGE,itemFoodUser.ImageURL);
        long l= sqLiteDatabase.insert(TABLE, null, contentValues);
        sqLiteDatabase.close();
        return l;
    }



    public int upData(ListItem itemFoodUser) {

        SQLiteDatabase sqLiteDatabase =getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(LATITUDE,itemFoodUser.lati);
        contentValues.put(LONGTUDE,itemFoodUser.log);
        int l= sqLiteDatabase.update(TABLE,contentValues,ID+" ="+itemFoodUser.id,null);
        sqLiteDatabase.close();

        return l;
    }


















    public ArrayList<ListItem> getData() {
        ArrayList<ListItem> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT*FROM " + TABLE, null);
        if (cursor.moveToFirst() && cursor != null) {

            do {

                int id=cursor.getInt(cursor.getColumnIndex(ID));
                String name= cursor.getString(cursor.getColumnIndex(NAME));
                String phone=cursor.getString(cursor.getColumnIndex(PHONE));
                String lati=cursor.getString(cursor.getColumnIndex(LATITUDE));
                String log=cursor.getString(cursor.getColumnIndex(LONGTUDE));
                String image=cursor.getString(cursor.getColumnIndex(IMAGE));

                arrayList.add(new ListItem(id,name,phone,Integer.parseInt(image),lati,log));

            } while (cursor.moveToNext());

            return arrayList;
        }
        return null;
    }





    public ListItem getOneData(String num) {



        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ListItem place = null;
        String[] AllItems = {ID,NAME,PHONE,LATITUDE,LONGTUDE,IMAGE};
       String[] IDI={"%"+num};



        Cursor cursor = sqLiteDatabase.query(TABLE, AllItems, PHONE+" LIKE ?",IDI, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            int id=cursor.getInt(cursor.getColumnIndex(ID));
            String name= cursor.getString(cursor.getColumnIndex(NAME));
            String phone=cursor.getString(cursor.getColumnIndex(PHONE));
            String image=cursor.getString(cursor.getColumnIndex(IMAGE));

            place =new ListItem(id,name,phone,Integer.parseInt(image),null,null);

            cursor.close();

        }
        return place;
    }












































    public ListItem getOneItem(String num) {



        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ListItem place = null;
        String[] AllItems = {ID,NAME,PHONE,LATITUDE,LONGTUDE,IMAGE};
        String[] IDI={num};



        Cursor cursor = sqLiteDatabase.query(TABLE, AllItems, PHONE+" =?",IDI, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            int id=cursor.getInt(cursor.getColumnIndex(ID));
            String name= cursor.getString(cursor.getColumnIndex(NAME));
            String phone=cursor.getString(cursor.getColumnIndex(PHONE));
            String image=cursor.getString(cursor.getColumnIndex(IMAGE));

            place =new ListItem(id,name,phone,Integer.parseInt(image),null,null);

            cursor.close();

        }
        return place;
    }









    public int delete(int id){

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String[] IDI={id+""};
        int ii= sqLiteDatabase.delete(TABLE,ID+" =?",IDI);
        return ii;
    }



}

