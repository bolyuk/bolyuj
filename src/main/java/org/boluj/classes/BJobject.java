package org.boluj.classes;

import org.boluj.Interpreter;

public class BJobject {
    public String value = "";
    public String type = "";

    public BJobject(String value, String type){
        this.value=value;
        this.type=type;
    }

    public BJobject(){
    }

    public boolean isNumber(){
        return type == "number";
    }

    public boolean isString(){
        return type == "string";
    }

    public boolean isLogic(){
        return type == "logic" || value == "true" || value == "false" || value == "0" || value == "1";
    }

    public boolean isStatement(){
       return type == "statement";
    }

    public Integer getNumber(){
       return Integer.parseInt(value);
    }

    public boolean getLogic(){
        if(value == "true" || value == "1")
            return true;
         return false;
    }

    public BJobject getStatement(Interpreter context){
        return context.exec((BJStatement)this,new BJobject(),0);
    }
}
