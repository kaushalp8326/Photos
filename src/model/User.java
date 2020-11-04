package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	// Data file path
	public String path;
	
	/**
	 * User's name.
	 */
	public String name;

	/**
	 * List of the user's albums.
	 */
	public ArrayList<Album> albums;
	
	/**
	 * Create a new user by name. Doesn't allow two users with the same name on the same machine.
	 * @param name Username
	 * @throws IllegalArgumentException if a user with this name already exists.
	 * @throws IOException if there is a problem creating the save file.
	 */
	public User(String name) throws IllegalArgumentException, IOException {
		this.name = name;
		this.path = "user/" + name + ".dat";
		File f = new File(path);
		if(f.exists()) {
			throw new IllegalArgumentException();
		}
		f.createNewFile(); // possible IOException here
		save();			   // and here
	}
	
	/**
	 * Save this user's data to the filesystem via serialization.
	 * @throws IOException if user save file is missing.
	 */
	public void save() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
		oos.writeObject(this);
		oos.close();
	}
	
	/**
	 * Load a user object from a data file, given the name of the desired user.
	 * @param name Username.
	 * @return A user object.
	 * @throws ClassNotFoundException if the classpath for this class is not found
	 * @throws IOException if the user save file can't be accessed
	 */
	public static User load(String name) throws ClassNotFoundException, IOException {
		String path = "user/" + name + ".dat";
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
		User u = (User)ois.readObject();
		ois.close();
		return u;
	}
	
}
