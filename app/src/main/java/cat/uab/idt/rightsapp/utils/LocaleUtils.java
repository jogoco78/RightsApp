package cat.uab.idt.rightsapp.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

import cat.uab.idt.rightsapp.Constants;

public class LocaleUtils {

//    public static void initialize(Context context){
  //      setLocale(context, Constants.LANGUAGE_EN);
    //}

    public static void setLocale(Context _context, String language)
    {
        Resources res = _context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language);
        res.updateConfiguration(conf, dm);
    }
    //public static boolean setLocale(Context context, String language){
      //  return updateResources(context, language);
    //}

    private static boolean updateResources(Context context, String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        context.createConfigurationContext(configuration);
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return true;
    }
}
