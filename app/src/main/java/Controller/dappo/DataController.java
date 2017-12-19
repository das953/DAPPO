package Controller.dappo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Model.dappo.DbContext;
import Model.dappo.PlayList;
import Model.dappo.Soundtrack;

/**
 * Created by das953 on 18-Dec-17.
 */

public final class DataController {


    private static Map<String , PlayList<Soundtrack>> playLists;
    private static ArrayList<String> fileNames;
    private static ArrayList<String> playsistsNames;
    private static PlayList<Soundtrack> defaultPlayList;
    private static MediaMetadataRetriever retriever;
    private static DbContext dbContext;
    private static Context context;

    public static PlayList<Soundtrack> getPlayList(String listName){

        return playLists.get(listName);

    }

    public static void Initialize(Context context){

        fileNames = new ArrayList<>();
        playLists = new HashMap<>();
        retriever = new MediaMetadataRetriever();
        DataController.context = context;

        dbContext = new DbContext(context);
        boolean existingDB = dbContext.OpenOrCreateDB();

        setFileNames();
        initListNames();
        setDefaultPlayList(existingDB);


        count+=0;
        int a =5;
        if(defaultPlayList.equals(fileNames))
            a =5;


    }

    private static void initListNames(){
        try {
            playsistsNames = dbContext.getPlaysistsNames();
        }
        catch (Exception e){
            Log.d(e.getCause().toString(), e.getMessage());
        }
    }

    private static void setDefaultPlayList(boolean bdExist){
        //TODO add if db exist - read default from it
        //if db exist - read default play list from if
        if(bdExist){
            try {
                defaultPlayList = dbContext.getPlaylist("default");
                playLists.put("default", defaultPlayList);
            }
            catch (Exception e){
                Log.d(e.getCause().toString(), e.getMessage());
            }
        }
        //if db is not exist
        else {

            defaultPlayList = new PlayList<>("default");
            //playLists.get("default");
            try{dbContext.insertPlayList(defaultPlayList.getListName());}
            catch (SQLiteException e){}
            playLists.put("default", defaultPlayList);



        }


        addAllFiles();
        int a = dbContext.SongsDBCount();
        a++;
    }


   /* private static void setPlayLists(){

        for (String playList:
             playsistsNames) {

            if(!playList.equals("default")){
                playLists.put(playList, new PlayList<>(playList));

                //TODO add soundtracks to play list from default list
            }

        }

    }*/

    public static Soundtrack addSoundtrackToPlayList(String name, List<Soundtrack> playList){

        Soundtrack soundtrack = new Soundtrack(name);

        retriever.setDataSource(soundtrack.getName());

        soundtrack.setArtist(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        soundtrack.setAlbum(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
        soundtrack.setAuthor(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR));
        soundtrack.setBitrate(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
        soundtrack.setDuration(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        soundtrack.setTittle(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        soundtrack.setDate(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE));

        byte[] bytes = null;
        try {
            bytes = retriever.getEmbeddedPicture();
        }
        catch (Exception e){
            Log.d(e.getLocalizedMessage(), e.getMessage());
        }
        if(bytes != null){
            InputStream inputStream= new ByteArrayInputStream(bytes);
            Drawable image = Drawable.createFromStream(inputStream, null);
            soundtrack.setImage(image);
        }

        playList.add(soundtrack);
        return soundtrack;
    }

    private static void setPlaysistsNames(){}

    private static int count = 0;
    private static void getFiles(File dir, String name){

        /*count++;
        if(fileNames.contains(name))
            return;*/

        if(name.endsWith(".mp3") || name.endsWith(".MP3")){
            String filename = dir.getAbsolutePath()+"/"+name;

            //if(file size > 750KB)
            if(((new File(filename)).length() / 1024) > 953)
                fileNames.add(filename);
            return;
        }
        File file = new File(dir.getAbsolutePath()+"/"+name);


        if(file.isDirectory()){
            for (File subfile:
                    file.listFiles()) {
                getFiles(file, subfile.getName());
            }
        }
    }

    private static void setFileNames(){
        for (File file:
                Environment.getExternalStorageDirectory().listFiles()) {
            getFiles(Environment.getExternalStorageDirectory(), file.getName());
        }
    }

    private static void addAllFiles(){
        for (String name:
                fileNames) {

            boolean cont = true;
            for (Soundtrack soundtrack1 : defaultPlayList) {
                if (soundtrack1.getName().equals(name)){
                    cont = !cont;
                }
            }
            if(cont) {
                String tmp = defaultPlayList.getListName();
                Soundtrack soundtrack = addSoundtrackToPlayList(name, defaultPlayList);
                dbContext.insertSoundtrack(soundtrack, defaultPlayList.getListName());
            }
        }
    }


    public static Map<String, PlayList<Soundtrack>> getPlayLists() {
        if(playLists.size() == 0 || defaultPlayList.size() == 0){
            setDefaultPlayList(dbContext.OpenOrCreateDB());
        }



        return playLists;
    }
}
