
package com.booktunisia.main;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.panorama.PanoramaClient;
import com.google.android.gms.panorama.PanoramaClient.OnPanoramaInfoLoadedListener;

/**
 * Displays examples of integrating with the panorama viewer API.
 */
public class Panorama extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener, OnPanoramaInfoLoadedListener {

    public static final String TAG = Panorama.class.getSimpleName();

    private PanoramaClient mClient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient = new PanoramaClient(this, this, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    public void onConnected() {
       Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pano1);

        mClient.loadPanoramaInfo(this, uri);
    }

    @Override
    public void onPanoramaInfoLoaded(ConnectionResult result, Intent viewerIntent) {
        if (result.isSuccess()) {
            Log.i(TAG, "found viewerIntent: " + viewerIntent);
            if (viewerIntent != null) {
                startActivity(viewerIntent);
            }
        } else {
            Log.e(TAG, "error: " + result);
        }
    }

    @Override
    public void onDisconnected() {
        // Do nothing
    }

    @Override
    public void onConnectionFailed(ConnectionResult status) {
        Log.e(TAG, "connection failed: " + status);
        // TODO fill in
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }
}
