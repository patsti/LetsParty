package com.example.stige.letsparty;

/**
 * Created by Stige on 07/04/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdapterMainSort extends BaseAdapter{


    private Context c;
    private final LayoutInflater mInflater;
    private List<Sms> dataInput;
    private List<List<Sms>> data;
    private static LayoutInflater inflater=null;

    //public ImageLoader imageLoader;

    public AdapterMainSort(Context c, List<Sms> inbox, List<Sms> outbox) {
        mInflater = LayoutInflater.from(c);
        data = new ArrayList<>();
        data.add(inbox);
        //data.add(outbox);
        Toast.makeText(MainActivity.mainactivity, "sms list size: " + String.valueOf(data.size()), Toast.LENGTH_SHORT).show();
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //  imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.item_main, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        ListView smsList = (ListView) vi.findViewById(R.id.lvAdapterMain);
        AdapterSmsList Ad = new AdapterSmsList(MainActivity.mainactivity, data.get(position));

        smsList.setAdapter(Ad);

        return vi;
    }
}

