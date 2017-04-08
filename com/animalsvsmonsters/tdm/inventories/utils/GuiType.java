package com.animalsvsmonsters.tdm.inventories.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.tdm.AVM;

/**
 * All the GuiTypes a player can have.
 * Used for determining which GUI each player has in their inventory.
 * @author Justis
 *
 */
public enum GuiType {
	
	/**
	 * The inventory a player receives when the join the server.
	 * Consists of the JoinAnimals JoinMonsters and Leave items.
	 */
	MAIN,

	/**
	 * The inventory of a player who has joined a team.
	 * Consists of a bow and their skill items.
	 */
	GAME_PLAYER,

	/**
	 * The inventory of a player who has died.
	 * TODO: Make an inventory? :/ Idk. 
	 */
	DIED,

	/**
	 * The inventory of a player who is currently in spectator mode.
	 * Consists of a compass for locating players and whatever else.
	 */
	SPECTATE,

	/**
	 * The inventory of a player who is currently using the skill "DRONE".
	 * Consists of a sword, and drone specific skills.
	 */
	SKILL_DRONE,
	
	/**
	 * The inventory of someone freed from any sort of GUI.
	 * Contents are independant from this plugin.
	 */
	NONE;

	/**
	 * Get the GUIType of a player.
	 * @param player
	 * @return The player's GUIType, as specified in their GamePlayer wrapper.
	 */
	public static GuiType getType(Entity player) {
		if (!(player instanceof Player))
			return null;
		return AVM.getGame().getPlayer((Player) player).getGUIType();
	}

	/**
	 * Set the player's GUIType to selected GUIType.
	 * @param player
	 */
	public void setType(Entity player) {
		if (!(player instanceof Player))
			return;
		AVM.getGame().getPlayer((Player) player).setGUIType(this);
	}

	/**
	 * Is the player locked in a GUI? 
	 * @param player
	 * @return true if the player is locked in a GUI.
	 */
	public static boolean isLocked(Entity player) {
		return getType(player) != NONE;
	}

	/**
	 * Is the player freed from a GUI?
	 * @param player
	 * @return true if the player is free from any GUI.
	 */
	public static boolean isUnlocked(Entity player) {
		return getType(player) == NONE;
	}

	/**
	 * Is the player able to be damaged while in their current GUIType?
	 * @param player
	 * @return true if they can be damaged.
	 */
	public static boolean isDamageable(Entity player) {
		switch (getType(player)) {
		case MAIN:
		case DIED:
		case SPECTATE:
			return false;
		case GAME_PLAYER:
		case SKILL_DRONE:
		case NONE:
		}
		return true;
	}

	/**
	 * Get the GUIType from a string.
	 * @param string
	 * @return the GUIType associated with the string.
	 * Null if the string doesn't match the GUIType.
	 */
	public static GuiType fromString(String string) {
		for (GuiType t : values()) {
			if (t.toString().equalsIgnoreCase(string))
				return t;
		}
		return null;
	}
}
