package com.vivekapps.avtodock_new;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by narasimma on 05/03/18.
 */

public class selectCarPackageAdapter extends BaseAdapter {

    Context context;
    String[] carPackageName;
    String[] carPackageDetails;
    String[] carPackagePrice;
    LayoutInflater inflater;
    RadioButton selected = null;

    public selectCarPackageAdapter(Context applicationContext, String[] carPackageName, String[] carPackageDetails, String[] carPackagePrice) {
        this.context = applicationContext;
        this.carPackageName = carPackageName;
        this.carPackageDetails = carPackageDetails;
        this.carPackagePrice = carPackagePrice;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return carPackageName.length;
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
        view = inflater.inflate(R.layout.activity_car_package_selection_list_view, null);
        TextView carPackName = (TextView) view.findViewById(R.id.carPackageName);
        carPackName.setText(carPackageName[i]);
        Log.i("selectCarPackageAdapter",carPackageName[i]);
        String text = (String) carPackName.getText();
        Log.wtf("After setting values", text);
        TextView carPackDetails = (TextView) view.findViewById(R.id.carPackageDetails);
        carPackDetails.setText(carPackageDetails[i]);
        TextView carPackPrice = (TextView) view.findViewById(R.id.carPackagePrice);
        carPackPrice.setText(carPackagePrice[i]);
        final RadioButton rbCarPackage = (RadioButton) view.findViewById(R.id.carPackageSelectionRB);
        rbCarPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected != null) {
                    selected.setChecked(false);
                }
                rbCarPackage.setChecked(true);
                selected = rbCarPackage;
            }
        });

        return view;
    }


}
