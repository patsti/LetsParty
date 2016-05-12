package com.stigeborn.Sms;


import android.util.Log;

import com.example.stige.letsparty.Conversation;
import com.example.stige.letsparty.Sms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by Stige on 19/04/2016.
 */
public class ResponseAlgorithm {
    public static List<Sms> smsList;
    public List<Sms> getTopSuggestions(List<Conversation> conv){
        smsList = new ArrayList<>();
        HashMap<Sms, Integer> ratedSms = new HashMap<>();
        HashMap<String, Integer> ratedWords = findMostlyUsedWords(conv);

        for(int i=0; i < conv.size(); i++) {
            Sms sms = conv.get(i).getResponse();
            smsList.add(sms);
        }
        try{
          //  MainActivity.transformToBaseForm(smsList, ratedWords);
            new BabLaGetVerbs2(smsList, ratedWords).execute();
        }catch(Exception e){
        }
        return smsList;
    }

    /*
    * Function that takes a list of conversations and looking at the response messages
     */
    public HashMap<String, Integer> findMostlyUsedWords(List<Conversation> conv){
    //    List<String> topList = new ArrayList<>();
        HashMap<String, Integer> topWords= new HashMap<>();

        for(int i=0; i < conv.size(); i++){
            Sms sms = conv.get(i).getResponse();
            String[] smsContent = sms.getMsg().split(" ");

            for(int j=0; j<smsContent.length; j++){
                String word = smsContent[j].toLowerCase().replace("?","").replace("!", "").replace(".","").replace(",","");
                if(!topWords.containsKey(word)) {
                    Log.d("--Untz FIRST ADDING--", word);
                    topWords.put(word,1);
                }
                else {
                    Log.d("--Untz FIRST INCREASE--", word +" to "+ String.valueOf(topWords.get(word)+1));
                    topWords.put(word, topWords.get(word)+1);
                }

            }
        }
        return topWords;
    }
    //Gets called from BabLaGetVerbs(2)
    public void parseWebResult(List<Sms> smsList, HashMap<String, Integer> map){
        HashMap<String, Integer> idMap = new HashMap<>();
        List<Sms> suggestionList = new ArrayList<>();

        for ( String key : map.keySet() ) {
            Log.d("<-Untz key: "+key, " value: "+String.valueOf(map.get(key)));
        }

        for ( Sms key : smsList ) {
            Log.d("---Untz message--", key.getMsg());
        }
        Log.d("---Untz smsList size--", String.valueOf(smsList.size()));

        for(int i=0; i < smsList.size(); i++){
            Sms sms = smsList.get(i);
            String[] smsContent = sms.getMsg().split(" ");
            List<String> contentFixed = new ArrayList<>();
            int smsRate = 0;
            //Remove all unnessecary extentions (?!.,)
            for(int j=0; j<smsContent.length; j++) {
                contentFixed.add(smsContent[j].toLowerCase().replace("?", "").replace("!", "").replace(".", "").replace(",", ""));
            }

            contentFixed = new ArrayList<String>(new HashSet<String>( contentFixed ));

            for(int j=0; j<contentFixed.size(); j++){
               // Log.d("--Untz Untz2--", smsContent[j]);
                if(map.get(contentFixed.get(j)) != null){
                    smsRate += map.get(contentFixed.get(j));
                }else{
                    Log.d("--Untz2 didnt find--", contentFixed.get(j));
                }
            }
            // TODO: This may give to much msg output.
            idMap.put(sms.getId(), Math.round(((float)smsRate *10.0f) /((float)smsContent.length)));
            //ratedSms.put(sms, smsRate);
        }


        while (idMap.size()>0) {
            int maxValueInMap = (Collections.max(idMap.values()));
            String maxEntry = null;

            for (Map.Entry<String, Integer> entry : idMap.entrySet()) {  // Itrate through hashmap
                if (entry.getValue()==maxValueInMap) {
                    maxEntry = entry.getKey();
                    Log.d("--Untz2 highest--", String.valueOf(maxValueInMap));
                    for(int i=0; i<APIGetSmsList.allSms.size(); i++){
                            if(APIGetSmsList.allSms.get(i).getId().equals(entry.getKey())){
                                Log.d("--Untz2 message-->", APIGetSmsList.allSms.get(i).getMsg());
                                suggestionList.add(APIGetSmsList.allSms.get(i));
                            }
                        }
                    break;
                }
            }
            if(maxEntry!=null){
                idMap.remove(maxEntry);
            }
        }
        APIGetSmsList.executeOnResult(suggestionList);
    }
}
