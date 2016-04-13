package com.example.stige.letsparty;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mainactivity;
    public static List<Sms> allSms = new ArrayList<>(10);;
    public static List<Sms> inboxSms = new ArrayList<>();
    public static List<Sms> outboxSms = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView lv = (ListView) findViewById(R.id.lvMainSms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainactivity = this;
        SmsFunctions sf = new SmsFunctions();
        sf.getAllSms("content://sms/");
      //  inboxSms = sf.getAllSms("content://sms/inbox");
       // outboxSms = sf.getAllSms("content://sms/outbox");

        AdapterMainSort Ad = new AdapterMainSort(mainactivity, allSms, allSms );
        lv.setAdapter(Ad);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, /*allSms.get(1).getMsg()*/ String.valueOf(allSms.size()), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                AdapterMainSort Ad = new AdapterMainSort(mainactivity, allSms, allSms );
                lv.setAdapter(Ad);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static void uppdateSmsList(List<Sms> listS){
        AdapterMainSort Ad = new AdapterMainSort(mainactivity, listS, listS );
        ListView lv = (ListView) MainActivity.mainactivity.findViewById(R.id.lvMainSms);
        lv.setAdapter(Ad);
    }
}
