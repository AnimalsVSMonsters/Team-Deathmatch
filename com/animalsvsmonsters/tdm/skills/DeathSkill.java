package com.animalsvsmonsters.tdm.skills;

import org.bukkit.Location;

import com.animalsvsmonsters.tdm.player.GamePlayer;

/**
 * The death skill is very special. 
 * Only game players that can die have this skill.
 * Future plans include adding a fake body on the ground.
 * The player will then be free to fly around the map spectating until they are revived.
 * This will be used to detect when a player wishes to revive a dead player.
 * 
 * TODO: Implement functionality.
 * 
 * @author Justis
 *
 */
public class DeathSkill extends BaseSkill {
	
	/**
	 * Create the death skill for this player
	 * @param id player to create the death skill for.
	 */
	public DeathSkill(GamePlayer id) {
		super(id, SkillTag.DEATH);
	}

	/**
	 * Activate the death skill.
	 * TODO: Add the deathskill functionality
	 * Remember that the player doesn't ever actually die
	 */
	public void activate() {
	}
	
	/**
	 * Get the location of the player's dead body disguise.
	 * @return the guy's location
	 * TODO: Implement this shet.
	 */
	public Location getDisguiseLocation() {
		return null;
	}

	/**
	 * Reset the player, derp. :P
	 * TODO: Implement this shet as well.
	 */
	public void reset() {
	}
}