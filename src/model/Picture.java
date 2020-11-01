package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

/**
 * Class that stores a picture and its relevant data.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class Picture {

	private String path;
	
	private Image image;
	
	public String caption;
	
	// public ArrayList<T> tags;
	// public HashMap<T,U> tags;
	// TODO do we want a Tag class?
	
	// private T timestamp;
	// TODO idk what type to use here
	
	/**
	 * Picture constructor. Loads the image from the file system given a path.
	 */
	public Picture(String path) throws FileNotFoundException {
		this.path = path;
		this.image = new Image(new FileInputStream(path)); // possible FNFE here
		long time = new File(path).lastModified(); // and here
		// this.timestamp = somefunction(time);
	}
	
	/**
	 * Fetch this picture's path.
	 * @return The path as a string.
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Fetch this picture's Image representation.
	 * @return This picture's Image.
	 */
	public Image getImage() {
		return image;
	}
	
}
