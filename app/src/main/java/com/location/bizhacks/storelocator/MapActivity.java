package com.location.bizhacks.storelocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;

public class MapActivity extends AppCompatActivity {

    private IALocationManager mIALocationManager; //Register and unregister for periodic updates of the user's current location.
    private IALocationListener mIALocationListener; //
    private static String TAG = "MapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        mIALocationManager = IALocationManager.create(this);
        mIALocationListener = new IALocationListener() {

            // Called when the location has changed.
            @Override
            public void onLocationChanged(IALocation location) {
                Log.d(TAG, "Latitude: " + location.getLatitude());
                Log.d(TAG, "Longitude: " + location.getLongitude());
            }
            // Called when status has changed.
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }
        };
    }


    /**
     * Start making location updates
     */
    @Override
    protected void onResume() {
        super.onResume();
        mIALocationManager.requestLocationUpdates(IALocationRequest.create(), mIALocationListener); //Create new request; where request is delivered (mIALocationListener)
    }

    /**
     * Stop receiving location updates
     */
    @Override
    protected void onPause() {
        super.onPause();
        mIALocationManager.removeLocationUpdates(mIALocationListener);
    }

    /**
     * Stop all location updates; Destroy location manager
     */
    @Override
    protected void onDestroy() {
        mIALocationManager.destroy();
        super.onDestroy();
    }



}
