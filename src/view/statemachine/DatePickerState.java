package view.statemachine;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Album;
import model.Picture;
import view.controller.DateController;

public class DatePickerState extends PhotosState{

	/**
	 * Singleton instance 
	 */
	private static DatePickerState instance = null;
	
	public static DateController dateController;
	
	/**
	 * Prevents instantiation, to implement singleton pattern.
	 */
	private DatePickerState() { 
		// TODO
	}
	
	public void enter() {
		// TODO
		// get FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/fxml/search_by_date.fxml"));
		Parent root;
		try {
			root = loader.load();
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		// get controller
		dateController = loader.getController();
		dateController.start();
		
		// show the window
		Scene mainScene = new Scene(root);
		dateController.stage = new Stage();
		dateController.stage.setTitle("Date Picker");
		dateController.stage.setResizable(false);
		dateController.stage.setScene(mainScene);
		dateController.stage.show();
	}
	
	public PhotosState processEvent() {
		Button b = (Button)lastEvent.getSource();
		
		if(b == dateController.cmdSearch) {
			//TODO
			ArrayList<Picture> pictures=new ArrayList<Picture>();
			for(int i=0; i<HomeState.homeController.lstAlbums.getItems().size(); i++) {
				Album album=HomeState.homeController.lstAlbums.getItems().get(i);
				pictures.addAll(album.getPictures());
			}
			stateMachine.currentAlbum=dateController.findPhotosByDate(pictures);
			return stateMachine.albumState;
			
			
		}else if(b == dateController.cmdCancelSearch) {
			return stateMachine.homeState;
					
		}
		
		return null;
	}
	
	/**
	 * Returns singleton instance.
	 * 
	 * @return Singleton instance
	 */
	public static DatePickerState getInstance() {
		if(instance == null) {
			instance = new DatePickerState();
		}
		return instance;
	}
	
}
