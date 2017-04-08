package com.animalsvsmonsters.tdm.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * The start of team based boards.
 * TODO: Feel free to go a different way with this.
 * @author Justis
 *
 */
public abstract class Board {
	
	private Scoreboard board;
	private Objective obj;

	/**
	 * Again, this was just here for testing.
	 * Feel free to go another way.
	 * @param team
	 */
	public Board(String team) {
		this.board = Bukkit.getScoreboardManager().getNewScoreboard();
		this.obj = this.board.registerNewObjective("AVM", "dummy");
		this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.obj.setDisplayName("Custom board goes here.");
	}

	public Scoreboard board() {
		return this.board;
	}

	public void setScore(String name, int score) {
		this.obj.getScore(name).setScore(score);
	}
}
