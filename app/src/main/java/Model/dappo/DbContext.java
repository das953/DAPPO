package Model.dappo;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by das953 on 18-Dec-17.
 */

public class DbContext extends SQLiteOpenHelper{

    //TODO

    //TODO delete blob image from db adn get it from file each time :(
    private static final String DATABASE_NAME = "dappo.db";
    private static final String DATABASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/dappo/";
    private static final int    SCHEMA_VERSION = 1;

    SQLiteDatabase db;

    private final Context context;

    public DbContext(Context context){
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public int SongsDBCount(){
        Cursor cursor = db.rawQuery("select * from soundtracks", null);
        int a=0; cursor.moveToNext();
        while (cursor.moveToNext()){
            a++;
        }
        return a;
    }

    public void insertSoundtrack(Soundtrack soundtrack, String listName) {

        SQLiteStatement statement = db.compileStatement("insert into " +
                "soundtracks(name, bitrate, duration, author, tittle, artist, " +
                "album, image, date) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

        statement.bindString(1, soundtrack.getName() != null ? soundtrack.getName() : "");
        statement.bindString(2, soundtrack.getBitrate() != null ? soundtrack.getBitrate() : "");
        statement.bindString(3, soundtrack.getDuration() != null ? soundtrack.getDuration() : "");
        statement.bindString(4, soundtrack.getAuthor() != null ? soundtrack.getAuthor() : "");
        statement.bindString(5, soundtrack.getTittle() != null ? soundtrack.getTittle() : "");
        statement.bindString(6, soundtrack.getArtist() != null ? soundtrack.getArtist() : "");
        statement.bindString(7, soundtrack.getAlbum() != null ? soundtrack.getAlbum() : "");
        byte[] imageBytes = getImageBytes(soundtrack.getImage());
        if (imageBytes == null)
            statement.bindLong(8, 0);
        else
            statement.bindLong(8, 1);

             /*statement.bindBlob(8, imageBytes);*/

        statement.bindString(9, soundtrack.getDate() != null ? soundtrack.getDate() : "");

        statement.execute();

        Cursor listID = db.rawQuery("select id from lists where name=\"" + listName + "\" ",
                null);


        Cursor soundtrackID = db.rawQuery("select id from soundtracks where name=\"" +
                soundtrack.getName() + "\" ", null);

        try {
            int listIDColumnIndex = listID.getColumnIndex("id");
            int soundtrackIDColumnIndex = soundtrackID.getColumnIndex("id");
            listID.moveToNext();
            soundtrackID.moveToNext();
            db.execSQL("insert into soundtracks_in_lists(listID, soundtrackID)" +
                    "values(" +
                    listID.getInt(listIDColumnIndex) + ", " +
                    soundtrackID.getInt(soundtrackIDColumnIndex) + ")");

            listID.close();
            soundtrackID.close();
        } catch (SQLiteException e) {
            Log.d(e.getCause().toString(), e.getMessage());
        }

    }

    public void insertPlayList(String playListName){
        db.execSQL("insert into lists (name)" +
                "values ('" +
                 playListName +"')");
    }

    private byte[] getImageBytes(Bitmap image){
        if(image != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 0, stream);
            return stream.toByteArray();
        }
        return null;
    }


    public boolean OpenOrCreateDB(){
        if(!isDBExist()){
            File file = new File(DATABASE_PATH);
            if(!file.exists() || ! file.isDirectory()){
                file.mkdirs();
            }
            db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH + DATABASE_NAME,null);
            db.setVersion(SCHEMA_VERSION);
            createDB(db);
            return false;
        }
        db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH + DATABASE_NAME,null);
        db.setVersion(SCHEMA_VERSION);
        return true;
    }

    private boolean isDBExist() {

        File file = new File(DATABASE_PATH + DATABASE_NAME);
        return file.exists();
    }

    private void createDB(SQLiteDatabase db){

        try {
            //create table lists
            db.execSQL("create table lists(" +
                    "id integer primary key autoincrement," +
                    "name varchar not null unique)");

            //create table soundtracks
            db.execSQL("create table soundtracks(" +
                    "id integer primary key autoincrement," +
                    "name varchar not null," +
                    "bitrate varchar," +
                    "duration varchar," +
                    "author varchar," +
                    "tittle varchar," +
                    "artist varchar," +
                    "album varchar," +
                    "image integer," +
                    "date varchar)");

            //create table soundtracks_in_lists
            db.execSQL("create table soundtracks_in_lists(" +
                    "listID integer not null," +
                    "soundtrackID integer not null)");

            //create table app_settings
            db.execSQL("create table app_settings(" +
                    "language varchar not null primary key," +
                    "current_playlist varchar not null," +
                    "current_song varchar)");

        }
        catch (SQLiteException e){
            Log.d(e.getCause().toString(), e.getMessage());
        }
    }

    public PlayList<Soundtrack> getPlaylist(String listName){

        PlayList<Soundtrack> list = new PlayList<>(listName);


        @SuppressLint("Recycle")
        Cursor listID = db.rawQuery("select id from lists where name='" + listName + "' ",
                null);
        listID.moveToNext();
        int id = listID.getInt(listID.getColumnIndex("id"));
        listID.close();


        String query = "select * from soundtracks inner join soundtracks_in_lists " +
                "on soundtracks.id = soundtracks_in_lists.soundtrackID " +
                "and soundtracks_in_lists.listID = " + id;

        Cursor soundtrackCursor = db.rawQuery(query, null);

        while (soundtrackCursor.moveToNext()){
            Soundtrack soundtrack = new Soundtrack
                    (soundtrackCursor.getString(soundtrackCursor.getColumnIndex("name")));

            soundtrack.setTittle
                    (soundtrackCursor.getString(soundtrackCursor.getColumnIndex("tittle")));
            soundtrack.setDuration
                    (soundtrackCursor.getString(soundtrackCursor.getColumnIndex("duration")));
            soundtrack.setBitrate
                    (soundtrackCursor.getString(soundtrackCursor.getColumnIndex("bitrate")));
            soundtrack.setAuthor
                    (soundtrackCursor.getString(soundtrackCursor.getColumnIndex("author")));
            soundtrack.setAlbum
                    (soundtrackCursor.getString(soundtrackCursor.getColumnIndex("album")));
            soundtrack.setArtist
                    (soundtrackCursor.getString(soundtrackCursor.getColumnIndex("artist")));
            soundtrack.setDate
                    (soundtrackCursor.getString(soundtrackCursor.getColumnIndex("date")));
            soundtrack.setDate
                    (soundtrackCursor.getString(soundtrackCursor.getColumnIndex("date")));

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(soundtrack.getName());

            int haveImage = soundtrackCursor.getInt(soundtrackCursor.getColumnIndex("image"));

            if(haveImage == 1) {

                byte[] bytes = retriever.getEmbeddedPicture();

                InputStream inputStream = new ByteArrayInputStream(bytes);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                soundtrack.setImage(image);

               /* byte[] imageByte =
                        soundtrackCursor.getBlob(soundtrackCursor.getColumnIndex("image"));

                soundtrack.setImage(BitmapFactory.decodeByteArray(imageByte,0, imageByte.length));*/
            }



            list.add(soundtrack);
        }
        soundtrackCursor.close();

        return list;
    }

    public ArrayList<String> getPlaysistsNames(){
        ArrayList<String> names = new ArrayList<>();

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("select * from lists", null);

        while (cursor.moveToNext()){
            names.add(cursor.getString(cursor.getColumnIndex("name")));
        }

        return names;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
