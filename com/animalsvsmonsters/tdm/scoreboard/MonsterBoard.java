package com.animalsvsmonsters.tdm.scoreboard;

import org.bukkit.ChatColor;

/**
 * The start of team based boards.
 * TODO: Feel free to go a different way with this.
 * @author Justis
 *
 */
public class MonsterBoard extends Board {
	
	/**
	 * Just for testing purposes
	 */
	public MonsterBoard() {
		super(ChatColor.RED + "Monster");
		setScore("Score:", 5);
	}
}
