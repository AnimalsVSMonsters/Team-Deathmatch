package com.animalsvsmonsters.tdm.game;

import org.bukkit.Bukkit;

import com.animalsvsmonsters.tdm.player.AnimalPlayer;
import com.animalsvsmonsters.tdm.player.GamePlayer;
import com.animalsvsmonsters.tdm.player.MonsterPlayer;
import com.animalsvsmonsters.tdm.scoreboard.AnimalBoard;
import com.animalsvsmonsters.tdm.scoreboard.MonsterBoard;

/**
 * 
 * @author Justis The default game type, currently the only one implemented.
 */
public class AVMGame extends Game {

	// Wasn't sure how to implement scoreboards exactly, but currently we're
	// gunna go with one score board per team instead of per player?
	private AnimalBoard ab;
	private MonsterBoard mb;

	/**
	 * Just loads the scoreoards up for both the teams.
	 */
	public void loadBoards() {
		this.ab = new AnimalBoard();
		this.mb = new MonsterBoard();
	}

	/**
	 * @return animal scoreboard
	 */
	public AnimalBoard getAnimalBoard() {
		return this.ab;
	}

	/**
	 * @return monster scoreboard
	 */
	public MonsterBoard getMonsterBoard() {
		return this.mb;
	}

	/**
	 * Sets a player's scorebaord.
	 */
	public void setBoard(GamePlayer player) {
		if ((player instanceof AnimalPlayer))
			player.getPlayer().setScoreboard(this.ab.board());
		else if ((player instanceof MonsterPlayer))
			player.getPlayer().setScoreboard(this.mb.board());
		else
			player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}