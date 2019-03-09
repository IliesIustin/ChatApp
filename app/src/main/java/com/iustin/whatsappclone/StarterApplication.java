package com.iustin.whatsappclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

/**
 * @author Iustin
 * @date 2/24/2019
 */
public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //password 36gNkcdCHLbi
        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("48d39337261e68fbcd587f6b5e70f1f766cc4a08")
                .clientKey("d24ea1aa1e2ee2e1058717e0d4f6e5aa3254b941")
                .server("http://3.8.232.80:80/parse/")
                .build()
        );


        //ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
