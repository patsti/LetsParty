package com.stigeborn.Sms;

import android.os.AsyncTask;
import android.util.Log;

import com.example.stige.letsparty.Sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Patirk on 25/02/2016.
 */
public class GetWordBaseForm extends AsyncTask<String, Void, String> {

    private Exception exception;

    HashMap<String, Integer> returnMap = new HashMap<>();
    HashMap<String, Integer> initMap = new HashMap<>();

    List<Sms> initSms;
    List<Sms> returnSms = new ArrayList<>();
    int row = 1;

    GetWordBaseForm(List<Sms> sms, HashMap<String, Integer> map) throws Exception{
        this.initSms = sms;
        this.initMap = map;
    }

    protected String doInBackground(String... urls) {
        initMap.keySet().toArray();

        for ( String word : initMap.keySet() ) {
            try {
                word = word.replace("?","").replace("!", "").replace(".","").replace(",","");
                URL synonymURL = new URL("http://www.synonymer.se/?query="+word);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(synonymURL.openStream()));
                String inputLine;
                boolean getData = false;

                while ((inputLine = in.readLine()) != null){

                    if(inputLine.contains("<div class=\"boxHeader\">")){
                        getData = true;
                    }

                    if(getData && !inputLine.contains("Inga synonymer hittades") && inputLine.length()>55){
                        getData = false;
                        in.close();
                        //inputLine = inputLine.replace("<a href=\"", "");
                        int start = inputLine.indexOf("<div class=\"boxHeader\">")+55;

                        //Ta fram ordets grundform
                        /*
                        * GER FEL NÄR DET NYA ORDET ÄR KORTARE ÄN DET TIDIGARE
                         */
                        String word_in_base_form = inputLine.substring(start, inputLine.length());
                        int end = word_in_base_form.indexOf("<");
                        Log.d("--Untz html--", word_in_base_form);
                        word_in_base_form = word_in_base_form.substring(0, end);
                        //word_in_base_form = word_in_base_form.replace("\"", "").replace(">", "");


                        if(!word_in_base_form.contains("?") && !word_in_base_form.contains("\"") && !word_in_base_form.contains(".") && !word_in_base_form.equals(word)) {
                            Log.d("--Untz Untz--", word_in_base_form);
                            if (!returnMap.containsKey(word_in_base_form)) {
                                Log.d("--Untz Adding--", word_in_base_form +" with value "+ String.valueOf(initMap.get(word)) );
                                returnMap.put(word_in_base_form, initMap.get(word));
                            } else {
                                Log.d("--Untz Increasing--", word_in_base_form+" with "+String.valueOf(returnMap.get(word_in_base_form))+ " " + String.valueOf(initMap.get(word)));
                                returnMap.put(word_in_base_form, returnMap.get(word_in_base_form) + initMap.get(word));
                            }

                            for (int i = 0; i < returnSms.size(); i++) {
                                String tmp_msg = returnSms.get(i).getMsg();

                            }

                            for (int i = 0; i < initSms.size(); i++) {
                                Sms nySms = new Sms();
                                Sms mainSms = initSms.get(i);
                                Sms oldSms = null;
                                for(Sms sms : returnSms){
                                    if(sms.getId().equals(mainSms.getId())){
                                        mainSms = sms;
                                        oldSms = sms;
                                    }
                                }
                                String tmp_msg = mainSms.getMsg();
                                if(tmp_msg.contains(word)) {
                                    Log.d("--Untz Message--", tmp_msg);
                                    Log.d("--Untz Replace--", word + " with " + word_in_base_form);
                                    tmp_msg = tmp_msg.replace(word, word_in_base_form);
                                    Log.d("--Untz Result--", tmp_msg);
                                    //  initSms.get(i).setMsg(tmp_msg);
                                }

                                nySms.setId(mainSms.getId());
                                nySms.setMsg(tmp_msg);
                                nySms.setAddress(mainSms.getAddress());
                                nySms.setFolderName(mainSms.getFolderName());
                                nySms.setReadState(mainSms.getReadState());
                                nySms.setTime(mainSms.getTime());
                                if(oldSms != null){
                                    this.returnSms.remove(oldSms);
                                }
                                this.returnSms.add(nySms);
                            }
                        }else{
                            Log.d("--Untz No change--", word);
                            if (!returnMap.containsKey(word.toLowerCase())) {
                                returnMap.put(word.toLowerCase(), initMap.get(word));
                            } else {
                                returnMap.put(word.toLowerCase(), returnMap.get(word.toLowerCase()) + initMap.get(word));
                            }
                        }
                    }else{
                      //  Log.d("--Untz Inga synonym--", word);
                    }
                }
                in.close();
            }catch (IOException e){
            }
        }
        return null;

    }

    protected void onPostExecute(String feed) {
           /*
            */
        //Toast.makeText(MainActivity.mainactivity, word_in_base_form, Toast.LENGTH_SHORT).show();
        ResponseAlgorithm RA = new ResponseAlgorithm();
        RA.parseWebResult(this.returnSms, returnMap);
    }
}