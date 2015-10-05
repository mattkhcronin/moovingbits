package cronin.matt.moovingbits.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cronin.matt.moovingbits.Model.Request;

/**
 * Created by mattcronin on 10/1/15.
 */
public class RequestRepository implements CRUDHelper<Request> {

    private DatabaseHelper databaseHelper;

    public RequestRepository(Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public long add(Request item) {
        ContentValues values = getContentValues(item);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        long newId = db.insert(databaseHelper.TABLE_REQUEST, null, values);
        db.close();
        return newId;
    }

    @Override
    public List<Request> getAll(){
        String query = "SELECT * FROM " + databaseHelper.TABLE_REQUEST;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        List<Request> requests = new ArrayList<>();

        while (cursor.moveToNext()) {
            Request request = new Request(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3));
            requests.add(request);
        }
        db.close();
        return requests;
    }

    @Override
    public Request getItem(long id) {
        String query = "SELECT * FROM " + databaseHelper.TABLE_REQUEST + " WHERE " + databaseHelper.COLUMN_REQUEST_ID + " = " + id;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Request request = null;

        if (cursor.moveToNext()) {
            request = new Request(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3));
        }
        db.close();
        return request;
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM " + databaseHelper.TABLE_REQUEST + " WHERE " + databaseHelper.COLUMN_REQUEST_ID + " = " + id;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    @Override
    public void update(long id, Request item) {
        ContentValues values = getContentValues(item);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.update(databaseHelper.TABLE_REQUEST, values, databaseHelper.COLUMN_REQUEST_ID + "=" + id, null);
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + databaseHelper.TABLE_REQUEST);
        db.close();
    }

    private ContentValues getContentValues(Request request){
        ContentValues values = new ContentValues();
        //values.put(databaseHelper.COLUMN_REQUEST_ID, item.getId());
        values.put(databaseHelper.COLUMN_REQUEST_TYPE, request.getType());
        values.put(databaseHelper.COLUMN_REQUEST_ROUTE, request.getRoute());
        values.put(databaseHelper.COLUMN_DOMAIN_ID, request.getDomainId());
        return values;
    }
}
