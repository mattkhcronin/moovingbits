package cronin.matt.moovingbits.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cronin.matt.moovingbits.Model.Domain;

/**
 * Created by mcronin on 10/5/2015.
 */
public class DomainRepository implements CRUDHelper<Domain> {

    private DatabaseHelper databaseHelper;

    public DomainRepository(Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public long add(Domain item) {
        ContentValues values = getContentValues(item);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        long newId = db.insert(databaseHelper.TABLE_DOMAIN, null, values);
        db.close();
        return newId;
    }

    @Override
    public List<Domain> getAll() {
        String query = "SELECT * FROM " + databaseHelper.TABLE_DOMAIN;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        List<Domain> domains = new ArrayList<>();

        while (cursor.moveToNext()) {
            Domain domain = new Domain(cursor.getLong(0), cursor.getString(1));
            domains.add(domain);
        }
        db.close();
        return domains;
    }

    @Override
    public Domain getItem(long id) {
        String query = "SELECT * FROM " + databaseHelper.TABLE_DOMAIN + " WHERE " + databaseHelper.COLUMN_DOMAIN_ID + " = " + id;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Domain domain = null;

        if (cursor.moveToNext()) {
            domain = new Domain(cursor.getLong(0), cursor.getString(1));
        }
        db.close();
        return domain;
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM " + databaseHelper.TABLE_DOMAIN + " WHERE " + databaseHelper.COLUMN_DOMAIN_ID + " = " + id;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    @Override
    public void update(long id, Domain item) {
        ContentValues values = getContentValues(item);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.update(databaseHelper.TABLE_DOMAIN, values, databaseHelper.COLUMN_DOMAIN_ID + "=" + id, null);
        db.close();
    }

    private ContentValues getContentValues(Domain domain){
        ContentValues values = new ContentValues();
        values.put(databaseHelper.COLUMN_DOMAIN_URL, domain.getURL());
        return values;
    }
}
