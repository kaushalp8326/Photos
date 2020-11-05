package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Container class for Pictures.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class Album implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Album name.
	 */
	private String name;
	
	/**
	 * Instance of the user that owns this album.
	 */
	private User owner;
	
	/**
	 * List of pictures that are contained by this album.
	 */
	public ArrayList<Picture> pictures = new ArrayList<Picture>();
	
	/**
	 * A more direct way of accessing this album's size, rather than referencing {@code this.pictures}.
	 * @return The number of pictures in this album.
	 */
	public int getSize() {
		return pictures.size();
	}
	
	/**
	 * Access this album's name.
	 * @return Album name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Renames this album as long as the new name does not conflict with another one
	 * of this user's albums.
	 * @param name New album name.
	 * @return {@code true} if the name is set successfully, {@code false} otherwise.
	 */
	public boolean setName(String name) {
		for(Album a: owner.albums) {
			if(a.name.equals(name)) {
				return false;
			}
		}
		this.name = name;
		return true;
	}
	
	/**
	 * Find the earliest picture timestamp in this album.
	 * @return LocalDateTime instance representing the earliest timestamp.
	 */
	public LocalDateTime getEarliestTimestamp() {
		if(pictures.size() == 0) {
			return null;
		}
		LocalDateTime earliest = pictures.get(0).getTimestamp();
		for(Picture p : pictures) {
			if(p.getTimestamp().compareTo(earliest) < 0){
				earliest = p.getTimestamp();
			}
		}
		return earliest;
	}
	
	/**
	 * Find the latest picture timestamp in this album.
	 * @return {@code LocalDateTime} instance representing the earliest timestamp.
	 */
	public LocalDateTime getLatestTimestamp() {
		if(pictures.size() == 0) {
			return null;
		}
		LocalDateTime latest = pictures.get(0).getTimestamp();
		for(Picture p : pictures) {
			if(p.getTimestamp().compareTo(latest) > 0){
				latest = p.getTimestamp();
			}
		}
		return latest;
	}
	
	/**
	 * Base constructor for the album. Gives the album a name without adding any pictures.
	 * Doesn't allow for two albums owned by the same user to have the same name.
	 * @param name Name for the album.
	 * @param user The album's owner.
	 */
	protected Album(String name, User owner) throws IllegalArgumentException {
		for(Album a: owner.albums) {
			if(a.name.equals(name)) {
				throw new IllegalArgumentException();
			}
		}
		this.name = name;
		this.owner = owner;
		this.owner.albums.add(this);
	}
	
	/**
	 * Constructor for the album that gives it a name and also adds some pictures.
	 * Most useful when creating an album from a list of search results.
	 * @param name Name for the album.
	 * @param pictures List of pictures that will be added to this album.
	 */
	protected Album(String name, User owner, List<Picture> pictures) throws IllegalArgumentException {
		this(name, owner);
		this.pictures.addAll(pictures);
	}
	
}
