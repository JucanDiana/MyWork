package constant;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedVariable {

    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "MyConstants";

    public SharedVariable() {

    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void setProfileId(Context context, int profileId) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt("profileId", profileId);
        editor.commit();
    }

    public static int getProfileId(Context context) {
        int profileId = -1;
        try {
            profileId = getPreferences(context).getInt("profileId", -1);
        } catch (Throwable e) {
        }
        return profileId;
    }

}


