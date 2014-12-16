package nl.joshuaslik.UFMReckoning.gui.game;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import nl.joshuaslik.UFMReckoning.gui.Main;
import nl.joshuaslik.UFMReckoning.gui.MainMenu;
import nl.joshuaslik.UFMReckoning.gui.Ranking;

/**5
 * @author Naomi de Ridder
 * @author <a href="http://www.joshuaslik.nl/" target="_blank">Joshua Slik</a>
 *
 */
public class Competition {


	@SuppressWarnings("restriction")
	public static void start() throws IOException {
		Parent root = FXMLLoader.load(Class.class
				.getResource("/data/gui/Competition.fxml"));
		Scene scene = new Scene(root, 1080, 1920);
		Main.stage.setScene(scene);
		Main.stage.setFullScreen(true);
	}
	
	@FXML
	protected void handleShowNextGame(ActionEvent event) throws IOException {
		System.out.println("btn_ShowNextGame pressed");
		System.out.println(event.getSource());
		ShowNextGame.start();
	}
	
	@FXML
	protected void handleRanking(ActionEvent event) throws IOException {
		System.out.println("btn_Ranking pressed");
		System.out.println(event.getSource());
		Ranking.start();
	}
	
	@FXML
	protected void handleReturnMainMenu(ActionEvent event) throws IOException {
		System.out.println("btn_Return pressed");
		System.out.println(event.getSource());
		MainMenu.start();
	}
	
	@FXML
	protected void handleAllGames(ActionEvent event) throws IOException {
		System.out.println("btn_AllGames pressed");
		System.out.println(event.getSource());
		SeeAllGames.start();
	}
	
	/*When pushing this button a new game has to start. 
	 * 
	 * @FXML
	protected void handlePlayNextRound(ActionEvent event) throws IOException {
		System.out.println("btn_PlayNextRound pressed");
		System.out.println(event.getSource());
		PlayNextRound.start();
	}*/
}
