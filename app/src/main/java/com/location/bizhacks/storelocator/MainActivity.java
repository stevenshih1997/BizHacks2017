package com.location.bizhacks.storelocator;

import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import android.support.annotation.NonNull;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;

public class MainActivity extends AppCompatActivity {

    private static final String ANONYMOUS = "Anonymous";
    public static final int RC_SIGN_IN = 1;
    private static String TAG = "MainActivity";

    private String mUsername;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    TextView mMainText;


    private final int CODE_PERMISSIONS = 225;//...
    private IALocationManager mIALocationManager; //Register and unregister for periodic updates of the user's current location.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (authStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(authStateListener);
        }

        String[] neededPermissions = {
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this, neededPermissions, CODE_PERMISSIONS );

        mIALocationManager = IALocationManager.create(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mMainText = (TextView) findViewById(R.id.main_text);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Toast.makeText(MainActivity.this, "You're signed in. Welcome!", Toast.LENGTH_SHORT).show();
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    onSignedInCleanup();
                    startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .build(),
                            RC_SIGN_IN
                            );
                }
            }
        };
    }

    public void onSignedInInitialize(String userName) {
        mUsername = userName;
        mMainText.setText(mUsername);
    }

    public void onSignedInCleanup() {
        mUsername = ANONYMOUS;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Handle if any of the permissions are denied, in grantResults
    }



    private IALocationListener mIALocationListener = new IALocationListener() {

        // Called when the location has changed.
        @Override
        public void onLocationChanged(IALocation location) {
            
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }
    };

}
