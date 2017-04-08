package com.animalsvsmonsters.tdm.player;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.tdm.inventories.GUIBase;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;

/**
 * NoClass players are players that are neither in the Animal Team nor the Monster team.
 * This class is mainly used for spectating, and as such, I've added some default spectating functionality/API.
 * @author Justis
 *
 */
public final class NoClassPlayer extends GamePlayer {
	
	// The two item names for the spectating GUI
	// TODO: Should probably use my own GUI for this, and not use this generic player class.
	public static final String SPEC_COMPASS = ChatColor.GRAY + "Find Player";
	public static final String SPEC_EXIT = ChatColor.GRAY + "Stop Spectating";

	/**
	 * Create the no-class player, set the GUIType to whatever you need it to be.
	 * If the GUIType is spectating, load in all the spectating GUI stuff, if not, do nothing with the GUI.
	 * @param id
	 * @param t
	 */
	public NoClassPlayer(UUID id, GuiType t) {
		super(id, EntityType.PLAYER);
		setGUIType(t);
		loadItems();
	}

	/**
	 * This class has a default spectating GUI, here.
	 * This can be avoided by simply not using the "SPECTATE" GUI type. Set it to something else.
	 */
	public void loadItems() {
		switch (getGUIType()) {
		case SPECTATE:
			getPlayer().setAllowFlight(true);
			Inventory inv = getPlayer().getInventory();
			inv.setItem(0, GUIBase.itemize(new ItemStack(Material.COMPASS), SPEC_COMPASS));
			inv.setItem(8, GUIBase.itemize(new ItemStack(Material.ACACIA_DOOR_ITEM), SPEC_EXIT));
			break;
		default:
		}
	}

	/**
	 * Remove player's ability to fly and further desolve the player.
	 */
	public void desolve() {
		getPlayer().setAllowFlight(false);
		getPlayer().setFlying(false);
		super.desolve();
	}
}