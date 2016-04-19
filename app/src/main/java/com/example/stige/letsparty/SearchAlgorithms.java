package com.example.stige.letsparty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patirk on 16/04/2016.
 */
public class SearchAlgorithms {
    public List<Conversation> findMatch(String sms, List<Sms> lSms){
        List<Conversation> Conversations = new ArrayList<>();
        String[] smsContent = sms.split(" ");//.getMsg().split(" ");
        Rellations rel = new Rellations();
        int matchRate = smsContent.length;
        for(int i=0; i<matchRate; i++){
            smsContent[i].replace("?","").replace("!", "").replace(".","").replace(",","");
        }

        for(int i = 1; i < lSms.size(); i++){
            int matchWords = 0;
            if(lSms.get(i).getFolderName().contains("inbox")) {
                for(int j = 0; j < matchRate; j++){
                    String message = lSms.get(i).getMsg().replace("?", "").replace("!","").replace(".","").replace(",","").toLowerCase();
                    List<String> axioms = rel.getAxioms(smsContent[j]);

                    for(int k=0; k<axioms.size(); k++) {

                        if (message.contains(" " + axioms.get(k) + " ") || message.startsWith(axioms.get(k) + " ") || message.endsWith(" " + axioms.get(k))) {
                            String[] smsContent2 = lSms.get(i).getMsg().split(" ");
                            int spaces = smsContent2.length;

                            if (spaces <= matchRate * 2) {
                                matchWords++;
                            }
                        }
                    }
                }
            }
            if((float)matchWords >= Math.round(matchRate*0.7f)){
               for(int j=1; j<5; j++) {
                   if(i>j) {
                       if (lSms.get(i).getAddress().equals(lSms.get(i - j).getAddress()) && !lSms.get(i).getFolderName().equals(lSms.get(i - j).getFolderName())) {
                           Conversations.add(new Conversation(lSms.get(i), lSms.get(i - j)));
                           break;
                       }
                   }
                }
            }
        }

        return Conversations;
    }
}
