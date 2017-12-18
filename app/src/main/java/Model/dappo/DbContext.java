package Model.dappo;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by das953 on 18-Dec-17.
 */

public class DbContext extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "dappo.db";
    private static final int SCHEMA_VERSION = 1;

    public SQLiteDatabase db;

    private final Context context;

    public DbContext(Context context){
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create table lists
        db.execSQL("create table lists(" +
                "id integer primary key autoincrement," +
                "name varchar not null unique)");

        //create table soundtracks
        db.execSQL("create table soundtracks(" +
                "id integer primary key autoincrement," +
                "name varchar not null," +
                "bitrate varchar," +
                "author varchar," +
                "tittle varchar," +
                "artist varchar," +
                "album varchar," +
                "image blob," +
                "date varchar)");

        //create table soundtracks_in_lists
        db.execSQL("create table soundtracks_in_lists(" +
                "listID integer not null," +
                "soundtrackID integer not null)");



    }

    /*
        private String name;

    private String bitrate;
    private String duration;
    private String author;
    private String tittle;
    private String artist;
    private String album;
    private Bitmap image;
    private String date;
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
