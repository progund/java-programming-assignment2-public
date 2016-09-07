package org.addressbook.storage;
import java.io.Serializable;

/**
 * This class describes a Contact (for instance an entry in an
 * address book). The class implements Serializable (so that
 * Contact instances can be saved) and Comparable (so that
 * Contact instances in a list can be sorted).
 *
 * Contact instances are immutable, which means that you cannot change
 * the state of an instance of Contact. Every call to methods for
 * changing an attribute of a Contact, such as changeName(), will
 * result in a new Contact with a state representing the old Contact
 * but with a new value for (in this case) name.
 */

public final class Contact implements Serializable, Comparable<Contact>{

  /* There's no point in initializing instance variables to null
   * but we do it here, so that the empty constuctor compiles
   */
  private final String name=null;
  private final String email=null;
  private final String phone=null;
  
  /**
   * Constructs a new Contact, using the arguments for the state.
   * @param name The name of this Contact (a name is a simple String with both
   * first name and last name)
   * @param email The email address of this Contact
   * @param phone The phone number of this Contact
   */
  public Contact(String name, String email, String phone){
    /*
     * Constructor, first version:
     * Save the parameters in the variables! Run the
     * Test to see if it worked.
     */

    /* 
     * Constructor second version:
     * Now, just saving the parameters is not enough.
     * Start the constructor with an if-statement
     * to see if name is null. If so, throw a NullPointerException.
     * Next, write an if-statement checking if the name equals "".
     * If so, throw an IllegalArgumentException.
     * Next, save name in the instance variable.
     * Next, check if email is null, if so save "" in the
     * instance variable.
     * Next, check if phone is null, if so save "" in the
     * instance variable.
     * Done! Run the test again to see if also the constructor
     * with null tests pass!
     */
  }

  /**
   * Returns the name of this contact, as a reference to a String
   * @return the name of this contact
   */
  public String name() { return name; }

  /**
   * Returns the email of this contact, as a reference to a String
   * @return the email of this contact
   */
  public String email(){ return email; }

  /**
   * Returns the phone number of this contact, as a reference to a String
   * @return the phone number of this contact
   */
  public String phone(){ return phone; }
  
  /**
   * Changes the name of this Contac by returning a new Contact reflecting this change
   */
  public Contact changeName(String newName){
    return new Contact(newName, email, phone);
  }

  /**
   * Changes the email of this Contac by returning a new Contact reflecting this change
   */
  public Contact changeEmail(String newEmail){
    return new Contact(name, newEmail, phone);
  }
  
  /**
   * Changes the phone number of this Contac by returning a new Contact reflecting this change
   */
  public Contact changePhone(String newPhone){
    return new Contact(name, email, newPhone);
  }
  
  /**
   * Returns a String representation of this Contact on the form "Name Email Phone"
   * @return A reference to a String representing this Contact
   */
  @Override
  public String toString(){
    return name + " " + email + " " + phone; 
  }
  
  /**
   * Compares this Contact with another Contact lexicograhpically, using only the name for comparison.
   * See java.lang.String for an explanation on how Strings are compared.
   */
  @Override
  public int compareTo(Contact other){
    // TODO: Implement a correct version of compareTo().
    // You should only care about the name, so use the compareTo in java.lang.String
    // on the name variables only.
    return 0;
  }
  
  /**
   * Compares this Contact to the Specified object. The result is true if and only if the argument is not null
   * and is a Contact object that represents the same name as this Contact.
   * @return true if the given object represents a Contact with exactly the same name as this object, false otherwise
   */
  @Override
  public boolean equals(Object other){
    /* TODO:
     * Implement a proper version of equals() where
     * you only care about whether the name is equal to
     * the name of the other object (everything else may differ).
     * 
     * These are the steps:
     * 1. if other is null or not an instance of Contact,
     *    return false.
     * 2. Cast other to a Contact reference and
     *    use the version of equals in java.lang.String on the
     *    name variables of both objects.
     */
    return false;
  }

  /**
   * Returns a hashcode for this Contact, using only the name of this contact.
   * @return a hashcode for this Contact.
   */
  @Override
  public int hashCode(){
    return name.hashCode();
  }
}
