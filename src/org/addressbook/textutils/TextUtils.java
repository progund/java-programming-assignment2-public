package org.addressbook.textutils;

/**
 * A utility class with (so far) one static method for getting
 * values from the user, using standard out and standard in for
 * communication.
 */
public class TextUtils{

  /* We don't want client code to instantiate this class */
  private TextUtils(){}

  /**
   * Asks the user a question and returns the result.
   * Note, this method doesn't check a user reply of NULL,
   * the empty String "". Nor does it trim the user's reply.
   *
   * These limitations are topics for the next assignment!
   * @param prompt The question to ask the user, e.g. "Name: "
   * @return The user's reply as a reference to a String
   *
   */
  public static String askFor(String prompt){
    String result=null;
    System.out.print(prompt + ": ");
    if(System.console() == null){
      result = new java.util.Scanner(System.in).nextLine();
    }else{
      result = System.console().readLine();
    }
    return result;
  }
}
