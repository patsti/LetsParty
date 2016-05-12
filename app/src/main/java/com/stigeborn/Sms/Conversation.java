package com.stigeborn.Sms;

import com.example.stige.letsparty.Sms;

/**
 * Created by Patirk on 13/04/2016.
 */
public class Conversation {
    private com.example.stige.letsparty.Sms question, response;
    public Conversation(com.example.stige.letsparty.Sms q, com.example.stige.letsparty.Sms r){
        this.question = q;
        this.response = r;
    }

    public com.example.stige.letsparty.Sms getQuestion(){
        return question;
    }
    public Sms getResponse(){
        return response;
    }
}
