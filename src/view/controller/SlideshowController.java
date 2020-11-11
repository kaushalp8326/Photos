package view.controller;

import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.Picture;

public class SlideshowController extends PhotosController {
	
	// FXML entities
	@FXML public Button cmdFirstPhoto;
	@FXML public Button cmdPrevPhoto;
	@FXML public Button cmdNextPhoto;
	@FXML public Button cmdLastPhoto;
	
	@FXML public Label lblPhotoIndex;
	@FXML public Label lblCaption;
	@FXML public Label lblDateCaptured;
	@FXML public Label lblTags;
	
	@FXML public ImageView imgPhoto;
	
	// Current "slide" in the slideshow
	public int slide;

	/**
	 * Initialize the slideshow to show the first photo in the album.
	 */
	public void start() {
		slide = 0;
		showPhoto();
	}
	
	/**
	 * Update the window given the current slide.
	 */
	public void showPhoto() {
		Picture pic = stateMachine.currentAlbum.getPictures().get(slide);
		imgPhoto.setImage(pic.getImage());
		lblCaption.setText("Caption: " + pic.caption);
		lblDateCaptured.setText("Date Captured: " + pic.getTimestamp()
													.format(DateTimeFormatter.ofPattern("YYYY-MM-dd, h:mm:ss a")));
		lblTags.setText("Tags: "); // TODO
		lblPhotoIndex.setText((slide+1) + " of " + stateMachine.currentAlbum.getSize());
		if(slide == 0) {
			cmdFirstPhoto.setDisable(true);
			cmdPrevPhoto.setDisable(true);
		}else {
			cmdFirstPhoto.setDisable(false);
			cmdPrevPhoto.setDisable(false);
		}
		if(slide == stateMachine.currentAlbum.getSize() - 1) {
			cmdNextPhoto.setDisable(true);
			cmdLastPhoto.setDisable(true);
		}else {
			cmdNextPhoto.setDisable(false);
			cmdLastPhoto.setDisable(false);
		}
	}
	
	@FXML private void processEvent(ActionEvent e) {
		stateMachine.processEvent(e);
	}
	
}
