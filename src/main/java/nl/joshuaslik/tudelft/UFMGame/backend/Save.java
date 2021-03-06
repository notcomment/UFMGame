package nl.joshuaslik.tudelft.UFMGame.backend;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import nl.joshuaslik.tudelft.UFMGame.backend.formation.Form343;
import nl.joshuaslik.tudelft.UFMGame.backend.formation.Form4321;
import nl.joshuaslik.tudelft.UFMGame.backend.formation.Form433;
import nl.joshuaslik.tudelft.UFMGame.backend.formation.Form442;
import nl.joshuaslik.tudelft.UFMGame.backend.formation.Form532;
import nl.joshuaslik.tudelft.UFMGame.util.ResourceWrangler;
import nl.joshuaslik.tudelft.UFMGame.util.xml.SAXParser;
import nl.joshuaslik.tudelft.UFMGame.util.xml.XMLFile;
import nl.joshuaslik.tudelft.UFMGame.util.xml.XMLTag;

/**
 * class to save the game
 * 
 * @author Bryan van Wijk
 * @author <a href="http://www.joshuaslik.nl/" target="_blank">Joshua Slik</a>
 *
 */
public class Save {

	/**
	 * Creates new Game object where team is the human and a username of the
	 * person who is playing the game
	 * 
	 * @param team
	 *            is the team you play with
	 * @param username
	 *            is the name the user inserts
	 * @return a game object with the user and pc teams
	 */
	public static Game newGame(Team team, String username) {
		Human human = new Human(team, username, 50000);
		LinkedHashMap<String, Team> teams = loadTeam();
		ArrayList<User> users = new ArrayList<User>();
		users.add(human);
		teams.remove(team.getid());
		int i = 1;
		for (String key : teams.keySet()) {
			users.add(new PC(teams.get(key), "pc" + i, 50000));
			i = i + 1;
		}
		Game game = new Game(users);
		return game;
	}

	/**
	 * load all the basic teams
	 * 
	 * @return a LinkedHashMap with as key String the team names and as value a
	 *         Team Object
	 */
	public static LinkedHashMap<String, Team> loadTeam() {
		ArrayList<String> listofFiles = ResourceWrangler.listResourceFiles("/data/base/teams");
		LinkedHashMap<String, Player> players = loadPlayers();
		LinkedHashMap<String, Team> teams = new LinkedHashMap<String, Team>();
		for (int j = 0; j < listofFiles.size(); j++) {
			XMLFile file = SAXParser.parseFile(listofFiles.get(j));
			String id = file.getElement("TEAM").getAttribute("id");
			String name = file.getElement("TEAM").getAttribute("name");
			String coach = file.getElement("TEAM").getAttribute("coach");
			Team team = new Team(id, name, coach);
			Form343 formation = new Form343(team);
			team.changeFormationType(formation);
			for (int i = 1; i < file.getElement("TEAM.PLAYERS").elements(); i++) {
				if (players.get(file.getElement("TEAM.PLAYERS.PLAYER", i).getAttribute("id")) == null) {
					System.out.println(file.getElement("TEAM.PLAYERS.PLAYER", i).getAttribute("id") + " is niet in the file");
				}
				if (file.getElement("TEAM.PLAYERS.PLAYER", i).getContent("ACTIVE").equals("true")) {
					team.addActivePlayer(players.get(file.getElement("TEAM.PLAYERS.PLAYER", i).getAttribute("id")));
				} else if (file.getElement("TEAM.PLAYERS.PLAYER", i).getContent("ACTIVE").equals("false")) {
					team.addBenchPlayer(players.get(file.getElement("TEAM.PLAYERS.PLAYER", i).getAttribute("id")));
				} else {
					System.out.println("er is een fout in players " + file.getElement("TEAM.PLAYERS.PLAYER", i).getAttribute("id"));
				}
			}

			for (int i = 0; i < team.getActivePlayers().size(); i++) {
				if (team.getActivePlayers().get(i) instanceof Goalkeeper) {
					team.getFormation().setGoalkeeper((Goalkeeper) team.getActivePlayers().get(i));
				} else {
					Fieldplayer fieldplayer = (Fieldplayer) team.getActivePlayers().get(i);
					if (fieldplayer.getPosition().equals("ST")) {
						team.getFormation().setST(fieldplayer);
					} else if (fieldplayer.getPosition().equals("RW")) {
						team.getFormation().setRW(fieldplayer);
					} else if (fieldplayer.getPosition().equals("LW")) {
						team.getFormation().setLW(fieldplayer);
					} else if (fieldplayer.getPosition().equals("RM")) {
						team.getFormation().setRM(fieldplayer);
					} else if (fieldplayer.getPosition().equals("LM")) {
						team.getFormation().setLM(fieldplayer);
					} else if (fieldplayer.getPosition().equals("CM") && formation.getCM1() == null) {
						team.getFormation().setCM1(fieldplayer);
					} else if (fieldplayer.getPosition().equals("CM") && formation.getCM2() == null) {
						team.getFormation().setCM2(fieldplayer);
					} else if (fieldplayer.getPosition().equals("RB")) {
						team.getFormation().setRB(fieldplayer);
					} else if (fieldplayer.getPosition().equals("LB")) {
						team.getFormation().setLB(fieldplayer);
					} else if (fieldplayer.getPosition().equals("CB")) {
						team.getFormation().setCB(fieldplayer);
					}
				}
			}
			teams.put(id, team);
		}
		return teams;
	}

