package org.addressbook.main;

public class SimpleMain{
  public static void main(String[] args){
    try{
      new SimpleApplication().start();
    }catch(Exception e){
      e.printStackTrace();
      System.err.println("Fatal error. Please see logfile for technical reasons.");
    }
  }
}
