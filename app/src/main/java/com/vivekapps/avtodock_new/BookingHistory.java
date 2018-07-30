package com.vivekapps.avtodock_new;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vivekapps.DTO.BookingHistoryDTO;
import com.vivekapps.DTO.LoginDTO;
import com.vivekapps.DTO.LoginDataDTO;
import com.vivekapps.utils.BookingHistoryDataModel;
import com.vivekapps.utils.BookingServices;
import com.vivekapps.utils.RetrofitClient;
import com.vivekapps.utils.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class BookingHistory extends AppCompatActivity {

    ProgressBar progressBar;
    ListView bookingHistoryListView;
    BookingHistoryAdapter bookingHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.infotoolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Booking History");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String user_id = SaveSharedPreference.getUserID(getApplicationContext());
//        fetchBookingHistoryData(user_id);

        bookingHistoryListView = (ListView) findViewById(R.id.bookingHistoryListView);
//        Log.i("BookingHistory",BookingHistoryDataModel.getBookingDate()[0]);
        bookingHistoryAdapter = new BookingHistoryAdapter(getApplicationContext(), new ArrayList<BookingHistoryDataModel>());
        bookingHistoryListView.setAdapter(bookingHistoryAdapter);

        (new BookingAsyncTask()).execute(user_id);

    }

    private class BookingAsyncTask extends AsyncTask<String,Void,List<BookingHistoryDataModel>> {

        @Override
        protected List<BookingHistoryDataModel> doInBackground(String... Params) {
            List<BookingHistoryDataModel> result = fetchBookingHistoryData(Params[0]);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(List<BookingHistoryDataModel> result) {
            super.onPostExecute(result);
            bookingHistoryAdapter.setItemList(result);
            bookingHistoryAdapter.notifyDataSetChanged();

        }

        private List<BookingHistoryDataModel> fetchBookingHistoryData(String user_id) {

            final List<BookingHistoryDataModel> resultList = new ArrayList<BookingHistoryDataModel>();
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Retrofit retrofit = RetrofitClient.getClient();
            final BookingServices bookingServices = retrofit.create(BookingServices.class);
            Call<BookingHistoryDTO> call = bookingServices.fetchBookingHistory("fetch", user_id);

            call.enqueue(new Callback<BookingHistoryDTO>() {
                @Override
                public void onResponse(Call<BookingHistoryDTO> call, Response<BookingHistoryDTO> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        response.body();
                        int size = response.body().getData().length;
                        for (int i = 0; i < size; i++) {
                            resultList.add(new BookingHistoryDataModel("15-May-2018",
                                    response.body().getData()[i].getCar_type(),
                                    response.body().getData()[i].getCar_package(),
                                    String.valueOf(1000),
                                    response.body().getData()[i].getVehicle_type()));
                        }

                    } else {
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BookingHistoryDTO> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Log.e("TAG", "=======onFailure: " + t.toString());
                    t.printStackTrace();
                    // Log error here since request failed
                }
            });

            return resultList;
        }

    }

}
