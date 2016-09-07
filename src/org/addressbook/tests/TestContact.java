package org.addressbook.tests;
import org.addressbook.storage.Contact;

public class TestContact{

  /* Test that the constructor saves the arguments in instance variables */
  private static void testConstructor(String name, String email, String phone){
    System.out.println("=====Testing constructor=====");
    Contact c = new Contact(name, email, phone);
    System.out.println("Testing new Contact(\"" + name +
                       "\", \"" + email +
                       "\", \"" + phone +
                       "\")");                       
    assert name.equals(c.name()) : "* Name wasn't saved!";
    assert email.equals(c.email()) : "* Email wasn't saved!";
    assert phone.equals(c.phone()) : "* Phone wasn't saved!";
    System.out.println("* Constructor test for contact: " + c + " passed.");
  }
  
  private static void testConstructorWithNullCheck(){
    System.out.println("=====Testing constructor with null checks=====");
    try{
      new Contact(null, null, null);
      assert false : "null argument for name to Contact constructor failed!";
    }catch(NullPointerException npe){}
    try{
      new Contact("", null, null);
      assert false : "empty string argument for name to Contact constructor failed!";
    }catch(IllegalArgumentException iae){}
    assert new Contact("A",null,"").email().equals("") : "null argument for email" + 
      " wasn't converted to the empty String!";
    assert new Contact("A","",null).phone().equals("") : "null argument for phone" + 
      " wasn't converted to the empty String!";    
    System.out.println("* Constructor null tests passed.");
  }

  private static void testCompareTo(){
    System.out.println("=====Testing compareTo=====");
    // compareTo only compares the name, according to the specification
    Contact a = new Contact("Ana Anason", "", "");
    Contact b = new Contact("Ben Benson", "", "");

    // Contact with the same name as a (a.compareTo(sameAsA) should return 0)
    Contact sameAsA = new Contact("Ana Anason", "x", "123");

    assert a.compareTo(b)<0        : a + " is not less than " + b + "!";
    assert b.compareTo(a)>0        : b + " is not greater than " + a + "!";
    assert a.compareTo(a)==0       : a + " is not the same order as itself!";
    assert a.compareTo(sameAsA)==0 : a + " is not the same order as " + sameAsA; 
    assert sameAsA.compareTo(a)==0 : sameAsA + " is not the same order as " + a; 
    System.out.println("* Test of compareTo() in Contact passed!");
  }

  private static void testEquals(){
    // Equals should only check name according to the specification
    System.out.println("=====Testing equals=====");
    Contact c1 = new Contact("abcdef", "", "");
    Contact sameNameAsC1 = new Contact("abcdef", "", "");
    Contact differentNameFromC1 = new Contact("Something completely different", "", "");
    assert !c1.equals(null) : "* an object equals(null) should be false";
    assert c1.equals(sameNameAsC1) : "* equals() for two contacts with equal names returned false!";
    assert sameNameAsC1.equals(c1) : "* equals() for two contacts with equal names returned false!";
    assert !differentNameFromC1.equals(c1) : "equals for two contacts with different names returned true!";
    assert c1.equals(c1) : "* equals() on self returned false!";
    assert !c1.equals(new Object()) : "* equals should return false when argument is not instance of same class!";
    System.out.println("* equals() test for Contact passed.");
  }

  public static void main(String[] args){
    testConstructor("Name Nameson", "email@email.com", "123");
    testConstructor("Bob Bobson", "bob@email.com", "1234");
    testConstructor("Charlie Ceeson", "charlie@email.com", "12345");
    testConstructorWithNullCheck();
    testCompareTo();
    testEquals();
  }
}
