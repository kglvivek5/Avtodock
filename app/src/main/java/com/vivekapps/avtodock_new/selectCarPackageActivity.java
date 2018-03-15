package com.vivekapps.avtodock_new;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class selectCarPackageActivity extends AppCompatActivity {

    ListView carPackageListView;
    String[] carPackageName = {"H2O","Steam","Steam+"};
    String[] carPackageDetails = {"H2O description","Steam description","Steam+ description"};
    //String[] carPackagePrice = {"1000","2000","3000"};
    String[] carPackagePrice;
    ListView carPackageExtraDetailListView;
    String[] carPackageEDName = {"Include Extra Detailing"};
    String[] carPackageEDDesc = {"Extra Detailing description"};
    String[] carPackageEDPrice = {"1500"};
    Button proceedButton;
    String carType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_package);

        getSupportActionBar().setTitle("Select a Package");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        carPackagePrice = bundle.getStringArray("carPackagePrice");
        carType = bundle.getString("carType");

        carPackageListView = (ListView) findViewById(R.id.carPackageSelectListView);
        selectCarPackageAdapter carPackageAdapter = new selectCarPackageAdapter(getApplicationContext(),carPackageName,carPackageDetails,carPackagePrice);
        carPackageListView.setAdapter(carPackageAdapter);

        carPackageExtraDetailListView = (ListView) findViewById(R.id.carPackageExtraDetailListView);
        carPackageExtraDetailAdapter carPackageEDAdapter = new carPackageExtraDetailAdapter(getApplicationContext(),carPackageEDName,carPackageEDDesc,carPackageEDPrice);
        carPackageExtraDetailListView.setAdapter(carPackageEDAdapter);

        proceedButton = (Button) findViewById(R.id.carPackageProceedButton);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carOrderBookingIntent = new Intent(getApplicationContext(),carOrderBookingActivity.class);
                Bundle carOrderBookingBundle = new Bundle();
                carOrderBookingBundle.putString("carType",carType);
                carOrderBookingBundle.putStringArray("carPackagePrice",carPackagePrice);
                carOrderBookingIntent.putExtras(carOrderBookingBundle);
                startActivity(carOrderBookingIntent);
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
