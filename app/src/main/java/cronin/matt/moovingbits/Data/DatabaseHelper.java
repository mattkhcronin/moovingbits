package cronin.matt.moovingbits.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mattcronin on 10/2/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "moovingbits.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;


    //request table
    public static final String TABLE_REQUEST = "request";
    public static final String COLUMN_REQUEST_ID = "requestId";
    public static final String COLUMN_REQUEST_ROUTE = "route";
    public static final String COLUMN_REQUEST_TYPE = "type";

    //group table
    public static final String TABLE_DOMAIN = "domain";
    public static final String COLUMN_DOMAIN_ID = "domainId";
    public static final String COLUMN_DOMAIN_URL = "url";


    public static synchronized DatabaseHelper getInstance(Context context){
        if (instance == null){
            instance = new DatabaseHelper(context.getApplicationContext(), null);
        }
        return instance;
    }

    private DatabaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createGroupTable = "CREATE TABLE "
                + TABLE_DOMAIN + "(" + COLUMN_DOMAIN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_DOMAIN_URL + " TEXT)";

        String createRequestTable = "CREATE TABLE "
                + TABLE_REQUEST + "(" + COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_REQUEST_TYPE + " TEXT,"
                + COLUMN_REQUEST_ROUTE + " TEXT,"
                + COLUMN_DOMAIN_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_DOMAIN_ID + ") REFERENCES " + TABLE_DOMAIN + "(" + COLUMN_DOMAIN_ID + "))";

        db.execSQL(createGroupTable);
        db.execSQL(createRequestTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOMAIN);
        onCreate(db);
    }
}
