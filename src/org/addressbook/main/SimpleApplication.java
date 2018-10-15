package org.addressbook.main;

import org.addressbook.storage.*;
import org.addressbook.ui.cli.menu.Menu;
import org.addressbook.ui.cli.menu.MenuAction;
import java.util.Iterator;
import static org.addressbook.textutils.TextUtils.askFor;

/**
 * This class represents the application logic of the address book.
 * It creates the menu for the application, and provides the logic
 * for each menu item.
 *
 * The idea is to allow for the main method for the application 
 * to be kept short and simple, by letting main create an instance
 * of this class, and let the instance do the work of initiating
 * the menu and starting it.
 *
 * You may use this class and complete the createMenu() method
 * if you don't want to write an alternative class from scratch.
 */

public class SimpleApplication{
  private SimpleMutableList<Contact> list;
  private Menu menu = new Menu("Address book");
  /**
   * Constructs a new SimpleApplication
   * with a fresh address book loaded from file
   * if the file exists.
   */
  public SimpleApplication(){
    list = new SimpleAddressBook();
    list.load();
    System.out.println(list.numberOfEntries() +
                       " items loaded from file.");
  }

  private void createMenu(){
    /* Example menu item.
     * This should be replaced by your own menu items.
     */
    menu.addMenuItem("Example", new MenuAction(){
        public void onItemSelected(){
          System.out.println("Remove this item and make your own.");
        }
      });    

    // Your tasks:
    /* 1. Add a menu item for listing all contacts */    

    /* 2. Add a menu item for adding a contact 
     * See above, and MenuExample for the syntax for
     * creating a menu item.
     *
     * Prompt the user for the information for the
     * new contact to be added: name, email and phone.
     *
     * See org.addressbook.textutils.TextUtilsExample 
     * for code examples on how to read a string from
     * the user.
     *
     * Create a new Contact using the strings you
     * get from the user, and call list.addEntry()
     * using the reference to the new Contact as the
     * only argument.
     */
  }

  /**
   * Starts this application - setting up and starting the menu system.
   */
  public void start(){
    createMenu();
    menu.start();
  }
}
