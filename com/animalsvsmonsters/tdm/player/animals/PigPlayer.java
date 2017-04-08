package com.animalsvsmonsters.tdm.player.animals;

import java.util.UUID;

import org.bukkit.entity.EntityType;

import com.animalsvsmonsters.tdm.player.AnimalPlayer;

/**
 * Turn this player into a pig. Have pig skills. Join Animal team. Woot.
 * 
 * @author Justis
 *
 */
public class PigPlayer extends AnimalPlayer {
	
	/**
	 * Make this player a pig player!
	 * TODO: Load in the skills for this type.
	 * @param id of the player
	 */
	public PigPlayer(UUID id) {
		super(id, EntityType.PIG);
	}

	/**
	 * TODO: Load in this guy's items for the skills that need to be registered.
	 */
	public void loadItems() {
	}

	/**
	 * TODO: Load this skill item
	 */
	public void loadFirstSkill() {
	}
	
	/**
	 * TODO: Load this skill item
	 */
	public void loadSecondSkill() {
	}
	
	/**
	 * TODO: Load this skill item
	 */
	public void loadThirdSkill() {
	}
}
