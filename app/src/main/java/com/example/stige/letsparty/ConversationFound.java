package com.example.stige.letsparty;

/**
 * Created by Patirk on 13/04/2016.
 */
public class ConversationFound {
    private Sms question, response;
    public ConversationFound(Sms q, Sms r){
        this.question = q;
        this.response = r;
    }

    public Sms getQuestion(){
        return question;
    }
    public Sms getResponse(){
        return response;
    }
}
