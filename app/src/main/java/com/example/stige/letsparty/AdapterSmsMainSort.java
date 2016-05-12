package com.example.stige.letsparty;

/**
 * Created by Stige on 07/04/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdapterSmsMainSort extends BaseAdapter{


    private Context c;
    private final LayoutInflater mInflater;
    private List<Sms> data;
    private static LayoutInflater inflater=null;

    //public ImageLoader imageLoader;

    public AdapterSmsMainSort(Context c, List<Sms> inbox) {
        mInflater = LayoutInflater.from(c);
        data = new ArrayList<>();
        data.addAll(inbox);
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
            vi = inflater.inflate(R.layout.item_conversation, null);


        Sms smsR = data.get(position);
        TextView toR = (TextView)vi.findViewById(R.id.tvAdapterSmsTo2); // title
        TextView fromR = (TextView)vi.findViewById(R.id.tvAdapterSmsFrom2); // title
        TextView contentR = (TextView)vi.findViewById(R.id.tvAdapterSmsContent2); // title

        toR.setText(smsR.getAddress());
        fromR.setText(smsR.getFolderName());
        contentR.setText(smsR.getMsg());

        return vi;
    }
}

