package com.vivekapps.avtodock_new;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.Toolbar;

public class carTypeSelectionActivity extends AppCompatActivity {

    GridView carTypeSelectionGridView;
    int carTypes[] = {R.drawable.hatchback,R.drawable.sedan,R.drawable.suv,R.drawable.lux};
    String carType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type);

        getSupportActionBar().setTitle("Select Car Type");
        // The below statements are for enabling the back button on the ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        carTypeSelectionGridView = (GridView) findViewById(R.id.carTypeSelectionGridView);
        carTypeSelectionAdapter carTypeAdapter = new carTypeSelectionAdapter(getApplicationContext(), carTypes);
        carTypeSelectionGridView.setAdapter(carTypeAdapter);

        carTypeSelectionGridView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent carPackageSelectIntent = new Intent(view.getContext(),selectCarPackageActivity.class);
                Bundle carPackagePriceDataBundle = new Bundle();
                String[] carPackagePriceData;
                if (position == 0) {
                    carPackagePriceData = new String[]{"1000", "2000", "3000"};
                    carType = "hatchback";
                    //Intent hatchBackIntent = new Intent(view.getContext(),selectCarPackageActivity.class);
                    //startActivityForResult(hatchBackIntent,0);
                } else if (position == 1) {
                    //Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
                    carPackagePriceData = new String[]{"1500", "2500", "3500"};
                    carType = "sedan";
                } else if (position == 2) {
                    carPackagePriceData = new String[]{"1700", "2700", "3700"};
                    carType = "suv";
                } else if (position == 3) {
                    carPackagePriceData = new String[]{"2000", "3000", "4000"};
                    carType = "lux";
                } else {
                    carPackagePriceData = new String[]{"1000", "2000", "3000"};
                    carType = "hatchback";
                }
                carPackagePriceDataBundle.putStringArray("carPackagePrice",carPackagePriceData);
                carPackagePriceDataBundle.putString("carType",carType);
                carPackageSelectIntent.putExtras(carPackagePriceDataBundle);
                startActivity(carPackageSelectIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
