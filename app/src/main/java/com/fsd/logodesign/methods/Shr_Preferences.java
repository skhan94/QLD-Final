package com.fsd.logodesign.methods;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by hamza on 6/27/2016.
 */
public class Shr_Preferences {

    SharedPreferences preferences;
    int openTimes = -1;

    public int setUpPrefrences(Context context){
        preferences = context.getSharedPreferences(context.getPackageName() , Context.MODE_PRIVATE);
        writePreferences();
        return openTimes;
    }

    public void writePreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        Log.d("OpenTimes" , "Write openTimes : " + openTimes);
        openTimes = getPreviousOpening();

        editor.putInt("Open_Times",++openTimes);
        editor.commit();

        Log.d("OpenTimes" , "After Commiting openTimes : " + openTimes);
    }

    private int getPreviousOpening() {
        openTimes = preferences.getInt("Open_Times" , openTimes); // Return -1 if not open
        Log.d("OpenTimes" , "Get openTimes : " + openTimes);
        if(openTimes == -1)
            return 0;
        return openTimes;
    }


}
