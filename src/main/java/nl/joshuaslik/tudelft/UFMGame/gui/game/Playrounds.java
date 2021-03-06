package nl.joshuaslik.tudelft.UFMGame.gui.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.joshuaslik.tudelft.UFMGame.backend.Match;
import nl.joshuaslik.tudelft.UFMGame.backend.Playround;
import nl.joshuaslik.tudelft.UFMGame.gui.Main;

/**
 * Class to control the playrounds
 * @author Naomi
 * @author Bryan
 *
 */
public class Playrounds {

	private static int round;

	@FXML
	private TableView<Match> competitiontable;
	@FXML
	private ComboBox<Playround> playround;
	@FXML
	private TableColumn<Match, String> home, away, homegoals, awaygoals;

	/**
	 * Initializes the playrounds
	 */
	@FXML
	private void initialize() {
		ObservableList<Playround> playroundnr = FXCollections
				.observableArrayList(getPlayroundList());
		round = MainGame.game.getCurrentRound();

		playround.setItems(playroundnr);
		playround.setConverter(new StringConverter<Playround>() {
			@Override
			public String toString(Playround playround) {
				return "Playround: " + playround.getPlayroundnr();
			}

			@Override
			public Playround fromString(String nr) {
				return null;
			}
		});

		playround.valueProperty().addListener(new ChangeListener<Playround>() {
			@Override
			public void changed(
					ObservableValue<? extends Playround> observable,
					Playround oldValue, Playround newValue) {
				round = newValue.getPlayroundnr();
				ObservableList<Match> matches = FXCollections
						.observableArrayList(getMatchList());
				competitiontable.setItems(matches);

			}
		});

		playround.setValue(MainGame.game.getPlayround(round));

		ObservableList<Match> matches = FXCollections
				.observableArrayList(getMatchList());
		competitiontable.setItems(matches);

		home.setCellValueFactory(new PropertyValueFactory<Match, String>(
				"hometeam"));
		away.setCellValueFactory(new PropertyValueFactory<Match, String>(
				"awayteam"));
		homegoals.setCellValueFactory(new PropertyValueFactory<Match, String>(
				"homegoals"));
		awaygoals.setCellValueFactory(new PropertyValueFactory<Match, String>(
				"awaygoals"));

		competitiontable
				.setRowFactory(new Callback<TableView<Match>, TableRow<Match>>() {
					@Override
					public TableRow<Match> call(TableView<Match> param) {
						return new TableRow<Match>() {
							@Override
							protected void updateItem(Match item, boolean empty) {
								super.updateItem(item, empty);
								if (getItem() != null
										&& getItem().contains(
												MainGame.game.getUser()
														.getTeam())) {
									setStyle("-fx-background-color: brown");
								} else {
									setStyle("");
								}
							}
						};
					}
				});

	}

	/**
	 * returns the goals
	 * @param goals to return
	 * @return int of the goals
	 */
	public Integer getgoals(int goals) {
		if (goals != -1) {
			return goals;
		}
		return null;
	}

	/**
	 * Loads the playrounds page
	 * @throws IOException is thrown if the FXML file cannot be parsed.
	 */
	public static void start() throws IOException {
		AnchorPane scene = (AnchorPane) FXMLLoader.load(Class.class
				.getResource("/data/gui/pages-game/Playrounds.fxml"));
		Main.setCenter(scene);
		AnchorPane bottom = (AnchorPane) FXMLLoader.load(Class.class
				.getResource("/data/gui/pages-game/GameBottomMenuBar.fxml"));
		Main.setBottom(bottom);
	}

	/**
	 * Method to get a observable list of all the mathces of a playround
	 * @return a observablelist with all mathces of a round
	 */
	public static ObservableList<Match> getMatchList() {
		Playround pr = MainGame.game.getCompetition().getPlayround(round);
		ArrayList<Match> matches = new ArrayList<Match>();
		matches.addAll(pr.getMatches());

		ObservableList<Match> data = FXCollections.observableArrayList(matches);
		return data;
	}

	/**
	 * Method to get a observable list of all the playrounds
	 * @return a observable list of all the playrounds in this game
	 */
	public static ObservableList<Playround> getPlayroundList() {
		ArrayList<Playround> pr = MainGame.game.getCompetition()
				.getPlayrounds();
		Collections.sort(pr);
		ObservableList<Playround> data = FXCollections.observableArrayList(pr);
		return data;
	}

}