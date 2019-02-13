package cat.uab.idt.rightsapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LocaleUtils {

    public static void setLocale(Context _context, String language) {
        Resources res = _context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration configuration = res.getConfiguration();
        configuration.locale = new Locale(language);
        res.updateConfiguration(configuration, dm);
    }
}
