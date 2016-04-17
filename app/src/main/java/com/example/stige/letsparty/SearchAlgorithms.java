package com.example.stige.letsparty;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patirk on 16/04/2016.
 */
public class SearchAlgorithms {
    public List<ConversationFound> findMatch(String sms, List<Sms> lSms){
        List<ConversationFound> Conversations = new ArrayList<>();
        String[] smsContent = sms.split(" ");//.getMsg().split(" ");
        int matchRate = smsContent.length;
        for(int i=0; i<matchRate; i++){
            smsContent[i].replace("?","").replace("!","");
        }
        Toast.makeText(MainActivity.mainactivity, "hej :"+ String.valueOf(matchRate), Toast.LENGTH_LONG).show();

        for(int i = 1; i < lSms.size(); i++){
            int matchWords = 0;
            if(lSms.get(i).getFolderName().contains("sent")) {
                for(int j = 0; j < matchRate; j++){
                    if (lSms.get(i).getMsg().contains(" " + smsContent[j] + " ") || lSms.get(i).getMsg().startsWith(smsContent[j] + " ") || lSms.get(i).getMsg().endsWith(" " + smsContent[j])) {
                        String[] smsContent2 = lSms.get(i).getMsg().split(" ");
                        int spaces = smsContent2.length;
                        if (spaces <= matchRate * 2) {
                            matchWords++;
                        }
                    }
                }
            }
            if((float)matchWords >= Math.round(matchRate*0.5f)){
               for(int j=1; j<5; j++) {
                    if (lSms.get(i).getAddress().equals(lSms.get(i - j).getAddress()) && !lSms.get(i).getFolderName().equals(lSms.get(i - j).getFolderName())) {
                        Conversations.add(new ConversationFound(lSms.get(i), lSms.get(i - 1)));
                        break;
                    }
                }
            }
        }

        return Conversations;
    }
}
