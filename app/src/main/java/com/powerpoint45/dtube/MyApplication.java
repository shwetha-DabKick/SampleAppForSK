package com.powerpoint45.dtube;

import com.dabkick.videosdk.publicsettings.DabKick;
import com.dabkick.videosdk.publicsettings.SdkApp;

/**
 * Created by iFocus on 28-02-2018.
 */

public class MyApplication extends SdkApp {

    @Override
    public void onCreate() {
        super.onCreate();
        DabKick.setVideoProvider(Utilities.createDabKickVideoProvider(null));
    }
}
