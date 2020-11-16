package view.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Album;
import model.Picture;
import model.User;

/**
 * Controller for the date picker screen.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public class DateController extends PhotosController {

	// FXML entities
	@FXML public DatePicker datStartDateField;
	@FXML public DatePicker datEndDateField;
	
	@FXML public Button cmdSearch;
	@FXML public Button cmdCancelSearch;
		
	/**
	 * 
	 */
	public void start() {
		//TODO
		return;
	}
	
	public Album findPhotosByDate(ArrayList<Picture> pictures) {
		HashSet<Picture> results=new HashSet<Picture>();
		
		LocalDate startDate=datStartDateField.getValue();
		if(startDate==null) {
			startDate=LocalDate.MIN;
		}
		LocalDate endDate=datEndDateField.getValue();
		if(endDate==null) {
			endDate=LocalDate.MAX;
		}
		LocalDateTime dateStart=LocalDateTime.of(startDate, LocalTime.MIN);
		LocalDateTime dateEnd=LocalDateTime.of(endDate, LocalTime.MAX);
		for(Picture pic:pictures) {
			if((pic.getTimestamp().isEqual(dateStart) || pic.getTimestamp().isAfter(dateStart)) && (pic.getTimestamp().isBefore(dateEnd) || pic.getTimestamp().isEqual(dateEnd))) {
				results.add(pic);
			}
		}
		
		Album searchResults=new Album("Search Results", results);
		return searchResults;
	}
	
	@FXML private void processEvent(Event e) {
		stateMachine.processEvent(e);
	}
	
}
