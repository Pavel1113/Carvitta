package pavelbich.fit.bstu.carvitta;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "carvitta.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных


    static final String TABLE_USERS = "users"; // название таблицы в бд
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ISAUTH = "isAuth";

    static final String TABLE_CARS = "cars"; // название таблицы в бд
    public static final String COLUMN_CARID = "idc";
    public static final String COLUMN_USID = "usid";
    public static final String COLUMN_MARKA = "marka";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MEHAUTO = "mehauto";
    public static final String COLUMN_VOLUME = "volume";
    public static final String COLUMN_TYPEFUEL = "typefuel";
    public static final String COLUMN_SHAPE = "shape";
    public static final String COLUMN_DISTANCE = "distance";
    public static final String COLUMN_IMG = "image";
    public static final String COLUMN_IMG2 = "image2";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_ISSELL = "isSell";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_LOGIN
                + " TEXT, " + COLUMN_NAME + " TEXT, "+COLUMN_PASSWORD+" TEXT,"+COLUMN_ISAUTH+" INTEGER);");
        db.execSQL("CREATE TABLE " + TABLE_CARS + " (" + COLUMN_CARID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USID
                + " INTEGER, " + COLUMN_MARKA + " TEXT, "+COLUMN_MODEL+" TEXT,"+COLUMN_PRICE+" TEXT,"+COLUMN_YEAR+" TEXT,"+COLUMN_MEHAUTO+" TEXT,"+COLUMN_VOLUME+" TEXT,"+COLUMN_TYPEFUEL+" TEXT,"+COLUMN_SHAPE+" TEXT,"+COLUMN_DISTANCE+" TEXT,"+COLUMN_IMG+" TEXT,"+COLUMN_IMG2+" TEXT,"+COLUMN_PHONE+" TEXT,"+COLUMN_ISSELL+" BOOLEAN);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CARS);
        onCreate(db);
    }

}
