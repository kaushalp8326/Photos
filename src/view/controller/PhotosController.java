package view.controller;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.statemachine.StateMachine;

/**
 * Abstract wrapper class for all controllers. Ensures that all controllers inherit the same state machine instance.
 * @author John Hoban
 * @author Kaushal Patel
 *
 */
public abstract class PhotosController {
	
	protected static StateMachine stateMachine = StateMachine.getInstance();
	
	/**
	 * A particular controller's corresponding stage.
	 * Each controller gets its own stage.
	 */
	public Stage stage;
	
	/**
	 * Utility method for showing an error dialog.
	 * @param stage Stage from which this dialog is launched.
	 * @param title Dialog title
	 * @param content Dialog caption
	 */
	protected void showErrorDialog(Stage stage, String title, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(stage);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	/**
	 * Utility method for showing a text input dialog.
	 * @param stage Stage from which this dialog is launched.
	 * @param title Dialog title
	 * @param content Dialog caption
	 * @return The string response, or {@code null} if there is none.
	 */
	protected String showInputDialog(Stage stage, String title, String content) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(stage);
		dialog.setTitle(title);
		dialog.setHeaderText("");
		dialog.setContentText(content);
		Optional<String> result = dialog.showAndWait();
		if(result.isEmpty()) {
			return null;
		}
		return result.get();
	}
	
	/**
	 * Utility method for showing a confirmation dialog.
	 * @param stage Stage from which this dialog is launched
	 * @param title Dialog title
	 * @param content Dialog caption
	 * @return The user's response from the dialog.
	 */
	protected ButtonType showConfirmationDialog(Stage stage, String title, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(stage);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(content);
		Optional<ButtonType> response = alert.showAndWait();
		if(!response.isPresent()) {
			return null;
		}
		return response.get();
	}
	
	/**
	 * Utility method for showing a choice dialog.
	 * @param <T> Type contained by {@code choices}
	 * @param stage Stage from which this dialog is launched
	 * @param title Dialog title
	 * @param content Dialog caption
	 * @param choices Set of choices the user will choose from
	 * @return A response of type {@code T}, or {@code null} if there is none.
	 */
	protected <T> T showChoiceDialog(Stage stage, String title, String content, Collection<T> choices) {
		ChoiceDialog<T> dialog = new ChoiceDialog<T>(null, choices);
		dialog.initOwner(stage);
		dialog.setTitle(title);
		dialog.setHeaderText("");
		dialog.setContentText(content);
		Optional<T> choice = dialog.showAndWait();
		if(!choice.isPresent()) {
			return null;
		}
		return choice.get();
	}
	
	/**
	 * Utility method for showing a FileChooser dialog to fetch a photo.
	 * @param stage Stage from which this dialog is launched
	 * @param title Dialog title
	 * @return A {@code File} pointing to the selected image.
	 */
	protected File showImageDialog(Stage stage, String title) {
		FileChooser fc = new FileChooser();
		fc.setTitle(title);
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.bmp", "*.gif", "*.jpg", "*.png"));
		return fc.showOpenDialog(stage);
	}

}
