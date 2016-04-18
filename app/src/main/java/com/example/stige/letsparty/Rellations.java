package com.example.stige.letsparty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stige on 18/04/2016.
 */
public class Rellations {
    List< List<String>> allAxioms;

    public Rellations(){
        allAxioms = new ArrayList<>();
        List<String> time = new ArrayList<>();
        List<String> location = new ArrayList<>();
        //Läggtill ord med rellation till tid
        time.add("när");
        time.add("tid");
        //Läggtill ord med rellation till platser
        location.add("var");
        location.add("vart");
    }
    public List<String> getAxioms(String s){
        for(int i=0; i< allAxioms.size(); i++){
            for(int j=0; j<allAxioms.get(i).size(); j++){
                if(allAxioms.get(i).get(j).equals(s)){
                    return allAxioms.get(i);
                }
            }
        }
        List<String> noFound = new ArrayList<>();
        noFound.add(s);
        return noFound;
    }
}
