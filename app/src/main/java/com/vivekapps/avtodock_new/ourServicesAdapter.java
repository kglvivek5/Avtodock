package com.vivekapps.avtodock_new;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ourServicesAdapter extends BaseAdapter{

    Context context;
    String services[];
    LayoutInflater inflater;

    public ourServicesAdapter(Context applicationContext, String[] services ) {
        this.context = applicationContext;
        this.services = services;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return services.length;
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
        view = inflater.inflate(R.layout.activity_our_services_list_view, null);
        Log.i("servicesAdapter",services[i]);
        TextView servicesTV = (TextView) view.findViewById(R.id.ourServicesTextView);
        servicesTV.setText(Html.fromHtml(services[i]));
        return view;
    }
}
