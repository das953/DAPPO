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
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

import Controller.dappo.*;
import Model.dappo.AppLocales;


/**
 * Base activity of Das Audio Player With Photo Option :)
 */
public class HomeActivity extends AppCompatActivity {

    //Permissions
    public final String[] EXTERNAL_PERMS =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA};
    public final int EXTERNAL_REQUEST = 138;

    //Controllers
    MainController mainController;
    LanguageController languageController;

    ArrayAdapter<String> adapter;

    TextView hello;
    int switcher;

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        requestForPermissions();


        mainController = new MainController();
        languageController = new LanguageController(getResources());

        listView = findViewById(R.id.lvLang);
        hello = findViewById(R.id.tvHello);
        switcher = 0;

        Initialize();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item =  view.findViewById(R.id.itemText);
                String text = item.getText().toString();
                Locale locale = AppLocales.valueOf(text).getLocale();
                languageController.setLanguage(locale);
                Initialize();
            }
        });


    }

    /**
     * Set content to all elements in activity
     */
    private void Initialize(){

        if(adapter == null){
            adapter = new ArrayAdapter<String>(this, R.layout.lang_list_item , R.id.itemText,
                    languageController.getLanguageArray());

            listView.setAdapter(adapter);

            Locale locale = AppLocales.valueOf(mainController.getSettings().getLanguage()).getLocale();
            languageController.setLanguage(locale);
        }

        hello.setText(R.string.Hello);



    }

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




}
