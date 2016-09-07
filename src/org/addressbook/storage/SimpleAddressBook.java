package org.addressbook.storage;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import java.util.Iterator;

/**
 * This class is an implemenation of {@link SimpleMutableList}
 * and typed for {@link Contact} elements.
 *
 * For storage of the address book, it will use a file with
 * serialized {@link Contact} objects. The file will be created
 * (or assumed to exist) in <code>$HOME/.address_book</code>
 * (where $HOME is the user's home directory).
 *
 * If there are any exceptions thrown during file I/O, these will
 * be logged to <code>$HOME/.address_book.log</code> for reference.
 */
public class SimpleAddressBook implements SimpleMutableList<Contact>{
  
  private static final String LOG_FILE =
    System.getProperty("user.home") +
    System.getProperty("file.separator") +
    ".address_book.log";
    

  private static final String ADDRESS_FILE = 
    System.getProperty("user.home") +
    System.getProperty("file.separator") +
    ".address_book";
  
  private List<Contact> entries;
  /**
   * Creates a new SimpleAddressBook with an empty list of {@link Contact} elements.
   */
  public SimpleAddressBook(){
    entries = new ArrayList<>();
  }
  
  public int numberOfEntries(){
    return entries.size();
  }

  public void listEntries(){
    List<Contact> copy = new ArrayList<>(entries);
    Collections.sort(copy);
    for(Contact c : copy){
      System.out.println(c);
    }
  }

  public void addEntry(Contact c){
    System.out.println("You must implement addEntry(Contact c)");
    System.out.println("In class org.addressbook.storage.SimpleAddressBook");
  }

  public boolean contains(Contact c){
    return entries.contains(c);
  }


  /*
   * Log exception in the application logfile.
   *
   * This method exists so that we can log exceptions
   * for the developers to investigate. This allows us
   * to hide the Java specific texts of the exceptions
   * from the user and show the users some meaningful
   * text instead.
   */
  private void logException(Exception e){
    try{
        e.printStackTrace
          (new PrintWriter(new FileWriter(LOG_FILE),true));
    }catch(Exception logErr){
      System.err.print("Could not log exception: ");
      System.err.println(e.getMessage());
      logErr.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  public void load(){
    try{
      if (!new File(ADDRESS_FILE).exists()){
        System.out.println("INFO: There is no address book file.");
        return;
      }
      ObjectInputStream in = 
        new ObjectInputStream(new FileInputStream
                              (ADDRESS_FILE));
      Contact c;
      entries = (List<Contact>)in.readObject();
      in.close();
    }catch(Exception e){
      // Write a user friendly error message,
      // log the exception, and, 
      // rethrow the exception as a RuntimeException
      // to be handle by the Main class.
      System.err.println("Could not load address book");
      logException(e);
      throw new RuntimeException("Your address book is corrupted.");
    }
  }

  public void save(){
    try{
      System.out.println("Saving in " + ADDRESS_FILE + "...");
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ADDRESS_FILE));
      out.writeObject(entries);
      out.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
