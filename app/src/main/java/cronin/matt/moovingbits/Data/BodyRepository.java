package cronin.matt.moovingbits.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cronin.matt.moovingbits.Model.Body;

/**
 * Created by mcronin on 10/5/2015.
 */
public class BodyRepository implements CRUDHelper<Body> {

    private DatabaseHelper databaseHelper;

    public BodyRepository(Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public long add(Body item) {
        ContentValues values = getContentValues(item);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        long newId = db.insert(databaseHelper.TABLE_BODY, null, values);
        db.close();
        return newId;
    }

    @Override
    public List<Body> getAll() {
        return getAllWhere("");
    }

    private List<Body> getAllWhere(String where){
        String query = "SELECT * FROM " + databaseHelper.TABLE_BODY + " " + where;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        List<Body> bodies = new ArrayList<>();

        while (cursor.moveToNext()) {
            Body body = new Body(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5));
            bodies.add(body);
        }
        db.close();
        return bodies;
    }

    @Override
    public Body getItem(long id) {
        List<Body> bodies = getAllWhere("WHERE " + databaseHelper.COLUMN_BODY_ID + " = " + id);
        Body body = bodies.get(0);
        return body;
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM " + databaseHelper.TABLE_BODY + " WHERE " + databaseHelper.COLUMN_BODY_ID + " = " + id;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public void deleteByRequest(long requestId){
        String sql = "DELETE FROM " + databaseHelper.TABLE_BODY + " WHERE " + databaseHelper.COLUMN_REQUEST_ID + " = " + requestId;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    @Override
    public void update(long id, Body item) {
        ContentValues values = getContentValues(item);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.update(databaseHelper.TABLE_BODY, values, databaseHelper.COLUMN_BODY_ID + "=" + id, null);
        db.close();
    }

    public List<Body> getAllByRequest(long requestId){
        return getAllWhere("WHERE " + databaseHelper.COLUMN_REQUEST_ID + " = " + requestId);
    }

    private ContentValues getContentValues(Body item){
        ContentValues values = new ContentValues();
        values.put(databaseHelper.COLUMN_BODY_KEY, item.getKey());
        values.put(databaseHelper.COLUMN_BODY_VALUE, item.getValue());
        values.put(databaseHelper.COLUMN_BODY_RAW, item.getRawData());
        values.put(databaseHelper.COLUMN_BODY_TYPE, item.getEncodingType());
        values.put(databaseHelper.COLUMN_REQUEST_ID, item.getRequestId());
        return values;
    }
}
