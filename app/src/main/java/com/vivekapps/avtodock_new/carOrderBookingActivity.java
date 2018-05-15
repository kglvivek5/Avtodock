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
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.vivekapps.DTO.BookingDTO;
import com.vivekapps.utils.BookingServices;
import com.vivekapps.utils.RetrofitClient;
import com.vivekapps.utils.SaveSharedPreference;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    String user_id;
    ProgressBar progressBar;

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

        //Fetch user id from Shared pref
        user_id = SaveSharedPreference.getUserID(getApplicationContext());

        //Create Booking Info table
        updateBookingInfo();

        //Create Add location button
        locationLayout = (TableLayout) findViewById(R.id.tableLayoutLocation);
        addLocation();

        carOrderName = (EditText) findViewById(R.id.custNameEditText);
        carOrderEmail = (EditText) findViewById(R.id.custEMailEditText);
        carOrderPhone = (EditText) findViewById(R.id.custPhoneEditText);
        carOrderAddress = (EditText) findViewById(R.id.custAddressEditText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        carOrderBookButton = (Button) findViewById(R.id.carOrderBookButton);
        carOrderBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String carOrderNameText = carOrderName.getText().toString();
                final String carOrderEmailText = carOrderEmail.getText().toString();
                final String carOrderPhoneText = carOrderPhone.getText().toString();
                final String carOrderAddressText = carOrderAddress.getText().toString();
                if (!carOrderNameText.isEmpty() && !carOrderEmailText.isEmpty() && !carOrderPhoneText.isEmpty()
                        && !carOrderAddressText.isEmpty()) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(carOrderBookingActivity.this);
                    alertBuilder.setMessage("Do you want to Confirm booking");
                    alertBuilder.setCancelable(true);
                    alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            HashMap<String,String> bookingDetails = new HashMap<String, String>();
                            bookingDetails.put("carOrderNameText",carOrderNameText);
                            bookingDetails.put("carOrderEmailText",carOrderEmailText);
                            bookingDetails.put("carOrderPhoneText",carOrderPhoneText);
                            bookingDetails.put("carOrderAddressText",carOrderAddressText);
                            bookingDetails.put("latitude",String.valueOf(latitude));
                            bookingDetails.put("longitude",String.valueOf(longitude));
                            bookingDetails.put("selectedCarPackage",selectedCarPackage);
                            bookingDetails.put("isCarExtraDetailSelected",String.valueOf(isCarExtraDetailSelected));
                            bookingDetails.put("carType",carType);
                            bookingDetails.put("user_id",user_id);
                            Log.i("carOrderBookingActivity",bookingDetails.toString());
                            insertBooking(bookingDetails);
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

    private void insertBooking(HashMap<String,String> bookingDetails) {

        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Retrofit retrofit = RetrofitClient.getClient();

        final BookingServices bookingServices = retrofit.create(BookingServices.class);
        Call<BookingDTO> call = bookingServices.insertBooking("insert",bookingDetails.get("user_id"),
                "car",bookingDetails.get("carType"),
                bookingDetails.get("selectedCarPackage"),bookingDetails.get("isCarExtraDetailSelected"),
                bookingDetails.get("carOrderAddressText"),bookingDetails.get("latitude"),
                bookingDetails.get("longitude"),"false");

        call.enqueue(new Callback<BookingDTO>() {
            @Override
            public void onResponse(Call<BookingDTO> call, Response<BookingDTO> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getApplicationContext(),"Thanks for your order",Toast.LENGTH_SHORT).show();
                    Intent launcherActivityIntent = new Intent(getApplicationContext(),vehicleTypeSelectionActivity.class);
                    launcherActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getApplicationContext().startActivity(launcherActivityIntent);
                } else {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getApplicationContext(), "Error while creating order. Please try again later",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingDTO> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.e("CarOrderBookingFailure", "=======onFailure: " + t.toString());
                t.printStackTrace();
            }

        });

    }
}
