package org.addressbook.main;

import org.addressbook.storage.*;
import org.addressbook.ui.cli.menu.Menu;
import org.addressbook.ui.cli.menu.MenuAction;
import java.util.Iterator;
import static org.addressbook.textutils.TextUtils.askFor;

public class SimpleApplication{
  private SimpleMutableList<Contact> list;
  private Menu menu = new Menu("Address book");
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
     * get from the user, and call entries.addEntry()
     * using the reference to the new Contact as the
     * only argument.
     */
  }

  public void start(){
    createMenu();
    menu.start();
  }
}
