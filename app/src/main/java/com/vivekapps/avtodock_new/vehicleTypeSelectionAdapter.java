package com.vivekapps.avtodock_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Lakshmi Narasimma on 02/03/18.
 */

public class vehicleTypeSelectionAdapter extends BaseAdapter {

    Context context;
    int vehicles[];
    LayoutInflater inflater;

    public vehicleTypeSelectionAdapter(Context applicationContext, int[] vehicles) {
        this.context = applicationContext;
        this.vehicles = vehicles;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return vehicles.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_vehicle_type_selection_list_view, null);
        ImageView type = (ImageView) view.findViewById(R.id.vehicleTypeImage);
        type.setImageResource(vehicles[i]);
        return view;
    }


}
