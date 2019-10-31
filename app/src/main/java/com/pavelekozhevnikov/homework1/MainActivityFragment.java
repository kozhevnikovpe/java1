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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.pavelekozhevnikov.homework1.Model.Locator;
import com.pavelekozhevnikov.homework1.Model.OnGotLocationEventListener;
import com.pavelekozhevnikov.homework1.Model.WeatherUpdaterThread;


public class MainActivityFragment extends BaseThemeActivity implements NavigationView.OnNavigationItemSelectedListener  {
    MaterialButton matButton;
    FloatingActionButton fab;
    Locator locator;
    private Toolbar toolbar;
    private DrawerLayout drawer;

    private final Handler handler = new Handler();

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_toolbar);
        initViews();
        initSideMenu();
        getPerissions();
        initLocator();
    }

    private void initLocator() {
        locator = new Locator(this);
        locator.setOnGotLocationEventListener(new OnGotLocationEventListener() {
            @Override
            public void onGotLocation() {
                matButton.setVisibility(View.VISIBLE);
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

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initSideMenu() {
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_add) {
            Toast.makeText(this, R.string.add_city, Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.menu_location) {
            new WeatherUpdaterThread(handler,MainActivityFragment.this,locator.lat,locator.lon).start();
            return true;
        }else if (id == R.id.menu_sensor) {
            Intent intent = new Intent(this,SensorActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_help) {
            Toast.makeText(this, R.string.help, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_developer) {
            Toast.makeText(this, R.string.developer, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_feedback) {
            Toast.makeText(this, R.string.feedback, Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}