package Controller.dappo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.os.Environment;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import Model.dappo.AppSettings;
import Model.dappo.Soundtrack;

/**
 * Created by das953 on 14-Dec-17.
 */

public class MainController {

    private AppSettings appSettings;
    private ArrayList<String> fileNames;
    private ArrayList<String> playsistsNames;
    private Map<String , ArrayList<Soundtrack>> playLists;


    public MainController() {

        Initialize();
        //TODO get lang string from DB if it exist


    }

    private void Initialize(){
        appSettings = new AppSettings("UA");
        fileNames = new ArrayList<>();
        playLists = new HashMap<>();
        setFiles();

    }


    public AppSettings getSettings() {
        return appSettings;
    }

  private void setFiles(){




    }




}
