package Model.dappo;

import java.util.Locale;

/**
 * Created by das953 on 14-Dec-17.
 */

public enum AppLocales {
    UA(AdditionalLocales.UKRAINIAN),
    RU(AdditionalLocales.RUSSIAN),
    US(Locale.US);


    private Locale locale;

    AppLocales(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
