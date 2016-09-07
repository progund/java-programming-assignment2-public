package org.addressbook.textutils;

public class TextUtils{

  /**
   * Asks the user a question and returns the result
   * @param prompt The question to ask the user, e.g. "Name: "
   * @return The user's reply as a reference to a String
   *
   * Note, this method doesn't check a user reply of NULL,
   * the empty String "". Nor does it trim the user's reply.
   *
   * These limitations are topics for the next assignment!
   */
  public static String askFor(String prompt){
    String result=null;
    System.out.print(prompt + ": ");
    result = System.console().readLine();
    return result;
  }
}
