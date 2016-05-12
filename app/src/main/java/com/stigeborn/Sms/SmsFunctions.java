package com.stigeborn.Sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.stige.letsparty.MainActivity;
import com.example.stige.letsparty.Sms;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Stige on 29/03/2016.
 */
public class SmsFunctions {
     android.net.Uri SMS_INBOX_CONTENT_URI = Uri.parse("content://sms/inbox");
        String SMS_READ_COLUMN = "read";
        String SORT_ORDER = "date DESC";
        int count = 0;
        // Log.v(WHERE_CONDITION);

    public List<Sms> getAllSms(String path) {
        List<Sms> lstSms = new ArrayList<Sms>();
        Sms objSms;
        Uri message = Uri.parse(path);
        ContentResolver cr = MainActivity.mainactivity.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        MainActivity.mainactivity.startManagingCursor(c);
        int totalSMS = c.getCount();


        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c
                        .getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                lstSms.add(objSms);
/*                if(objSms.getMsg().contains("?")){
                    MainActivity.qSms.add(objSms);
                }else{
                    MainActivity.aSms.add(objSms);
                }
                //MainActivity.allSms.add(objSms);
*/
                c.moveToNext();
            }
  //          MainActivity.allSms.addAll(lstSms);
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c.close();

        return lstSms;
    }

}
