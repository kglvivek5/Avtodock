package com.vivekapps.avtodock_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by narasimma on 07/03/18.
 */

public class carPackageExtraDetailAdapter extends BaseAdapter {

    Context context;
    String[] carPackageEDName;
    String[] carPackageEDDetails;
    String[] carPackageEDPrice;
    LayoutInflater inflater;
    public static boolean isExtraDetailSelected=false;

    public carPackageExtraDetailAdapter(Context applicationContext, String[] carPackageEDName, String[] carPackageEDDetails, String[] carPackageEDPrice) {
        this.context = applicationContext;
        this.carPackageEDName = carPackageEDName;
        this.carPackageEDDetails = carPackageEDDetails;
        this.carPackageEDPrice = carPackageEDPrice;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return carPackageEDName.length;
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
        view = inflater.inflate(R.layout.activity_car_package_extra_detail, null);
        TextView carPackEDName = (TextView) view.findViewById(R.id.carPackageExtraDetailName);
        carPackEDName.setText(carPackageEDName[i]);
        TextView carPackEDDetails = (TextView) view.findViewById(R.id.carPackageExtraDetailDesc);
        carPackEDDetails.setText(carPackageEDDetails[i]);
        TextView carPackEDPrice = (TextView) view.findViewById(R.id.carPackageExtraDetailPrice);
        carPackEDPrice.setText(carPackageEDPrice[i]);

        final CheckBox cbExtraDetail = (CheckBox)view.findViewById(R.id.carPackageExtraDetailCB);
        isExtraDetailSelected = false;
        cbExtraDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbExtraDetail.isChecked()) {
                    isExtraDetailSelected = true;
                }
            }
        });
        return view;
    }

}
