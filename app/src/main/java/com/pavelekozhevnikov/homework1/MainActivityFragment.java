package com.pavelekozhevnikov.homework1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pavelekozhevnikov.homework1.Model.Locator;
import com.pavelekozhevnikov.homework1.Model.OnGotLocationEventListener;
import com.pavelekozhevnikov.homework1.Model.WeatherUpdaterThread;


public class MainActivityFragment extends BaseThemeActivity {
    MaterialButton matButton;
    FloatingActionButton fab;
    Locator locator;
    private final Handler handler = new Handler();

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_toolbar);

        initViews();

        getPerissions();

        initLocator();
    }

    private void initLocator() {
        locator = new Locator(this);
        locator.setOnGotLocationEventListener(new OnGotLocationEventListener() {
            @Override
            public void onGotLocation() {
                matButton.setText(R.string.locationDetected);
            }
        });
    }

    private void getPerissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_ACCESS_COARSE_LOCATION) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.needLocation, Toast.LENGTH_SHORT).show();
            }else{
                locator.stop();
                locator.start();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        locator.start();
    }

    @Override
    protected void onStop() {
        locator.stop();
        super.onStop();
    }


    private void initViews() {
        matButton = findViewById(R.id.materialBtn);
        matButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WeatherUpdaterThread(handler,MainActivityFragment.this,locator.lat,locator.lon).start();
            }
        });
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(App.getInstance(),getString(R.string.theme), Toast.LENGTH_SHORT).show();
                toggleTheme();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_developer) {
            Intent intent = new Intent(this,TestActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}