package com.vivekapps.avtodock_new;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class carOrderBookingActivity extends AppCompatActivity {

    String carType;
    String[] carPackagePrice;
    Button carOrderBookButton;
    EditText carOrderName,carOrderEmail,carOrderPhone,carOrderAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_order_booking);

        getSupportActionBar().setTitle("Book your Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        carType = bundle.getString("carType");
        carPackagePrice = bundle.getStringArray("carPackagePrice");

        carOrderName = (EditText) findViewById(R.id.carOrderName);
        carOrderEmail = (EditText) findViewById(R.id.carOrderEmail);
        carOrderPhone = (EditText) findViewById(R.id.carOrderPhone);
        carOrderAddress = (EditText) findViewById(R.id.carOrderAddress);

        carOrderBookButton = (Button) findViewById(R.id.carOrderBookButton);
        carOrderBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String carOrderNameText = carOrderName.getText().toString();
                String carOrderEmailText = carOrderEmail.getText().toString();
                String carOrderPhoneText = carOrderPhone.getText().toString();
                String carOrderAddressText = carOrderAddress.getText().toString();
                if (!carOrderNameText.isEmpty() && !carOrderEmailText.isEmpty() && !carOrderPhoneText.isEmpty()
                        && !carOrderAddressText.isEmpty()) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(carOrderBookingActivity.this);
                    alertBuilder.setMessage("Do you want to Confirm booking");
                    alertBuilder.setCancelable(true);
                    alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(),"Thanks for your order",Toast.LENGTH_SHORT).show();
                            Intent launcherActivityIntent = new Intent(getApplicationContext(),vehicleTypeSelectionActivity.class);
                            launcherActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            getApplicationContext().startActivity(launcherActivityIntent);
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(getApplicationContext(),"Enter all the details",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                Intent backToCarPackIntent = new Intent(getApplicationContext(),selectCarPackageActivity.class);
                Bundle backToCarPackBundle = new Bundle();
                backToCarPackBundle.putStringArray("carPackagePrice",carPackagePrice);
                backToCarPackBundle.putString("carType",carType);
                backToCarPackIntent.putExtras(backToCarPackBundle);
                startActivity(backToCarPackIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
