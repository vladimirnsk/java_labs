package com.example.laba2.core;

import java.io.Serializable;

public class Request implements Serializable {
    public String toName;
    public String fromName;
 public Request(String toName, String fromName){
     this.toName = toName;
     this.fromName = fromName;
 }

}
