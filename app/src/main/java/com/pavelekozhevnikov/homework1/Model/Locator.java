package com.pavelekozhevnikov.homework1.Model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.pavelekozhevnikov.homework1.R;

public class Locator implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private GoogleApiClient googleApiClient;
    private Context context;
    public double lat;
    public double lon;
    private OnGotLocationEventListener mOnGotLocationEventListener;


    public Locator(Context context) {
        this.context = context;
        googleApiClient = new GoogleApiClient.Builder(this.context, this, this).addApi(LocationServices.API).build();
    }

    public void setOnGotLocationEventListener(OnGotLocationEventListener eventListener)
    {
        mOnGotLocationEventListener = eventListener;
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            lat = lastLocation.getLatitude();
            lon = lastLocation.getLongitude();

            if(mOnGotLocationEventListener != null)
            {
                mOnGotLocationEventListener.onGotLocation();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(Locator.class.getSimpleName(), context.getString(R.string.errGoogleConnectionSusspended));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(Locator.class.getSimpleName(), context.getString(R.string.errGoogleConnect));
    }

    public void start() {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    public void stop() {
        googleApiClient.disconnect();
    }
}
