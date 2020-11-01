package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * User class that functions mostly as a wrapper class for several albums.
 * This class's data can be serialized, and should be loaded from the file system
 * when a user logs in.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class User implements Serializable {

	/**
	 * User's name.
	 */
	public String name;

	/**
	 * List of the user's albums.
	 */
	public ArrayList<Album> albums;
	
}
