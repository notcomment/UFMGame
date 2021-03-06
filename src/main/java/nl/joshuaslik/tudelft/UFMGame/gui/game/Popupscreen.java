package nl.joshuaslik.tudelft.UFMGame.gui.game;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import javafx.util.Duration;
import nl.joshuaslik.tudelft.UFMGame.gui.Main;

/**
 * Pop up screen for the username
 * @author Naomi
 *
 */
public class Popupscreen {

	private static Popup popup;
	private static AnchorPane page;
	
	/**
	 * Creates the popup screen and displays it
	 */
	public static void start(){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Class.class
				.getResource("/data/gui/pages-game/Popup.fxml"));
		try {
			page = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FadeTransition ft = new FadeTransition(Duration.millis(900), page);
		ft.setFromValue(0.0);
		ft.setToValue(0.97);
		ft.play();
		popup = new Popup();
		popup.setAutoHide(true);
		popup.getContent().add(page);
		popup.show(Main.stage);
	}
	
	/**
	 * Sets the title of the label
	 * @param input is a string to be the title of this popup
	 */
	public static void setTitle(String input){
		Label changetitle = (Label) page.lookup("#title");
		changetitle.setText(input);
	}
	
	/**
	 * Sets the message for the popup
	 * @param input message to display in the popup
	 */
	public static void setMessage(String input){
		Text text = new Text(input);
	    text.setFill(Color.WHITE);
	    text.setFont(Font.font(20));
	    TextFlow message  = new TextFlow(text);
	    AnchorPane.setTopAnchor(message, 99.0);
	    AnchorPane.setLeftAnchor(message, 0.0);
	    AnchorPane.setRightAnchor(message, 0.0);
		message.setTextAlignment(TextAlignment.CENTER);
	    page.getChildren().add(message);
	}
	
	/**
	 * handles clicking on the ok button
	 */
	@FXML
	protected void handleOK() {
		FadeTransition ft = new FadeTransition(Duration.millis(900), page);
		ft.setFromValue(0.97);
		ft.setToValue(0.0);
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent actionevent) {
		    		popup.hide(); 
		    	}
		 });
	}
}
