package com.example.stige.letsparty;


import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stige on 19/04/2016.
 */
public class responseAlgorithm {
    public List<Sms> getTopSuggestions(List<Conversation> conv){
        List<Sms> smsList = new ArrayList<>();
        HashMap<Sms, Integer> ratedSms = new HashMap<>();
        List<String> topWords = findMostlyUsedWords(conv);

        return smsList;
    }

    public List<String> findMostlyUsedWords(List<Conversation> conv){
        List<String> topList = new ArrayList<>();
        HashMap<String, Integer> topWords= new HashMap<>();

        for(int i=0; i < conv.size(); i++){
            Sms sms = conv.get(i).getResponse();
            String[] smsContent = sms.getMsg().split(" ");

            for(int j=0; j<smsContent.length; j++){
                topWords.put(smsContent[j], topWords.get(smsContent[j]+1));
            }
        }
        int overflow = 0;

        while (topWords.size()>0) {
            int maxValueInMap = (Collections.max(topWords.values()));

            for (Map.Entry<String, Integer> entry : topWords.entrySet()) {  // Itrate through hashmap
                if (entry.getValue()==maxValueInMap) {
                    topList.add(entry.getKey());     // Print the key with max value
                    topWords.remove(entry);
                }
            }
            overflow++;

            if(overflow == 2000){
                Toast.makeText(MainActivity.mainactivity, "Overflow", Toast.LENGTH_SHORT).show();
            }
        }

        return topList;
    }
}
