package com.powerpoint45.dtube;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by michael on 5/11/17.
 */

public class DtubeAPI {

    static final int CAT_SUBSCRIBED = 0;
    static final int CAT_HOT = 1;
    static final int CAT_TRENDING = 2;
    static final int CAT_NEW = 3;
    static final int CAT_HISTORY = 4;


    //public static String PROFILE_IMG_URL = "https://img.busy.org/@";//https://img.busy.org/@lukewearechange//too slow, useing steemitimages
    static String PROFILE_IMAGE_SMALL_URL = "https://steemitimages.com/u/username/avatar/small";//replace username with actual username
    static String PROFILE_IMAGE_MEDIUM_URL = "https://steemitimages.com/u/username/avatar/medium";//replace username with actual username
    public static String PROFILE_IMAGE_LARGE_URL = "https://steemitimages.com/u/username/avatar/large";//replace username with actual username
    static String CONTENT_IMAGE_URL = "https://steemitimages.com/0x0/https://ipfs.io/ipfs/"; // hash at end


    static String getUserPrivateKey(Context c){
        Encryption encryption = new Encryption(c);
        return encryption.decryptString("privateKeyWif");
    }

    static String getAccountName(Context c){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPref.getString("username",null);
    }

    static void saveUserCredentials(String username, String privateKey, Context c){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c);
        sharedPref.edit().putString("username", username).apply();

        Encryption encryption = new Encryption(c);
        encryption.encryptString("privateKeyWif", privateKey);
    }
    
}
