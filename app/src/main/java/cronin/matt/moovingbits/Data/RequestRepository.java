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
    public void add(Request item) {
        ContentValues values = new ContentValues();
        //values.put(databaseHelper.COLUMN_REQUEST_ID, item.getId());
        values.put(databaseHelper.COLUMN_REQUEST_TYPE, item.getType());
        values.put(databaseHelper.COLUMN_REQUEST_ROUTE, item.getRoute());

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        db.insert(databaseHelper.TABLE_REQUEST, null, values);
        db.close();
    }

    @Override
    public List<Request> getAll(){
        String query = "SELECT * FROM " + databaseHelper.TABLE_REQUEST;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        List<Request> requests = new ArrayList<>();

        while (cursor.moveToNext()) {
            Request request = new Request(cursor.getInt(0), null, cursor.getString(1), 0);
            requests.add(request);
        }
        db.close();
        return requests;
    }

    @Override
    public Request getItem(int id) {
        return null;
    }

    @Override
    public void delete(Request item) {

    }

    @Override
    public void update(int id, Request item) {

    }

    public void deleteAll(){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + databaseHelper.TABLE_REQUEST);
        db.close();
    }
}
