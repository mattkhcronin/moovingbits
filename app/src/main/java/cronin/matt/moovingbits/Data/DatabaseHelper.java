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

    //header table
    public static final String TABLE_HEADER = "header";
    public static final String COLUMN_HEADER_ID = "headerId";
    public static final String COLUMN_HEADER_KEY = "key";
    public static final String COLUMN_HEADER_VALUE = "value";

    //body table
    public static final String TABLE_BODY = "body";
    public static final String COLUMN_BODY_ID = "bodyId";
    public static final String COLUMN_BODY_KEY = "key";
    public static final String COLUMN_BODY_VALUE = "value";
    public static final String COLUMN_BODY_RAW = "rawData";
    public static final String COLUMN_BODY_TYPE = "encodingType";


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

        String createHeaderTable = "CREATE TABLE "
                + TABLE_HEADER + "(" + COLUMN_HEADER_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_HEADER_KEY + " TEXT,"
                + COLUMN_HEADER_VALUE + " TEXT,"
                + COLUMN_REQUEST_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_REQUEST_ID + ") REFERENCES " + TABLE_REQUEST + "(" + COLUMN_REQUEST_ID + "))";

        String createBodyTable = "CREATE TABLE "
                + TABLE_BODY + "(" + COLUMN_BODY_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_BODY_KEY + " TEXT,"
                + COLUMN_BODY_VALUE + " TEXT,"
                + COLUMN_BODY_RAW + " TEXT,"
                + COLUMN_BODY_TYPE + " TEXT,"
                + COLUMN_REQUEST_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_REQUEST_ID + ") REFERENCES " + TABLE_REQUEST + "(" + COLUMN_REQUEST_ID + "))";

        db.execSQL(createGroupTable);
        db.execSQL(createRequestTable);
        db.execSQL(createHeaderTable);
        db.execSQL(createBodyTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BODY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEADER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOMAIN);
        onCreate(db);
    }
}
