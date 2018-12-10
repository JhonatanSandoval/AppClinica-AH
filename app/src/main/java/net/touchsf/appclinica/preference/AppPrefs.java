package net.touchsf.appclinica.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefs {

    private static final String PREFERENCE_NAME = "AppCliente-AH";

    private static final String KEY_LOGGED = "logged";
    private static final String KEY_USER_ID = "userId";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AppPrefs(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        this.editor = this.sharedPreferences.edit();
    }

    public boolean isLogged() {
        return this.sharedPreferences.getBoolean(KEY_LOGGED, false);
    }

    public void setLogged(boolean value) {
        this.editor.putBoolean(KEY_LOGGED, value);
        this.editor.apply();
    }

    public int getUsetId() {
        return this.sharedPreferences.getInt(KEY_USER_ID, 0);
    }

    public void setUserId(int userId) {
        this.editor.putInt(KEY_USER_ID, userId);
        this.editor.apply();
    }

}
