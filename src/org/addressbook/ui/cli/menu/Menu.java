package org.addressbook.ui.cli.menu;
import java.util.List;
import java.util.ArrayList;

public class Menu{
  
  /* The menu has a list of menu items */
  private List<MenuItem> items;
  /* The menu has a title */
  private String title;


  /* The only way to create a menu object:
   * provide a title.
   */
  public Menu(String title){
    // Initialize the list
    items=new ArrayList<>();
    // Save the title
    this.title = title;
  }

  /* This is part of the public API of the menu object:
   * A way to add a menu item to the menu.
   */
  public void addMenuItem(String prompt, MenuAction action){
    // Add this text and action to the list of menu items
    items.add(new MenuItem(prompt, action));
  }

  /* This is part of the public API of the menu object:
   * a way to start the menu (displaying the options and
   * reacting to the user's selection.
   */
  public void start(){
    while(true){
      System.out.println("\n===="+title+"====\n");
      try{
        int index=0; // Used as the number connected to the item
        int reply=0; // Used to hold the user's reply (choice)

        /* Print a number as the option and the prompt of every item */
        for(MenuItem m : items){
          System.out.println(index++ + " " + m.prompt);
        }
        // Add an option for quitting (this would be the last number)
        System.out.println(index + " quit");
        // Prompt the user for a selection (a number from the list)
        System.out.print("Please enter a number from the menu: ");
        // Read the number from the user
        reply = Integer.parseInt(System.console().readLine());
        // If the user wants to quit, then return from this method
        if (reply==index) {
          System.out.println("\nBye!\n");
          // Quit the menu
          return;
        }
        // Give feedback on the selection and run the action of the
        // corresponding menu item
        System.out.println("\nYou selected "+items.get(reply).prompt+"\n");
        items.get(reply).action.onItemSelected();

        // Deal with bad input (illegal number or non-numeric response)
      }catch(NumberFormatException | IndexOutOfBoundsException e){
        System.out.println("\n\n\n>>>Invalid choice, please try again.<<<");
        System.out.println(">>>Your selection must be a number from the menu.<<<");
      }
    }
  }
  /* 
   * Inner class wrapping a menu item as an object
   * with a prompt (text of the item) and an action
   * to be performed when the user selects this item.
   * 
   * An inner class is convenient when you need a class
   * but no one else needs to know about it.
   *
   * Making it private is an effective way to hide it from
   * everyone.
   */
  
  private class MenuItem{
    private String prompt;
    private MenuAction action;
    public MenuItem(String prompt, MenuAction action){
      this.prompt = prompt;
      this.action = action;
    }
  }
  
}
