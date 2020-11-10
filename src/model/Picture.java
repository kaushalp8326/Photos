package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
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
public class Picture implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String path;
	
	private transient Image image;
	
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
		long time = new File(path).lastModified(); // possible FNFE here
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
	 * Fetch this picture's {@code Image} representation. Since {@code Image} isn't serializable, the {@code Image} gets
	 * loaded from the file system when it's first accessed, but is stored inside the class until the end of the session.
	 * @return This picture's {@code Image}.
	 */
	public Image getImage() {
		if(image == null) {
			try {
				this.image = new Image(new FileInputStream(path));
			}catch(FileNotFoundException e) {
				e.printStackTrace(); // should not occur, since you can't have a picture with an invalid path
			}
		}
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
