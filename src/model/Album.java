package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Container class for Pictures.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class Album {

	/**
	 * Album name.
	 */
	public String name;
	
	/**
	 * List of pictures that are contained by this album.
	 */
	public ArrayList<Picture> pictures = new ArrayList<Picture>();
	
	/**
	 * A more direct way of accessing this album's size, rather than referencing {@code this.pictures}.
	 */
	public int getSize() {
		return pictures.size();
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
	 * @return LocalDateTime instance representing the earliest timestamp.
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
	 * @param name Name for the album.
	 */
	public Album(String name) {
		this.name = name;
	}
	
	/**
	 * Constructor for the album that gives it a name and also adds some pictures.
	 * Most useful when creating an album from a list of search results.
	 * @param name Name for the album.
	 * @param pictures List of pictures that will be added to this album.
	 */
	public Album(String name, List<Picture> pictures) {
		this(name);
		this.pictures.addAll(pictures);
	}
	
}
