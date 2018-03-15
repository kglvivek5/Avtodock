package com.vivekapps.avtodock_new;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class vehicleTypeSelectionActivity extends AppCompatActivity {

    ListView vehicleTypeLV;
    int vehicles[] = {R.drawable.carvehicletype,R.drawable.bikevehicletype};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_type_selection);

        getSupportActionBar().setTitle("Select a Vehicle");

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
}
