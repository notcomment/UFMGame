package nl.joshuaslik.tudelft.UFMGame.backend.formation;

import nl.joshuaslik.tudelft.UFMGame.backend.Fieldplayer;
import nl.joshuaslik.tudelft.UFMGame.backend.Team;

/**
 * Formation met 4-4-2 where CB is Central Back<br>
 * RB is Right back player<br>
 * LB is Left back player<br>
 * CM is Central midfield player<br>
 * LM is left midfield player<br>
 * RM is right midfield player<br>
 * LW is left wing player<br>
 * RW is right wing player<br>
 * ORM is offensive right midfield<br>
 * OLM is offensive left midfield<br>
 * ST is striker<br>
 * 
 * @author Bryan van Wijk
 * 
 */

public class Form4321 extends Formation {

	private Fieldplayer CB1;
	private Fieldplayer CB2;
	private Fieldplayer RB;
	private Fieldplayer LB;
	private Fieldplayer CM;
	private Fieldplayer LM;
	private Fieldplayer RM;
	private Fieldplayer OLM;
	private Fieldplayer ORM;
	private Fieldplayer ST;
	private String Name = "4-3-2-1";

	/**
	 * Method to get the name of this formation type
	 * @return the name of this formation
	 */
	public String getName() {
		return Name;
	}

	/**
	 * Contructor of a formation 4321 object
	 * @param team the team of this formation
	 */
	public Form4321(Team team) {
		super(team);
	}

	/**
	 * Method to get the central back player 1
	 * @return central back player 1
	 */
	public Fieldplayer getCB1() {
		return CB1;
	}

	/**
	 * method to get the central back player 2
	 * @return central back player 2
	 */
	public Fieldplayer getCB2() {
		return CB2;
	}

	/**
	 * 
	 * @return left back player
	 */
	public Fieldplayer getLB() {
		return LB;
	}

	/**
	 * method to get the right back player
	 * @return right back player
	 */
	public Fieldplayer getRB() {
		return RB;
	}

	/**
	 * method to get the central midfield player
	 * @return central midfield player
	 */
	public Fieldplayer getCM() {
		return CM;
	}

	/**
	 * method to get the right midfield player
	 * @return right midfield player
	 */
	public Fieldplayer getRM() {
		return RM;
	}

	/**
	 * Method to get the left midfield player
	 * @return Left midfield player
	 */
	public Fieldplayer getLM() {
		return LM;
	}

	/**
	 * method to get the offensive left midfield player
	 * @return offensive left midfield player
	 */
	public Fieldplayer getOLM() {
		return OLM;
	}

	/**
	 * method to get the left wing player
	 * @return Left wing player
	 */
	public Fieldplayer getORM() {
		return ORM;
	}

	/**
	 * 
	 * @return the striker
	 */
	public Fieldplayer getST() {
		return ST;
	}

	/**
	 * Set the Centralback 1
	 * 
	 * @param CB1
	 *            new CB1
	 * @return old CB1
	 */
	public Fieldplayer setCB1(Fieldplayer CB1) {
		Fieldplayer tmp = this.CB1;
		if (tmp != null) {
			team.setPlayerBench(tmp);
		}
		this.CB1 = CB1;
		team.setPlayerActive(CB1);
		return tmp;
	}

	/**
	 * Set the Centralback 2
	 * 
	 * @param CB2
	 *            new CB1
	 * @return old CB1
	 */
	public Fieldplayer setCB2(Fieldplayer CB2) {
		Fieldplayer tmp = this.CB2;
		if (tmp != null) {
			team.setPlayerBench(tmp);
		}
		this.CB2 = CB2;
		team.setPlayerActive(CB2);
		return tmp;
	}

	/**
	 * Set the rightback
	 * 
	 * @param RB
	 *            new RB
	 * @return old RB
	 */
	public Fieldplayer setRB(Fieldplayer RB) {
		Fieldplayer tmp = this.RB;
		if (tmp != null) {
			team.setPlayerBench(tmp);
		}
		this.RB = RB;
		team.setPlayerActive(RB);
		return tmp;
	}

	/**
	 * Set the leftback
	 * 
	 * @param LB
	 *            new LB
	 * @return old LB
	 */
	public Fieldplayer setLB(Fieldplayer LB) {
		Fieldplayer tmp = this.LB;
		if (tmp != null) {
			team.setPlayerBench(tmp);
		}
		this.LB = LB;
		team.setPlayerActive(LB);
		return tmp;
	}

	/**
	 * Set the Central midfield
	 * 
	 * @param CM
	 *            new CM
	 * @return old CM
	 */
	public Fieldplayer setCM(Fieldplayer CM) {
		Fieldplayer tmp = this.CM;
		if (tmp != null) {
			team.setPlayerBench(tmp);
		}
		this.CM = CM;
		team.setPlayerActive(CM);
		return tmp;
	}

	/**
	 * Set the left midfield
	 * 
	 * @param LM
	 *            new LM
	 * @return old LM
	 */
	public Fieldplayer setLM(Fieldplayer LM) {
		Fieldplayer tmp = this.LM;
		if (tmp != null) {
			team.setPlayerBench(tmp);
		}
		this.LM = LM;
		team.setPlayerActive(LM);
		return tmp;
	}

	/**
	 * Set the right midfield
	 * 
	 * @param RM
	 *            new RM
	 * @return old RM
	 */
	public Fieldplayer setRM(Fieldplayer RM) {
		Fieldplayer tmp = this.RM;
		if (tmp != null) {
			team.setPlayerBench(tmp);
		}
		this.RM = RM;
		team.setPlayerActive(RM);
		return tmp;
	}

	/**
	 * Set the offensive left midfield
	 * 
	 * @param OLM
	 *            new OLM
	 * @return old OLM
	 */
	public Fieldplayer setOLM(Fieldplayer OLM) {
		Fieldplayer tmp = this.OLM;
		if (tmp != null) {
			team.setPlayerBench(tmp);
		}
		this.OLM = OLM;
		team.setPlayerActive(OLM);
		return tmp;
	}

	/**
	 * Set the offensive right midfield
	 * 
	 * @param ORM
	 *            new ORM
	 * @return old ORM
	 */
	public Fieldplayer setORM(Fieldplayer ORM) {
		Fieldplayer tmp = this.ORM;
		if (tmp != null) {
			team.setPlayerBench(tmp);
		}
		this.ORM = ORM;
		team.setPlayerActive(ORM);
		return tmp;
	}

	/**
	 * Set the striker
	 * 
	 * @param ST
	 *            new ST
	 * @return old ST
	 */
	public Fieldplayer setST(Fieldplayer ST) {
		Fieldplayer tmp = this.ST;
		if (tmp != null) {
			team.setPlayerBench(tmp);
		}
		this.ST = ST;
		team.setPlayerActive(ST);
		return tmp;
	}

}