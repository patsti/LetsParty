package com.stigeborn.Sms;

import com.example.stige.letsparty.Conversation;
import com.example.stige.letsparty.MainActivity;
import com.example.stige.letsparty.Sms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patirk on 12/05/2016.
 */
public class APIGetSmsList {
    public static List<Sms> allSms;
    public static int RESPONSE_SIZE=0;
    public void getBestSmsResponse(String initSms, int MAX_RESPONSE_SIZE){
        this.RESPONSE_SIZE = MAX_RESPONSE_SIZE;
        allSms = new ArrayList<>();
        List<Conversation> answersSms = new ArrayList<>();

        //Collection all previous SMS on phone
        SmsFunctions sf = new SmsFunctions();
        allSms = sf.getAllSms("content://sms/");

        //Get a list of "look-a-likes" to initSMS and the response to each.
        SearchAlgorithms algo = new SearchAlgorithms();
        answersSms = algo.findMatch(initSms, allSms);

        ResponseAlgorithm RA = new ResponseAlgorithm();
        List<Sms> ordered_best_result = RA.getTopSuggestions(answersSms);
    }
    public static void executeOnResult(List<Sms> suggestionList){
        List<Sms> returnRequestedSize = new ArrayList<>();
        for(int i=0; i<RESPONSE_SIZE; i++){
            returnRequestedSize.add(suggestionList.get(i));
        }
        MainActivity.uppdateSmsList(returnRequestedSize);
    }
}
