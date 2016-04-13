package com.example.stige.letsparty;

/**
 * Created by Stige on 07/04/2016.
 */
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdapterSmsList extends BaseAdapter {


    private Context c;
    private final LayoutInflater mInflater;
    private List<Sms> smsList;
    private static LayoutInflater inflater=null;


    public AdapterSmsList(Context c, List<Sms> sms) {
        mInflater = LayoutInflater.from(c);
        this.smsList = sms;

        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //  imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return smsList.size();
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
            vi = inflater.inflate(R.layout.item_sms, null);

        TextView to = (TextView)vi.findViewById(R.id.tvAdapterSmsTo); // title
        TextView from = (TextView)vi.findViewById(R.id.tvAdapterSmsFrom); // title
        TextView content = (TextView)vi.findViewById(R.id.tvAdapterSmsContent); // title

        Sms sms = smsList.get(position);

        to.setText(sms.getTime() +" : ");
        from.setText(sms.getId());
        content.setText(sms.getMsg());

//    imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}

