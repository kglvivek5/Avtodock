package com.vivekapps.avtodock_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by narasimma on 04/03/18.
 */

public class carTypeSelectionAdapter extends BaseAdapter {

    Context context;
    int cars[];
    LayoutInflater inflater;

    public carTypeSelectionAdapter(Context applicationContext, int[] cars) {
        this.context = applicationContext;
        this.cars = cars;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return cars.length;
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
        view = inflater.inflate(R.layout.activity_car_type_selection_grid_view, null);
        ImageView type = (ImageView) view.findViewById(R.id.carTypeSelectionImageView);
        type.setImageResource(cars[i]);
        return view;
    }

}
