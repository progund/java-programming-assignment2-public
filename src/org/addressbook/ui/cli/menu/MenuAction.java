package org.addressbook.ui.cli.menu;
/**
 * This interface describes the action to be performed
 * when a user selects a menu item associated with
 * an instance implementing this interface.
 *
 * Typically, instances of this interface are created
 * using anonymous inner classes, as seen in the example
 * code in the documentation of {@link Menu}.
 */
public interface MenuAction{
  /**
   * This method will be called from the {@link Menu}
   * when the user selects a menu item associated with
   * an instance of this interface.
   */
  public void onItemSelected();
}
