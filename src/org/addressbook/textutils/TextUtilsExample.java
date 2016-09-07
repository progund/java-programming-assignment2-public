package org.addressbook.textutils;

public class TextUtilsExample{
  public static void main(String[] args){
    
    String favoriteColor = TextUtils.askFor("Your favorite color");
    String favoriteFood  = TextUtils.askFor("Favorie food");
    System.out.println("Your favorite color was: " + favoriteColor);
    System.out.println("Your favorite food was:  " + favoriteFood);
  }
}
