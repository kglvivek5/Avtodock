package com.vivekapps.avtodock_new;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

    String carType,selectedCarPackage;
    String[] carPackagePrice;
    boolean isCarExtraDetailSelected;
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
        selectedCarPackage = bundle.getString("selectedCarPackage");
        Log.i("carOrderBookingActivity","Selected Package is: "+selectedCarPackage);
        isCarExtraDetailSelected = bundle.getBoolean("isCarExtraDetailSelected");
        Log.i("carOrderBookingActivity","Extra Detailing value is:"+String.valueOf(isCarExtraDetailSelected));

        //Create Booking Info table
        updateBookingInfo();

        //Create Add location button
        locationLayout = (TableLayout) findViewById(R.id.tableLayoutLocation);
        addLocation();

        carOrderName = (EditText) findViewById(R.id.custNameEditText);
        carOrderEmail = (EditText) findViewById(R.id.custEMailEditText);
        carOrderPhone = (EditText) findViewById(R.id.custPhoneEditText);
        carOrderAddress = (EditText) findViewById(R.id.custAddressEditText);

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
                navigateToPacakgeSelection();
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
//        locationLayout.setBackgroundColor(Color.DKGRAY);
//        locationLayout.setPadding(0,1,0,0);
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
        locationLayout.setBackgroundColor(Color.WHITE);

        layout.setStretchAllColumns(true);
        layout.setPadding(0,1,0,0);
        tableRow = new TableRow(carOrderBookingActivity.this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        removeLocationText = new TextView(carOrderBookingActivity.this);
        removeLocationText.setText("Remove Location");
        removeLocationText.setTextColor(Color.BLUE);
        removeLocationText.setGravity(Gravity.RIGHT);
        removeLocationText.setPadding(0,0,40,0);
        removeLocationText.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        removeLocationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocation();
            }
        });

        TextView locationAddedMsg = new TextView(carOrderBookingActivity.this);
        locationAddedMsg.setText("Your Location Added");
        locationAddedMsg.setGravity(Gravity.CENTER_VERTICAL);
        locationAddedMsg.setPadding(40,0,0,0);

        locationAddedMsg.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));

        tableRow.addView(locationAddedMsg);
        tableRow.addView(removeLocationText);
        layout.addView(tableRow);
    }

    private void updateBookingInfo() {

        float bookingInfoTextSize = 12.0f;

        TableLayout bookingDetailTable = (TableLayout) findViewById(R.id.bookingInfoTablelayout);
        TableRow bookingDetailRow = (TableRow) findViewById(R.id.bookingInfoDetailsRow);
        bookingDetailRow.setWeightSum(3f);

        TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,1f);
        params.setMargins(40,0,40,0);

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(Color.TRANSPARENT);
        shape.setStroke(2,Color.CYAN);
        shape.setCornerRadius(15.0f);

        TextView carTypeBookingInfo = new TextView(carOrderBookingActivity.this);
        carTypeBookingInfo.setLayoutParams(params);
        carTypeBookingInfo.setBackground(shape);
        carTypeBookingInfo.setGravity(Gravity.CENTER);
        carTypeBookingInfo.setTextSize(bookingInfoTextSize);
        carTypeBookingInfo.setText(carType.toUpperCase());

        GradientDrawable shapePackageType = new GradientDrawable();
        shapePackageType.setShape(GradientDrawable.RECTANGLE);
        shapePackageType.setColor(Color.TRANSPARENT);
        shapePackageType.setStroke(2,Color.CYAN);
        shapePackageType.setCornerRadius(15.0f);

        TextView carPackageTypeBookingInfo = new TextView(carOrderBookingActivity.this);
        carPackageTypeBookingInfo.setLayoutParams(params);
        carPackageTypeBookingInfo.setBackground(shapePackageType);
        carPackageTypeBookingInfo.setGravity(Gravity.CENTER);
        carPackageTypeBookingInfo.setTextSize(bookingInfoTextSize);
        carPackageTypeBookingInfo.setText(selectedCarPackage.toUpperCase());

        if (isCarExtraDetailSelected) {
            GradientDrawable shapeExtraDetail = new GradientDrawable();
            shapeExtraDetail.setShape(GradientDrawable.RECTANGLE);
            shapeExtraDetail.setColor(Color.TRANSPARENT);
            shapeExtraDetail.setStroke(2,Color.CYAN);
            shapeExtraDetail.setCornerRadius(15.0f);

            TextView carExtraDetailSelectedInfo = new TextView(carOrderBookingActivity.this);
            carExtraDetailSelectedInfo.setLayoutParams(params);
            carExtraDetailSelectedInfo.setBackground(shapeExtraDetail);
            carExtraDetailSelectedInfo.setGravity(Gravity.CENTER);
            carExtraDetailSelectedInfo.setTextSize(bookingInfoTextSize);
            carExtraDetailSelectedInfo.setText("Car Detailing".toUpperCase());
            bookingDetailRow.addView(carExtraDetailSelectedInfo);
        }

        bookingDetailRow.addView(carTypeBookingInfo);
        bookingDetailRow.addView(carPackageTypeBookingInfo);

    }

    public void goBackToPackage(View view) {
        navigateToPacakgeSelection();
    }

    private void navigateToPacakgeSelection() {
        Intent backToCarPackIntent = new Intent(getApplicationContext(),selectCarPackageActivity.class);
        Bundle backToCarPackBundle = new Bundle();
        backToCarPackBundle.putStringArray("carPackagePrice",carPackagePrice);
        backToCarPackBundle.putString("carType",carType);
        backToCarPackIntent.putExtras(backToCarPackBundle);
        startActivity(backToCarPackIntent);
    }
}
