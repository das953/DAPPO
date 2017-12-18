package Controller.dappo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Model.dappo.Soundtrack;

/**
 * Created by das953 on 18-Dec-17.
 */

public final class DataController {


    private static Map<String , ArrayList<Soundtrack>> playLists;
    private static ArrayList<String> fileNames;
    private static ArrayList<String> playsistsNames;
    private static ArrayList<Soundtrack> defaultPlayList;
    private static  MediaMetadataRetriever retriever;

    public static List<Soundtrack> getPlayList(String listName){

        return playLists.get(listName);

    }

    public static void Initialize(){

        retriever = new MediaMetadataRetriever();

        setDefaultPlayList();






    }

    private static void setDefaultPlayList(){
        //TODO add if db exist - read default from it
        //if db exist - read default play list from if
        if(false){

        }
        //if db is not exist or has`t info about play lists children
        else {

            for (File file:
                    Environment.getExternalStorageDirectory().listFiles()) {
                getFiles(Environment.getExternalStorageDirectory(), file.getName());
            }

            playLists.put("default", new ArrayList<>());
            defaultPlayList = playLists.get("default");

            for (String name:
                    fileNames) {

                addSoundtrackToPlayList(name, defaultPlayList);

            }
        }
    }


    private static void setPlayLists(){

        for (String playList:
             playsistsNames) {

            if(!playList.equals("default")){
                playLists.put(playList, new ArrayList<>());

                //TODO add soundtracks to play list from default list
            }

        }

    }

    public static void addSoundtrackToPlayList(String name, List<Soundtrack> playList){

        Soundtrack soundtrack = new Soundtrack(name);

        retriever.setDataSource(soundtrack.getName());

        soundtrack.setArtist(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        soundtrack.setAlbum(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
        soundtrack.setAuthor(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR));
        soundtrack.setBitrate(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
        soundtrack.setDuration(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        soundtrack.setTittle(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        soundtrack.setDate(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE));

        byte[] bytes = retriever.getEmbeddedPicture();

        if(bytes != null){
            InputStream inputStream= new ByteArrayInputStream(bytes);
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            soundtrack.setImage(image);
        }
        else {

        }

        playList.add(soundtrack);
    }

    private static void setPlaysistsNames(){}

    private static void getFiles(File dir, String name){

        if(name.endsWith(".mp3") || name.endsWith(".MP3")){
            fileNames.add(dir.getAbsolutePath()+"/"+name);
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
}
