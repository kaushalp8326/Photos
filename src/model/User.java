package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * User class that functions mostly as a wrapper class for several albums.
 * This class's data can be serialized, and should be loaded from the file system
 * when a user logs in.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Data file path
	 */
	public String path;
	
	/**
	 * User's name.
	 */
	private String name;

	/**
	 * List of the user's albums. Hidden outside this package.
	 */
	protected ArrayList<Album> albums;
	
	/**
	 * Unique tag names this user has used.
	 */
	public HashSet<String> uniqueTags=new HashSet<String>();
	
	/**
	 * Get this user's username.
	 * @return The username.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get a read-only copy of this user's albums. The albums themselves are mutable,
	 * but adds and deletes are not allowed.
	 * @return An unmodifiable {@code List} instance containing each album this user owns.
	 */
	public List<Album> getAlbums(){
		return List.copyOf(albums);
	}
	
	/**
	 * Creates a new album for this user. A user cannot own two albums with the same name.
	 * @param name Album name.
	 * @throws IllegalArgumentException if an existing album has this name.
	 * @return Instance of the new Album.
	 */
	public Album createAlbum(String name) throws IllegalArgumentException {
		Album a = new Album(name, this);
		return a;
	}
	
	/**
	 * Deletes an album from the user's collection.
	 * @param album The album to be deleted.
	 * @return {@code true} if delete was successful, {@code false} otherwise.
	 */
	public boolean deleteAlbum(Album album) {
		return albums.remove(album);
	}
	
	/**
	 * Create a new user by name. Doesn't allow two users with the same name on the same machine.
	 * @param name Username
	 * @throws IllegalArgumentException if a user with this name already exists.
	 * @throws IOException if there is a problem creating the save file.
	 */
	public User(String name) throws IllegalArgumentException, IOException {
		this.name = name;
		this.path = "user/" + name + ".dat";
		this.albums = new ArrayList<Album>();
		File f = new File(path);
		if(f.exists()) {
			throw new IllegalArgumentException();
		}
		try {
			f.createNewFile();
			save();
		}catch(IOException e) {
			throw new IOException();
		}
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
