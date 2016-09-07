package org.addressbook.storage;
import java.util.Iterator;

/**
 * This interface describes a simple mutable list
 * (a collection) of entries. Objects of this type can 
 * perform a few basic operations like giving the number
 * of collected entries, listing the entries to Standard out,
 * adding an entry to the collection, saving the collection (for
 * instance to a file) and loading the collection (for instance
 * from a file).
 *
 * The interface is generic, allowing you to create an implementation
 * for a specific class. See the implementation we have provided
 * you, for an example on how this interface can be used.
 */
public interface SimpleMutableList<E>{
  /**
   * Returns the number of entries currently in the list.
   * @return The number of entries currently in the list
   */
  public int numberOfEntries();
  
  /**
   * Lists the entries to Standard out.
   */
  public void listEntries();
  
  /**
   * Let's you add a new entry to the list.
   * @param entry A reference to the entry to be added to the list
   */
  public void addEntry(E entry);

  /**
   * Saves this list. It is up to the implementation to decide on
   * how and where to save the list. However, save() should correspond
   * to load(), in the sense that they should be considered symetric
   * so that a load uses the same resource as save.
   */
  public void save();
  
  /**
   * Loads the list. It is up to the implementation to decide on
   * how and from where to load the list. However, load() should
   * correspond to save(), in the sense that they should be considered
   * symetric so that a save() saves to the same resource used by
   * load().
   */
  public void load();

  public boolean contains(E entry);
}
