package com.example.stopwatchappv3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

/**
 * Version 3.0 der App
 * More Info in last version
 * TODO: schrift grösse in em (oder rem für responsive)
 * TODO: delete button zu remove - (minus) button ändern
 * TODO: edit neben name, anderen drei in dieser reihenfolge vertikal rechts von lap times
 * TODO: lap duration und time point in cour.tff, andere schriften grösser
 * TODO: kleine #id neben stopwatch timeTv
 */
public class MainActivity extends AppCompatActivity {

    // For the navigation
    NavController navController;
    DrawerLayout drawerLayout;
    AppBarConfiguration appBarConfiguration;

    /**
     * Berechnet aus der Zeit (int) die Zeit in min:sec.hs also 00:00.00,
     * maximum ist 59:59.99 geht als nicht über eine Stunde hinaus
     *
     * @param time als int
     * @return Zeit als String formatiert 00:00.00
     */
    public static String getTimeStringFromInt(long time) {
        time /= 100; // um von mili auf zehntel sekunde
        long cs = time % 10; // Berechnet Zehntelsekunden
        long sec = time / 10 % 3600 % 60; // Berechnet Sekunden (wird immer abgerundet)
        long min = time / 10 % 3600 / 60; // Berechnet Minuten ( " )
        return String.format(Locale.getDefault(), "%02d:%02d.%d", min, sec, cs); // Gibt das Resultat formatiert zurück
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For the navigation (with drawer)
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        drawerLayout = findViewById(R.id.drawer_layout);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); // that the drawer icon is shown in the action bar and opens
        NavigationUI.setupWithNavController((NavigationView) findViewById(R.id.nav_view), navController); // that the drawer works on clicks
    }

    // For the navigation (to get back)
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}