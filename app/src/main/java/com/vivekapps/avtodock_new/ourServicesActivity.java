package com.vivekapps.avtodock_new;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


public class ourServicesActivity extends AppCompatActivity {

    ListView ourServicesListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_services);

        Toolbar toolbar = (Toolbar) findViewById(R.id.infotoolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String[] services = new String[2];
        services[0] = getResources().getString(R.string.exteriorcleaning);
        services[1] = getResources().getString(R.string.interiorcleaning);

        ourServicesListView = (ListView) findViewById(R.id.ourServicesListView);
        ourServicesAdapter ourServicesAdapter = new ourServicesAdapter(getApplicationContext(),services);
        ourServicesListView.setAdapter(ourServicesAdapter);


    }
}