	/**
	 * load all the basic teams
	 * 
	 * @return a arraylist with all the teams objects
	 */
	public static ArrayList<Team> loadTeams() {
		ArrayList<String> listofFiles = ResourceWrangler.listResourceFiles("/data/base/teams");
		LinkedHashMap<String, Player> players = loadPlayers();
		ArrayList<Team> teams = new ArrayList<Team>();
		for (int j = 0; j < listofFiles.size(); j++) {
			XMLFile file = SAXParser.parseFile(listofFiles.get(j));
			String id = file.getElement("TEAM").getAttribute("id");
			String name = file.getElement("TEAM").getAttribute("name");
			String coach = file.getElement("TEAM").getAttribute("coach");
			Team team = new Team(id, name, coach);
			Form343 formation = new Form343(team);
			team.changeFormationType(formation);
			for (int i = 1; i < file.getElement("TEAM.PLAYERS").elements(); i++) {
				if (players.get(file.getElement("TEAM.PLAYERS.PLAYER", i).getAttribute("id")) == null) {
					System.out.println(file.getElement("TEAM.PLAYERS.PLAYER", i).getAttribute("id") + " is niet in the file");
				}
				if (file.getElement("TEAM.PLAYERS.PLAYER", i).getContent("ACTIVE").equals("true")) {
					team.addActivePlayer(players.get(file.getElement("TEAM.PLAYERS.PLAYER", i).getAttribute("id")));
				} else if (file.getElement("TEAM.PLAYERS.PLAYER", i).getContent("ACTIVE").equals("false")) {
					team.addBenchPlayer(players.get(file.getElement("TEAM.PLAYERS.PLAYER", i).getAttribute("id")));
				} else {
					System.out.println("er is een fout in players " + file.getElement("TEAM.PLAYERS.PLAYER", i).getAttribute("id"));
				}
			}

			for (int i = 0; i < team.getActivePlayers().size(); i++) {
				if (team.getActivePlayers().get(i) instanceof Goalkeeper) {
					team.getFormation().setGoalkeeper((Goalkeeper) team.getActivePlayers().get(i));
				} else {
					Fieldplayer fieldplayer = (Fieldplayer) team.getActivePlayers().get(i);
					if (fieldplayer.getPosition().equals("ST")) {
						team.getFormation().setST(fieldplayer);
					} else if (fieldplayer.getPosition().equals("RW")) {
						team.getFormation().setRW(fieldplayer);
					} else if (fieldplayer.getPosition().equals("LW")) {
						team.getFormation().setLW(fieldplayer);
					} else if (fieldplayer.getPosition().equals("RM")) {
						team.getFormation().setRM(fieldplayer);
					} else if (fieldplayer.getPosition().equals("LM")) {
						team.getFormation().setLM(fieldplayer);
					} else if (fieldplayer.getPosition().equals("CM") && formation.getCM1() == null) {
						team.getFormation().setCM1(fieldplayer);
					} else if (fieldplayer.getPosition().equals("CM") && formation.getCM2() == null) {
						team.getFormation().setCM2(fieldplayer);
					} else if (fieldplayer.getPosition().equals("RB")) {
						team.getFormation().setRB(fieldplayer);
					} else if (fieldplayer.getPosition().equals("LB")) {
						team.getFormation().setLB(fieldplayer);
					} else if (fieldplayer.getPosition().equals("CB")) {
						team.getFormation().setCB(fieldplayer);
					}
				}
			}

			teams.add(team);
		}
		return teams;

	}

