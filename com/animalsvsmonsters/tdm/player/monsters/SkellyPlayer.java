package com.animalsvsmonsters.tdm.player.monsters;

import java.util.UUID;

import org.bukkit.entity.EntityType;

import com.animalsvsmonsters.tdm.player.MonsterPlayer;

/**
 * Turn this player into a skeleton. Have skelly skills. Join Monster team. Woot.
 * 
 * @author Justis
 *
 */
public class SkellyPlayer extends MonsterPlayer {
	
	/**
	 * Make this player a skelly player!
	 * TODO: Load in the skills for this type.
	 * @param id of the player
	 */
	public SkellyPlayer(UUID id) {
		super(id, EntityType.SKELETON);
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
