package Controller.dappo;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Stream;

import Model.dappo.AppLocales;

/**
 * Created by das953 on 14-Dec-17.
 */

public class LanguageController {

    private Resources res;
    private final ArrayList<String> languages;
    private String[] languageNames;
    
    public LanguageController(Resources res) {
        this.res = res;
        languageNames = names();
        languages = new ArrayList<> (Arrays.asList( languageNames));
    }

    public void setLanguage(Locale locale){
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public String[] getLanguageNames() {
        return languageNames;
    }

    public ArrayList<String> getLanguageArray(){
        return languages;
    }

    private static String[] names() {
        return Arrays
                .toString(AppLocales.values())
                .replaceAll("^.|.$", "")
                .split(", ");
    }
}
