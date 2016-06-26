package com.example.epos.bettin16;

/**
 * Created by DefaultUser on 25.06.2016.
 */
import android.app.Application;

import com.parse.Parse;

public class BettinApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "xJQaSn6ajsrGUPePno8TQo2E260J7VkqXRafLuuE", "ugkkL7Qq30qumwVOm0Mujr5PwMBLS53Pqkix4zXE");
    }
}