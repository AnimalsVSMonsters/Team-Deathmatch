package com.animalsvsmonsters.tdm.scoreboard;

import org.bukkit.ChatColor;

/**
 * The start of team based boards.
 * TODO: Feel free to go a different way with this.
 * @author Justis
 *
 */
public class AnimalBoard extends Board {
	
	/**
	 * Just for testing purposes
	 */
	public AnimalBoard() {
		super(ChatColor.BLUE + "Animal");
		setScore("Score:", 5);
	}
}
