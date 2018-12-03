package net.touchsf.appclinica;

import android.app.Application;

import com.facebook.stetho.Stetho;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        setUpCalligraphy();
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this);
    }

    private void setUpCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

}
