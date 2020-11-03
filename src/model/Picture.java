package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

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
	
	private LocalDateTime timestamp;
	
	/**
	 * Picture constructor. Loads the image from the file system given a path.
	 */
	public Picture(String path) throws FileNotFoundException {
		this.path = path;
		this.image = new Image(new FileInputStream(path)); // possible FNFE here
		long time = new File(path).lastModified(); // and here
		this.timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
	}
	
	/**
	 * Fetch this picture's path.
	 * @return The path as a string.
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Fetch this picture's {@code Image} representation.
	 * @return This picture's {@code Image}.
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * Fetch this picture's timestamp.
	 * @return Timestamp as a {@code LocalDateTime} object.
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
}
