package cronin.matt.moovingbits.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cronin.matt.moovingbits.Model.Header;

/**
 * Created by mcronin on 10/5/2015.
 */
public class HeaderRepository implements CRUDHelper<Header> {

    private DatabaseHelper databaseHelper;

    public HeaderRepository(Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public long add(Header item) {
        ContentValues values = getContentValues(item);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        long newId = db.insert(databaseHelper.TABLE_HEADER, null, values);
        db.close();
        return newId;
    }

    @Override
    public List<Header> getAll() {
        return getAllWhere("");
    }

    private List<Header> getAllWhere(String where){
        String query = "SELECT * FROM " + databaseHelper.TABLE_HEADER + " " + where;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        List<Header> headers = new ArrayList<>();

        while (cursor.moveToNext()) {
            Header header = new Header(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3));
            headers.add(header);
        }
        db.close();
        return headers;
    }

    @Override
    public Header getItem(long id) {
        List<Header> headers = getAllWhere("WHERE " + databaseHelper.COLUMN_HEADER_ID + " = " + id);
        Header header = headers.get(0);
        return header;
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM " + databaseHelper.TABLE_HEADER + " WHERE " + databaseHelper.COLUMN_HEADER_ID + " = " + id;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public void deleteByRequest(long requestId){
        String sql = "DELETE FROM " + databaseHelper.TABLE_HEADER + " WHERE " + databaseHelper.COLUMN_REQUEST_ID + " = " + requestId;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    @Override
    public void update(long id, Header item) {
        ContentValues values = getContentValues(item);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.update(databaseHelper.TABLE_HEADER, values, databaseHelper.COLUMN_HEADER_ID + "=" + id, null);
        db.close();
    }

    public List<Header> getAllByRequest(long requestId){
        return getAllWhere("WHERE " + databaseHelper.COLUMN_REQUEST_ID + " = " + requestId);
    }

    private ContentValues getContentValues(Header item){
        ContentValues values = new ContentValues();
        values.put(databaseHelper.COLUMN_HEADER_KEY, item.getKey());
        values.put(databaseHelper.COLUMN_HEADER_VALUE, item.getValue());
        values.put(databaseHelper.COLUMN_REQUEST_ID, item.getRequestId());
        return values;
    }
}
