package view.dappo;/*
DAPPO - DasAudioPlayerWithPhotoOption
DBDE  - DasBestDevelopmentEver
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Controller.dappo.*;
import Model.dappo.AppLocales;
import Model.dappo.PlayList;
import Model.dappo.PlayerAction;
import Model.dappo.Soundtrack;
import view.dappo.SoundTracks.SoundTracksContent;


/**
 * Base activity of Das Audio Player With Photo Option :)
 */
public class HomeActivity extends AppCompatActivity implements SoundtracksFragment.OnListFragmentInteractionListener {

    //Permissions
    public final String[] EXTERNAL_PERMS =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA};
    public final int EXTERNAL_REQUEST = 138;

    //Controllers
    MainController mainController;
    LanguageController languageController;

    //Language adapter
    ArrayAdapter<String> adapter;

    //Current Soundtrack
    SoundTracksContent.SoundtrackItem currentItem;

    //Navigation buttons
    Button btnPrev;
    Button btnPlay;
    Button btnNext;

    //Current state
    PlayerAction playerAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestForPermissions();


        mainController = new MainController(this);
        languageController = new LanguageController(getResources());


        Initialize();


        setContentView(R.layout.activity_home);
    }

    /**
     * Set content to all elements in activity
     */
    private void Initialize(){

        playerAction = PlayerAction.PAUSE;

        btnPrev = findViewById(R.id.btnPrev);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);

        btnPlay.setOnClickListener(v -> {PlayPause(PlayerAction.PLAY);});

        if(adapter == null){
            //set ListView through ArrayAdapter<String>
          /*  adapter = new ArrayAdapter<String>(this, R.layout.lang_list_item , R.id.itemText,
                    languageController.getLanguageArray());

            listView.setAdapter(adapter);*/

            Locale locale = AppLocales.valueOf(mainController.getSettings().getLanguage()).getLocale();
            languageController.setLanguage(locale);
        }

        //hello.setText(R.string.Hello);

    }


    /**
     * For permissions
     */
    public boolean canAccessExternalSd() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    public boolean canAccessCamera() {
        return (hasPermission(Manifest.permission.CAMERA));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));
    }

    public boolean requestForPermissions() {
        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            if (!canAccessExternalSd() || !canAccessCamera()) {
                isPermissionOn = false;
                ActivityCompat.requestPermissions(this, EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
        }
        return isPermissionOn;
    }

    @Override
    public void onListFragmentInteraction(SoundTracksContent.SoundtrackItem item) {
        currentItem = item;
    }

    private void PlayPause(PlayerAction action){

        switch (action){

        }

    }
}



