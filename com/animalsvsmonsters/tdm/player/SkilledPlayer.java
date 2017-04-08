package com.animalsvsmonsters.tdm.player;

/**
 * This is for players that have skills
 * There are three skills for each player, and a primary bow.
 * 
 * @author Justis
 *
 */
public abstract interface SkilledPlayer {
	
	public abstract void loadFirstSkill();

	public abstract void loadSecondSkill();

	public abstract void loadThirdSkill();

	// Because why not? :P
	public abstract void loadSkill(int paramInt);
}
