package com.vivekapps.avtodock_new;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vivekapps.utils.SaveSharedPreference;

public class vehicleTypeSelectionActivity extends AppCompatActivity {

    ListView vehicleTypeLV;
    int vehicles[] = {R.drawable.carvehicletype,R.drawable.bikevehicletype};

    DrawerLayout dLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_type_selection);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });

        setNavigationDrawer();

        vehicleTypeLV = (ListView) findViewById(R.id.vehicleSelectionListView);
        vehicleTypeSelectionAdapter vehicleAdapter = new vehicleTypeSelectionAdapter(getApplicationContext(),vehicles);
        vehicleTypeLV.setAdapter(vehicleAdapter);

        vehicleTypeLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {
                if (postion == 0) {
                    Intent carTypePageIntent = new Intent(view.getContext(), carTypeSelectionActivity.class);
                    startActivityForResult(carTypePageIntent, 0);
                }

                if (postion == 1) {
                    Toast.makeText(getApplicationContext(),"Bike details yet to be implemented",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId(); // get selected menu item's id
                if (itemId == R.id.servicesMenu) {
                    Intent ourServicesIntent = new Intent(getApplicationContext(),ourServicesActivity.class);
                    startActivity(ourServicesIntent);
                    dLayout.closeDrawers();
                    return true;
                } else if (itemId == R.id.logoutMenu) {
                    logout();
                }
                // display a toast message with menu item's title
                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void logout() {
        SaveSharedPreference.setLoggedIn(getApplicationContext(), false);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
