package com.location.bizhacks.storelocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;

public class MapActivity extends AppCompatActivity {

    private IALocationManager mIALocationManager; //Register and unregister for periodic updates of the user's current location.
    private IALocationListener mIALocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        mIALocationManager = IALocationManager.create(this);
        mIALocationListener = new IALocationListener() {

            // Called when the location has changed.
            @Override
            public void onLocationChanged(IALocation location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }
        };
    }

}
