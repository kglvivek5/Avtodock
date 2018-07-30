package com.vivekapps.avtodock_new;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vivekapps.utils.BookingHistoryDataModel;

import java.util.List;

public class BookingHistoryAdapter extends ArrayAdapter<BookingHistoryDataModel> {

    Context context;
    LayoutInflater inflater;
    private List<BookingHistoryDataModel> itemList;

    public List<BookingHistoryDataModel> getItemList() {
        return itemList;
    }

    public void setItemList(List<BookingHistoryDataModel> itemList) {
        this.itemList = itemList;
    }

    public BookingHistoryAdapter(Context applicationContext, List<BookingHistoryDataModel> itemList) {
        super(applicationContext,android.R.layout.activity_list_item,itemList);
        this.context = applicationContext;
        this.itemList = itemList;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public BookingHistoryDataModel getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_booking_history_list_view, null);
        }
        BookingHistoryDataModel bookingHistoryDataModel = itemList.get(i);
        TextView bookingDateTV = (TextView) v.findViewById(R.id.dateTextView);
        TextView bookingModelTV = (TextView) v.findViewById(R.id.modelTextView);
        TextView bookingPackageTV = (TextView) v.findViewById(R.id.packageTextView);
        TextView bookingPriceTV = (TextView) v.findViewById(R.id.priceTextView);
        TextView bookingVehicleTypeTV = (TextView) v.findViewById(R.id.vehicleTypeTextView);
        Log.i("BookingHistoryAdapter",bookingHistoryDataModel.getBookingDate());
        Log.i("BookingHistoryAdapter",bookingHistoryDataModel.getBookingModel());
        Log.i("BookingHistoryAdapter",bookingHistoryDataModel.getBookingPackage());
        Log.i("BookingHistoryAdapter",bookingHistoryDataModel.getBookingPrice());
        Log.i("BookingHistoryAdapter",bookingHistoryDataModel.getVehicleType());
        bookingDateTV.setText(bookingHistoryDataModel.getBookingDate());
        bookingModelTV.setText(bookingHistoryDataModel.getBookingModel());
        bookingPackageTV.setText(bookingHistoryDataModel.getBookingPackage());
        bookingPriceTV.setText(bookingHistoryDataModel.getBookingPrice());
        bookingVehicleTypeTV.setText(bookingHistoryDataModel.getVehicleType());

        return v;
    }


}