	/**
	 * Load all the players of the game
	 * 
	 * @return LinkedHashMap with all the players as value and their id as key
	 */
	public static LinkedHashMap<String, Player> loadPlayers() {
		ArrayList<String> listofFiles = ResourceWrangler.listResourceFiles("/data/base/players");
		ArrayList<Player> players = new ArrayList<Player>();
		LinkedHashMap<String, Player> playerslist = new LinkedHashMap<String, Player>();
		for (int i = 0; i < listofFiles.size(); i++) {
			XMLFile file = SAXParser.parseFile(listofFiles.get(i));
			String id = file.getElement("PLAYER").getAttribute("id");
			String last = file.getElement("PLAYER.NAME").getAttribute("last");
			String first = file.getElement("PLAYER.NAME").getAttribute("first");
			String country = file.getContent("PLAYER.COUNTRY");
			String type = file.getContent("PLAYER.TYPE");
			String pos = file.getContent("PLAYER.POS");
			int price = Integer.parseInt(file.getContent("PLAYER.TPRICE"));
			if (type.equals("GK")) {
				int DIV = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("DIV"));
				int POS = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("POS"));
				int REF = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("REF"));
				playerslist.put(id, new Goalkeeper(id, first, last, country, REF, DIV, POS, price));
				players.add(new Goalkeeper(id, first, last, country, REF, DIV, POS, price));

			} else {
				int ATT = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("ATT"));
				int DEF = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("DEF"));
				int STA = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("STA"));
				playerslist.put(id, new Fieldplayer(id, first, last, country, pos, ATT, DEF, STA, price));
				players.add(new Fieldplayer(id, first, last, country, pos, ATT, DEF, STA, price));
			}
		}
		return playerslist;
	}

	/**
	 * Load all the players
	 * 
	 * @return ArrayList with all the players
	 */
	public static ArrayList<Player> loadplayersArrayList() {
		ArrayList<String> listofFiles = ResourceWrangler.listResourceFiles("/data/base/players");
		ArrayList<Player> players = new ArrayList<Player>();

		for (int i = 0; i < listofFiles.size(); i++) {
			XMLFile file = SAXParser.parseFile(listofFiles.get(i));
			String id = file.getElement("PLAYER").getAttribute("id");
			String last = file.getElement("PLAYER.NAME").getAttribute("last");
			String first = file.getElement("PLAYER.NAME").getAttribute("first");
			String country = file.getContent("PLAYER.COUNTRY");
			String type = file.getContent("PLAYER.TYPE");
			String pos = file.getContent("PLAYER.POS");
			int price = Integer.parseInt(file.getContent("PLAYER.TPRICE"));
			if (type.equals("GK")) {
				int DIV = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("DIV"));
				int POS = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("POS"));
				int REF = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("REF"));

				players.add(new Goalkeeper(id, first, last, country, REF, DIV, POS, price));

			} else {
				int ATT = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("ATT"));
				int DEF = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("DEF"));
				int STA = Integer.parseInt(file.getElement("PLAYER.STATS").getContent("STA"));

				players.add(new Fieldplayer(id, first, last, country, pos, ATT, DEF, STA, price));
			}
		}
		return players;
	}

	/**
	 * Complicated save game shit
	 * 
	 * @param game
	 *            game to save
	 * @param slot
	 *            where to save the game
	 */
	public static void saveGame(Game game, int slot) {
		ArrayList<User> userlist = game.getUsers();
		XMLTag root = new XMLTag("savegame");
		// Add info about current round
		XMLTag currentround = new XMLTag("currentround");
		currentround.setContent(Integer.toString(game.getCurrentRound()));
		root.addElement(currentround);
		XMLTag nonContractedPlayers = new XMLTag("noncontractedplayers");
		for (int i = 0; i < game.getNonContractedPlayers().size(); i++) {
			XMLTag player = new XMLTag("player");
			player.addAttribute("id", game.getNonContractedPlayers().get(i).getID());
			nonContractedPlayers.addElement(player);
		}
		root.addElement(nonContractedPlayers);
		// Add info about past playrounds
		XMLTag playrounds = new XMLTag("playrounds");
		for (int i = 0; i < game.getCompetition().getPlayrounds().size(); i++) {
			Playround round = game.getCompetition().getPlayrounds().get(i);
			XMLTag playround = new XMLTag("playround");
			playround.addAttribute("nr", Integer.toString(round.getPlayroundnr()));
			for (int j = 0; j < round.getMatches().size(); j++) {
				Match match = round.getMatches().get(j);
				XMLTag matchtag = new XMLTag("match");

				XMLTag hometeam = new XMLTag("hometeam");
				hometeam.setContent(match.getHomeTeam().getid());
				matchtag.addElement(hometeam);

				XMLTag awayteam = new XMLTag("awayteam");
				awayteam.setContent(match.getAwayTeam().getid());
				matchtag.addElement(awayteam);

				XMLTag winner = new XMLTag("winner");
				if (match.getWinner() != null) {
					winner.setContent(match.getWinner().getid());
				}
				matchtag.addElement(winner);

				XMLTag loser = new XMLTag("loser");
				if (match.getLoser() != null) {
					loser.setContent(match.getLoser().getid());
				}
				matchtag.addElement(loser);

				XMLTag homegoals = new XMLTag("homegoals");
				if (match.getHomegoals() != null) {
					homegoals.setContent(Integer.toString(match.getHomegoals()));
				}
				matchtag.addElement(homegoals);

				XMLTag awaygoals = new XMLTag("awaygoals");
				if (match.getAwaygoals() != null) {
					awaygoals.setContent(Integer.toString(match.getAwaygoals()));
				}

				matchtag.addElement(awaygoals);

				matchtag.addAttribute("nr", Integer.toString(match.getPlayround()));
				playround.addElement(matchtag);
			}
			playrounds.addElement(playround);
		}
		root.addElement(playrounds);
		game.getCompetition().getPlayrounds();

		// Add all users
		XMLTag users = new XMLTag("users");
		for (int i = 0; i < userlist.size(); i++) {
			User user = userlist.get(i);
			LinkedHashMap<String, String> atts = new LinkedHashMap<String, String>();
			atts.put("username", user.getUserName());
			atts.put("budget", Integer.toString(user.getBudget()));
			if (user instanceof Human) {
				atts.put("type", "human");
				// Only save highscore if a match has been played.
				if (game.getCurrentRound() - 1 > 0) {
					saveHighscore(user.getUserName(), user.getTeam().getTotalGoals(), (game.getCurrentRound() - 1));
				}
			} else {
				atts.put("type", "PC");
			}

			// Add team formation
			Team team = user.getTeam();

			LinkedHashMap<String, String> attsteam = new LinkedHashMap<String, String>();
			attsteam.put("coach", team.getCoachName());
			attsteam.put("id", team.getid());
			attsteam.put("name", team.getTeamName());
			XMLTag teamtag = new XMLTag("team", attsteam);

			XMLTag form = new XMLTag("formation");
			form.addAttribute("name", user.getTeam().getFormation().getName());
			if (user.getTeam().getFormation() instanceof Form343) {
				Form343 formation343 = (Form343) user.getTeam().getFormation();
				XMLTag CB = new XMLTag("CB");
				XMLTag LB = new XMLTag("LB");
				XMLTag RB = new XMLTag("RB");
				XMLTag LM = new XMLTag("LM");
				XMLTag RM = new XMLTag("RM");
				XMLTag CM1 = new XMLTag("CM1");
				XMLTag CM2 = new XMLTag("CM2");
				XMLTag LW = new XMLTag("LW");
				XMLTag RW = new XMLTag("RW");
				XMLTag ST = new XMLTag("ST");

				if (formation343.getCB() != null) {
					CB.setContent(formation343.getCB().getID());
				}
				if (formation343.getLB() != null) {
					LB.setContent(formation343.getLB().getID());
				}
				if (formation343.getRB() != null) {
					RB.setContent(formation343.getRB().getID());
				}
				if (formation343.getLM() != null) {
					LM.setContent(formation343.getLM().getID());
				}
				if (formation343.getRM() != null) {
					RM.setContent(formation343.getRM().getID());
				}
				if (formation343.getCM1() != null) {
					CM1.setContent(formation343.getCM1().getID());
				}
				if (formation343.getCM2() != null) {
					CM2.setContent(formation343.getCM2().getID());
				}
				if (formation343.getLW() != null) {
					LW.setContent(formation343.getLW().getID());
				}
				if (formation343.getRW() != null) {
					RW.setContent(formation343.getRW().getID());
				}
				if (formation343.getST() != null) {
					ST.setContent(formation343.getST().getID());
				}
				form.addElement(CB);
				form.addElement(LB);
				form.addElement(RB);
				form.addElement(LM);
				form.addElement(RM);
				form.addElement(CM1);
				form.addElement(CM2);
				form.addElement(LW);
				form.addElement(RW);
				form.addElement(ST);
			}
			if (user.getTeam().getFormation() instanceof Form4321) {
				Form4321 formation4321 = (Form4321) user.getTeam().getFormation();
				XMLTag CB1 = new XMLTag("CB1");
				XMLTag CB2 = new XMLTag("CB2");
				XMLTag LB = new XMLTag("LB");
				XMLTag RB = new XMLTag("RB");
				XMLTag LM = new XMLTag("LM");
				XMLTag RM = new XMLTag("RM");
				XMLTag CM = new XMLTag("CM");
				XMLTag OLM = new XMLTag("OLM");
				XMLTag ORM = new XMLTag("ORM");
				XMLTag ST = new XMLTag("ST");

				if (formation4321.getCB1() != null) {
					CB1.setContent(formation4321.getCB1().getID());
				}
				if (formation4321.getCB2() != null) {
					CB2.setContent(formation4321.getCB2().getID());
				}
				if (formation4321.getLB() != null) {
					LB.setContent(formation4321.getLB().getID());
				}
				if (formation4321.getRB() != null) {
					RB.setContent(formation4321.getRB().getID());
				}
				if (formation4321.getLM() != null) {
					LM.setContent(formation4321.getLM().getID());
				}
				if (formation4321.getRM() != null) {
					RM.setContent(formation4321.getRM().getID());
				}
				if (formation4321.getCM() != null) {
					CM.setContent(formation4321.getCM().getID());
				}
				if (formation4321.getOLM() != null) {
					OLM.setContent(formation4321.getOLM().getID());
				}
				if (formation4321.getORM() != null) {
					ORM.setContent(formation4321.getORM().getID());
				}
				if (formation4321.getST() != null) {
					ST.setContent(formation4321.getST().getID());
				}
				form.addElement(CB1);
				form.addElement(CB2);
				form.addElement(LB);
				form.addElement(RB);
				form.addElement(LM);
				form.addElement(RM);
				form.addElement(CM);
				form.addElement(OLM);
				form.addElement(ORM);
				form.addElement(ST);
			}
			if (user.getTeam().getFormation() instanceof Form433) {
				Form433 formation433 = (Form433) user.getTeam().getFormation();
				XMLTag CB1 = new XMLTag("CB1");
				XMLTag CB2 = new XMLTag("CB2");
				XMLTag LB = new XMLTag("LB");
				XMLTag RB = new XMLTag("RB");
				XMLTag LM = new XMLTag("LM");
				XMLTag RM = new XMLTag("RM");
				XMLTag CM = new XMLTag("CM");
				XMLTag LW = new XMLTag("LW");
				XMLTag RW = new XMLTag("RW");
				XMLTag ST = new XMLTag("ST");

				if (formation433.getCB1() != null) {
					CB1.setContent(formation433.getCB1().getID());
				}
				if (formation433.getCB2() != null) {
					CB2.setContent(formation433.getCB2().getID());
				}
				if (formation433.getLB() != null) {
					LB.setContent(formation433.getLB().getID());
				}
				if (formation433.getRB() != null) {
					RB.setContent(formation433.getRB().getID());
				}
				if (formation433.getLM() != null) {
					LM.setContent(formation433.getLM().getID());
				}
				if (formation433.getRM() != null) {
					RM.setContent(formation433.getRM().getID());
				}
				if (formation433.getCM() != null) {
					CM.setContent(formation433.getCM().getID());
				}
				if (formation433.getLW() != null) {
					LW.setContent(formation433.getLW().getID());
				}
				if (formation433.getRW() != null) {
					RW.setContent(formation433.getRW().getID());
				}
				if (formation433.getST() != null) {
					ST.setContent(formation433.getST().getID());
				}
				form.addElement(CB1);
				form.addElement(CB2);
				form.addElement(LB);
				form.addElement(RB);
				form.addElement(LM);
				form.addElement(RM);
				form.addElement(CM);
				form.addElement(LW);
				form.addElement(RW);
				form.addElement(ST);
			}
			if (user.getTeam().getFormation() instanceof Form442) {
				Form442 formation442 = (Form442) user.getTeam().getFormation();
				XMLTag CB1 = new XMLTag("CB1");
				XMLTag CB2 = new XMLTag("CB2");
				XMLTag LB = new XMLTag("LB");
				XMLTag RB = new XMLTag("RB");
				XMLTag LM = new XMLTag("LM");
				XMLTag RM = new XMLTag("RM");
				XMLTag CM1 = new XMLTag("CM1");
				XMLTag CM2 = new XMLTag("CM2");
				XMLTag LW = new XMLTag("LW");
				XMLTag RW = new XMLTag("RW");

				if (formation442.getCB1() != null) {
					CB1.setContent(formation442.getCB1().getID());
				}
				if (formation442.getCB2() != null) {
					CB2.setContent(formation442.getCB2().getID());
				}
				if (formation442.getLB() != null) {
					LB.setContent(formation442.getLB().getID());
				}
				if (formation442.getRB() != null) {
					RB.setContent(formation442.getRB().getID());
				}
				if (formation442.getLM() != null) {
					LM.setContent(formation442.getLM().getID());
				}
				if (formation442.getRM() != null) {
					RM.setContent(formation442.getRM().getID());
				}
				if (formation442.getCM1() != null) {
					CM1.setContent(formation442.getCM1().getID());
				}
				if (formation442.getCM2() != null) {
					CM2.setContent(formation442.getCM2().getID());
				}
				if (formation442.getLW() != null) {
					LW.setContent(formation442.getLW().getID());
				}
				if (formation442.getRW() != null) {
					RW.setContent(formation442.getRW().getID());
				}
				form.addElement(CB1);
				form.addElement(CB2);
				form.addElement(LB);
				form.addElement(RB);
				form.addElement(LM);
				form.addElement(RM);
				form.addElement(CM1);
				form.addElement(CM2);
				form.addElement(LW);
				form.addElement(RW);
			}
			if (user.getTeam().getFormation() instanceof Form532) {
				Form532 formation532 = (Form532) user.getTeam().getFormation();
				XMLTag CB1 = new XMLTag("CB1");
				XMLTag CB2 = new XMLTag("CB2");
				XMLTag CB3 = new XMLTag("CB3");
				XMLTag LB = new XMLTag("LB");
				XMLTag RB = new XMLTag("RB");
				XMLTag LM = new XMLTag("LM");
				XMLTag RM = new XMLTag("RM");
				XMLTag CM = new XMLTag("CM");
				XMLTag LW = new XMLTag("LW");
				XMLTag RW = new XMLTag("RW");

				if (formation532.getCB1() != null) {
					CB1.setContent(formation532.getCB1().getID());
				}
				if (formation532.getCB2() != null) {
					CB2.setContent(formation532.getCB2().getID());
				}
				if (formation532.getCB3() != null) {
					CB3.setContent(formation532.getCB3().getID());
				}
				if (formation532.getLB() != null) {
					LB.setContent(formation532.getLB().getID());
				}
				if (formation532.getRB() != null) {
					RB.setContent(formation532.getRB().getID());
				}
				if (formation532.getLM() != null) {
					LM.setContent(formation532.getLM().getID());
				}
				if (formation532.getRM() != null) {
					RM.setContent(formation532.getRM().getID());
				}
				if (formation532.getCM() != null) {
					CM.setContent(formation532.getCM().getID());
				}
				if (formation532.getLW() != null) {
					LW.setContent(formation532.getLW().getID());
				}
				if (formation532.getRW() != null) {
					RW.setContent(formation532.getRW().getID());
				}
				form.addElement(CB1);
				form.addElement(CB2);
				form.addElement(CB3);
				form.addElement(LB);
				form.addElement(RB);
				form.addElement(LM);
				form.addElement(RM);
				form.addElement(CM);
				form.addElement(LW);
				form.addElement(RW);
			}
			XMLTag GK = new XMLTag("GK");
			if (user.getTeam().getFormation().getGoalkeper() != null) {
				GK.setContent(user.getTeam().getFormation().getGoalkeper().getID());
			}
			form.addElement(GK);
			teamtag.addElement(form);

			// Add team players
			ArrayList<Player> players = team.getAllPlayers();
			XMLTag playerstag = new XMLTag("players");
			for (int j = 0; j < team.getAllPlayers().size(); j++) {
				Player player = players.get(j);
				XMLTag playertag = new XMLTag("player");
				playertag.addAttribute("id", player.getID());
				playerstag.addElement(playertag);
			}
			teamtag.addElement(playerstag);

			XMLTag teamcaptain = new XMLTag("teamcaptain");
			if (team.getTeamCaptain() != null) {
				teamcaptain.setContent(team.getTeamCaptain().getID());
			}
			teamtag.addElement(teamcaptain);

			XMLTag goals = new XMLTag("totalgoals");
			goals.setContent(Integer.toString(team.getTotalGoals()));
			teamtag.addElement(goals);

			XMLTag goalsagainst = new XMLTag("goalsagainst");
			goalsagainst.setContent(Integer.toString(team.getGoalsAgainst()));
			teamtag.addElement(goalsagainst);

			XMLTag points = new XMLTag("points");
			points.setContent(Integer.toString(team.getPoints()));
			teamtag.addElement(points);

			XMLTag totallosses = new XMLTag("totallosses");
			totallosses.setContent(Integer.toString(team.getTotalLosses()));
			teamtag.addElement(totallosses);

			XMLTag totalwins = new XMLTag("totalwins");
			totalwins.setContent(Integer.toString(team.getTotalWins()));
			teamtag.addElement(totalwins);

			XMLTag totaldraws = new XMLTag("totaldraws");
			totaldraws.setContent(Integer.toString(team.getTotalDraws()));
			teamtag.addElement(totaldraws);

			XMLTag usertag = new XMLTag("user", atts);
			usertag.addElement(teamtag);
			users.addElement(usertag);
		}
		root.addElement(users);

		XMLFile savefile = new XMLFile(root);
		String saveloc = System.getenv("APPDATA") + "\\Ultimate Football Manager\\saves\\slot" + slot + ".xml";
		savefile.save(saveloc);
	}

	/**
	 * Method to save a score of a human user
	 * 
	 * @param username
	 *            score of this user
	 * @param goals
	 *            amount of goals this user made in the past matches
	 * @param matches
	 *            amount of matches that are played
	 */
	public static void saveHighscore(String username, int goals, int matches) {
		BigDecimal bd = new BigDecimal((double) goals / (double) matches);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		Double thisavgGoals = bd.doubleValue();

		Boolean newuser = true;
		XMLTag highscores = new XMLTag("highscores");
		String saveloc = System.getenv("APPDATA") + "\\Ultimate Football Manager\\highscores\\highscores.xml";

		if (new File(saveloc).exists() == false) {

		} else {
			XMLFile file = SAXParser.parseLocalFile(saveloc);
			for (int i = 1; i < file.getElement("highscores").elements() + 1; i++) {
				XMLTag user = new XMLTag("user");
				XMLTag avgGoals = new XMLTag("avggoals");
				if (file.getElement("highscores").getElement("user", i).getAttribute("username").equals(username)) {
					avgGoals.setContent(Double.toString(thisavgGoals));
					user.addAttribute("username", file.getElement("highscores").getElement("user", i).getAttribute("username"));
					newuser = false;
				} else {
					user.addAttribute("username", file.getElement("highscores").getElement("user", i).getAttribute("username"));
					avgGoals.setContent(file.getElement("highscores").getElement("user", i).getElement("avggoals").getContent());
				}
				user.addElement(avgGoals);
				highscores.addElement(user);
			}
		}
		if (newuser) {
			XMLTag user = new XMLTag("user");
			XMLTag avgGoals = new XMLTag("avggoals");
			avgGoals.setContent(Double.toString(thisavgGoals));
			user.addAttribute("username", username);
			user.addElement(avgGoals);
			highscores.addElement(user);
		}
		XMLFile savefile = new XMLFile(highscores);
		savefile.save(saveloc);
	}

	/**
	 * Method to save a option
	 * 
	 * @param optie
	 *            the option to save
	 * @param value
	 *            the value of the option to save
	 */
	public static void saveOption(String optie, String value) {
		XMLTag options = new XMLTag("options");
		String saveloc = System.getenv("APPDATA") + "\\Ultimate Football Manager\\options\\options.xml";

		if (new File(saveloc).exists() == false) {
			XMLFile savefile = new XMLFile(options);
			XMLTag fullscreen = new XMLTag("fullscreen");
			options.addElement(fullscreen);
			XMLTag difficulty = new XMLTag("difficulty");
			options.addElement(difficulty);
			savefile.save(saveloc);
		}
		options = new XMLTag("options");
		XMLFile file = SAXParser.parseLocalFile(saveloc);
		if (optie.equals("fullscreen")) {
			XMLTag fullscreen = new XMLTag("fullscreen");
			fullscreen.setContent(value);
			options.addElement(fullscreen);
			XMLTag difficulty = new XMLTag("difficulty");
			difficulty.setContent(file.getElement("options.difficulty").getContent());
			options.addElement(difficulty);
		}
		if (optie.equals("difficulty")) {
			XMLTag fullscreen = new XMLTag("fullscreen");
			fullscreen.setContent(file.getElement("options.fullscreen").getContent());
			options.addElement(fullscreen);
			XMLTag difficulty = new XMLTag("difficulty");
			difficulty.setContent(value);
			options.addElement(difficulty);
		}

		XMLFile savefile = new XMLFile(options);
		savefile.save(saveloc);
	}

	/**
	 * Method to get a option value
	 * 
	 * @param optie
	 *            the option to get
	 * @return value of the option
	 */
	public static String getOption(String optie) {
		String saveloc = System.getenv("APPDATA") + "\\Ultimate Football Manager\\options\\options.xml";

		if (new File(saveloc).exists() == true) {
			XMLFile file = SAXParser.parseLocalFile(saveloc);
			return file.getElement("options." + optie).getContent();
		}

		return null;
	}

	/**
	 * Method to get the highscores from the highscore file
	 * 
	 * @return username with the highscore
	 */
	public static LinkedHashMap<String, Double> getHighscore() {
		LinkedHashMap<String, Double> highscores = new LinkedHashMap<String, Double>();
		String saveloc = System.getenv("APPDATA") + "\\Ultimate Football Manager\\highscores\\highscores.xml";

		if (new File(saveloc).exists() == true) {
			XMLFile file = SAXParser.parseLocalFile(saveloc);
			for (int i = 1; i < file.getElement("highscores").elements() + 1; i++) {
				highscores.put(file.getElement("highscores").getElement("user", i).getAttribute("username"),
						Double.parseDouble(file.getElement("highscores").getElement("user", i).getElement("avggoals").getContent()));
			}
		}
		return highscores;
	}

	/**
	 * Method to get all the usernames that have a saved game
	 * 
	 * @return LinkedHashMap with the slot number and the string username
	 * */
	public static LinkedHashMap<Integer, String> getUsernames() {
		LinkedHashMap<Integer, String> usernames = new LinkedHashMap<Integer, String>();
		for (int j = 1; j < 4; j++) {
			String saveloc = System.getenv("APPDATA") + "\\Ultimate Football Manager\\saves\\slot" + j + ".xml";
			XMLFile file = null;
			if (new File(saveloc).exists() == true) {
				file = SAXParser.parseLocalFile(saveloc);
				if (file != null) {
					for (int i = 1; i < file.getElement("savegame.users").elements() + 1; i++) {
						if (file.getElement("savegame.users.user", i).getAttribute("type").equals("human")) {
							usernames.put(j, file.getElement("savegame.users.user", i).getAttribute("username"));
						}
					}
				}
			}
		}
		return usernames;
	}

	/**
	 * Load a saved game
	 * 
	 * @param slot
	 *            from where to load a game
	 * @return Game object with all the data in the current slot
	 */
	public static Game loadGame(int slot) {
		LinkedHashMap<String, Player> players = loadPlayers();
		ArrayList<User> users = new ArrayList<User>();
		String saveloc = System.getenv("APPDATA") + "\\Ultimate Football Manager\\saves\\slot" + slot + ".xml";
		if (new File(saveloc).exists() == false) {
			return null;
		}
		XMLFile file = SAXParser.parseLocalFile(saveloc);

		for (int i = 1; i < file.getElement("savegame.users").elements() + 1; i++) {
			Team team = new Team(file.getElement("savegame.users.user", i).getElement("team").getAttribute("id"), file.getElement("savegame.users.user", i).getElement("team").getAttribute("name"),
					file.getElement("savegame.users.user", i).getElement("team").getAttribute("coach"));
			team.addGoals(Integer.parseInt(file.getElement("savegame.users.user", i).getElement("team").getElement("totalgoals").getContent()));
			team.addGoalsAgainst(Integer.parseInt(file.getElement("savegame.users.user", i).getElement("team").getElement("goalsagainst").getContent()));
			team.setTotalDraws(Integer.parseInt(file.getElement("savegame.users.user", i).getElement("team").getElement("totaldraws").getContent()));
			team.setTotalWins(Integer.parseInt(file.getElement("savegame.users.user", i).getElement("team").getElement("totalwins").getContent()));
			team.setTotalLosses(Integer.parseInt(file.getElement("savegame.users.user", i).getElement("team").getElement("totallosses").getContent()));
			team.addPoints(Integer.parseInt(file.getElement("savegame.users.user", i).getElement("team").getElement("points").getContent()));

			for (int j = 1; j < file.getElement("savegame.users.user", i).getElement("team").getElement("players").elements() + 1; j++) {
				team.addBenchPlayer(players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("players").getElement("player", j).getAttribute("id")));
			}
			// if the formation type is 343 load all the players of that
			// formation
			if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getAttribute("name").equals("3-4-3")) {
				Form343 form = new Form343(team);
				team.changeFormationType(form);
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB").getContent() != null) {
					team.getFormation().setCB((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RB").getContent() != null) {
					team.getFormation().setRB((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RB").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LB").getContent() != null) {
					team.getFormation().setLB((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LB").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LM").getContent() != null) {
					team.getFormation().setLM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RM").getContent() != null) {
					team.getFormation().setRM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM1").getContent() != null) {
					team.getFormation().setCM1((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM1").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM2").getContent() != null) {
					team.getFormation().setCM2((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM2").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RW").getContent() != null) {
					team.getFormation().setRW((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RW").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LW").getContent() != null) {
					team.getFormation().setLW((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LW").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("ST").getContent() != null) {
					team.getFormation().setST((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("ST").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("GK").getContent() != null) {
					team.getFormation().setGoalkeeper((Goalkeeper) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("GK").getContent()));
				}
			}
			// if the formation type is 433
			if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getAttribute("name").equals("4-3-3")) {
				Form433 form = new Form433(team);
				team.changeFormationType(form);
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB1").getContent() != null) {
					team.getFormation().setCB1((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB1").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB2").getContent() != null) {
					team.getFormation().setCB2((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB2").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RB").getContent() != null) {
					team.getFormation().setRB((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RB").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LB").getContent() != null) {
					team.getFormation().setLB((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LB").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LM").getContent() != null) {
					team.getFormation().setLM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RM").getContent() != null) {
					team.getFormation().setRM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM").getContent() != null) {
					team.getFormation().setCM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RW").getContent() != null) {
					team.getFormation().setRW((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RW").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LW").getContent() != null) {
					team.getFormation().setLW((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LW").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("ST").getContent() != null) {
					team.getFormation().setST((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("ST").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("GK").getContent() != null) {
					team.getFormation().setGoalkeeper((Goalkeeper) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("GK").getContent()));
				}
			}

			// if the formation type is 4321
			if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getAttribute("name").equals("4-3-2-1")) {
				Form4321 form = new Form4321(team);
				team.changeFormationType(form);
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB1").getContent() != null) {
					team.getFormation().setCB1((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB1").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB2").getContent() != null) {
					team.getFormation().setCB2((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB2").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RB").getContent() != null) {
					team.getFormation().setRB((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RB").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LB").getContent() != null) {
					team.getFormation().setLB((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LB").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LM").getContent() != null) {
					team.getFormation().setLM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RM").getContent() != null) {
					team.getFormation().setRM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM").getContent() != null) {
					team.getFormation().setCM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("ORM").getContent() != null) {
					team.getFormation().setORM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("ORM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("OLM").getContent() != null) {
					team.getFormation().setOLM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("OLM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("ST").getContent() != null) {
					team.getFormation().setST((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("ST").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("GK").getContent() != null) {
					team.getFormation().setGoalkeeper((Goalkeeper) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("GK").getContent()));
				}
			}

			// if the formation type is 442 load all the players of that
			// formation
			if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getAttribute("name").equals("4-4-2")) {
				Form442 form = new Form442(team);
				team.changeFormationType(form);
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB1").getContent() != null) {
					team.getFormation().setCB1((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB1").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB2").getContent() != null) {
					team.getFormation().setCB2((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB2").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RB").getContent() != null) {
					team.getFormation().setRB((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RB").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LB").getContent() != null) {
					team.getFormation().setLB((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LB").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LM").getContent() != null) {
					team.getFormation().setLM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RM").getContent() != null) {
					team.getFormation().setRM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM1").getContent() != null) {
					team.getFormation().setCM1((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM1").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM2").getContent() != null) {
					team.getFormation().setCM2((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM2").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RW").getContent() != null) {
					team.getFormation().setRW((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RW").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LW").getContent() != null) {
					team.getFormation().setLW((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LW").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("GK").getContent() != null) {
					team.getFormation().setGoalkeeper((Goalkeeper) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("GK").getContent()));
				}
			}

			// if the formatin type is 532 load all the players of that
			// formation
			if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getAttribute("name").equals("5-3-2")) {
				Form532 form = new Form532(team);
				team.changeFormationType(form);
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB1").getContent() != null) {
					team.getFormation().setCB1((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB1").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB2").getContent() != null) {
					team.getFormation().setCB2((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB2").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB3").getContent() != null) {
					team.getFormation().setCB3((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CB3").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RB").getContent() != null) {
					team.getFormation().setRB((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RB").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LB").getContent() != null) {
					team.getFormation().setLB((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LB").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LM").getContent() != null) {
					team.getFormation().setLM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RM").getContent() != null) {
					team.getFormation().setRM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM").getContent() != null) {
					team.getFormation().setCM((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("CM").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RW").getContent() != null) {
					team.getFormation().setRW((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("RW").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LW").getContent() != null) {
					team.getFormation().setLW((Fieldplayer) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("LW").getContent()));
				}
				if (file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("GK").getContent() != null) {
					team.getFormation().setGoalkeeper((Goalkeeper) players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("formation").getElement("GK").getContent()));
				}
			}

			if (file.getElement("savegame.users.user", i).getElement("team").getElement("teamcaptain").getContent() != null) {
				team.setTeamCaptain(players.get(file.getElement("savegame.users.user", i).getElement("team").getElement("teamcaptain").getContent()));
			}

			if (file.getElement("savegame.users.user", i).getAttribute("type").equals("PC")) {
				PC pc = new PC(team, file.getElement("savegame.users.user", i).getAttribute("username"), Integer.parseInt(file.getElement("savegame.users.user", i).getAttribute("budget")));
				users.add(pc);
			} else if (file.getElement("savegame.users.user", i).getAttribute("type").equals("human")) {
				Human human = new Human(team, file.getElement("savegame.users.user", i).getAttribute("username"), Integer.parseInt(file.getElement("savegame.users.user", i).getAttribute("budget")));
				users.add(human);
			}
		}

		Game game = new Game(users);
		ArrayList<Playround> playrounds = new ArrayList<Playround>();
		for (int i = 1; i < file.getElement("savegame.playrounds").elements() + 1; i++) {
			Playround playround = new Playround();
			playround.setPlayroundnr(Integer.parseInt(file.getElement("savegame.playrounds.playround", i).getAttribute("nr")));
			for (int j = 1; j < file.getElement("savegame.playrounds.playround", i).elements() + 1; j++) {
				Match match = new Match(game.getTeam(file.getElement("savegame.playrounds.playround", i).getElement("match", j).getElement("hometeam").getContent()), game.getTeam(file
						.getElement("savegame.playrounds.playround", i).getElement("match", j).getElement("awayteam").getContent()));
				if (file.getElement("savegame.playrounds.playround", i).getElement("match", j).getElement("homegoals").getContent() != null) {
					match.setHomegoals(Integer.parseInt(file.getElement("savegame.playrounds.playround", i).getElement("match", j).getElement("homegoals").getContent()));
				}
				if (file.getElement("savegame.playrounds.playround", i).getElement("match", j).getElement("awaygoals").getContent() != null) {
					match.setawaygoals(Integer.parseInt(file.getElement("savegame.playrounds.playround", i).getElement("match", j).getElement("awaygoals").getContent()));
				}
				if (file.getElement("savegame.playrounds.playround", i).getElement("match", j).getElement("winner").getContent() != null) {
					match.setWinner(game.getTeam(file.getElement("savegame.playrounds.playround", i).getElement("match", j).getElement("winner").getContent()));
				}
				if (file.getElement("savegame.playrounds.playround", i).getElement("match", j).getElement("loser").getContent() != null) {
					match.setLoser(game.getTeam(file.getElement("savegame.playrounds.playround", i).getElement("match", j).getElement("loser").getContent()));
				}
				match.setPlayround(Integer.parseInt(file.getElement("savegame.playrounds.playround", i).getElement("match", j).getAttribute("nr")));
				playround.addmatch(match);
			}
			playrounds.add(playround);
		}
		game.getCompetition().setPlayrounds(playrounds);
		game.setCurrentRound(Integer.parseInt(file.getElement("savegame.currentround").getContent()));
		game.computeStandings();
		ArrayList<Player> nonContractedPlayers = new ArrayList<Player>();
		for (int i = 1; i < file.getElement("savegame.noncontractedplayers").elements() + 1; i++) {
			nonContractedPlayers.add(players.get(file.getElement("savegame.noncontractedplayers").getElement("player", i).getAttribute("id")));
		}
		game.setNonContractedPlayers(nonContractedPlayers);
		return game;
	}
}
