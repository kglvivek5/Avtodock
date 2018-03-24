package com.vivekapps.avtodock_new;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class carOrderBookingActivity extends AppCompatActivity {

    String carType;
    String[] carPackagePrice;
    Button carOrderBookButton;
    EditText carOrderName,carOrderEmail,carOrderPhone,carOrderAddress;
    double latitude,longitude;
    String placeName;
    TextView addLocationText,removeLocationText;
    TableLayout locationLayout;
    TableRow tableRow;
    private final static int PLACE_PICKER_REQUEST = 1000;

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

        //Create Add location button
        locationLayout = (TableLayout) findViewById(R.id.tableLayoutLocation);
        addLocation();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //checkPermissionOnActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(this, data);
                    placeName = String.format("Place: %s", place.getName());
                    latitude = place.getLatLng().latitude;
                    longitude = place.getLatLng().longitude;
                    Log.i("carOrderBookingActivity", String.valueOf(latitude));
                    Log.i("carOrderBookingActivity",String.valueOf(longitude));
                    clearLocation(locationLayout);

            }
        }
    }

    private void addLocation() {

        locationLayout.removeAllViews();
        addLocationText = new TextView(this);
        addLocationText.setText("Add Location");
        addLocationText.setGravity(Gravity.CENTER);
        addLocationText.setTextColor(Color.BLUE);
        addLocationText.setBackgroundColor(Color.WHITE);
        addLocationText.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100));
        addLocationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent mapsActivityIntent = new Intent(carOrderBookingActivity.this,MapsActivity.class);
                startActivityForResult(mapsActivityIntent,2);*/
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(carOrderBookingActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        locationLayout.addView(addLocationText);
    }

    private void clearLocation(TableLayout layout) {

        layout.removeAllViews();

        layout.setStretchAllColumns(true);
        tableRow = new TableRow(carOrderBookingActivity.this);
        tableRow.setBackgroundColor(Color.WHITE);
        tableRow.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int viewWidth = deviceWidth / 2;*/

        removeLocationText = new TextView(carOrderBookingActivity.this);
        removeLocationText.setText("Remove Location");
        removeLocationText.setTextColor(Color.BLUE);
        removeLocationText.setGravity(Gravity.RIGHT);
        removeLocationText.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100));
        removeLocationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocation();
            }
        });

        TextView locationAddedMsg = new TextView(carOrderBookingActivity.this);
        locationAddedMsg.setText("Your Location Added");
        locationAddedMsg.setGravity(Gravity.LEFT);
        locationAddedMsg.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100));

        tableRow.addView(locationAddedMsg);
        tableRow.addView(removeLocationText);
        layout.addView(tableRow);
    }
}
