package com.animalsvsmonsters.tdm.inventories.guis;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.tdm.inventories.GUIBase;

import net.md_5.bungee.api.ChatColor;

/**
 * The GUI for players to choose whether or not they want to leave. 
 * @author Justis
 * 
 * TODO: This GUI currently isn't in use.
 * Please replace backToLobby() methods with this GUI and listen for clicks. 
 *
 */
public class LeaveGUI extends GUIBase {
	
	// Inventory Title
	public static final String title = ChatColor.RED + "Are you sure you want to leave?";

	/**
	 * Create the GUI
	 */
	public LeaveGUI() {
		super(InventoryType.HOPPER, title);
		loadDefaults();
	}

	/**
	 * Load the options into the GUI
	 */
	public void loadDefaults() {
		this.inv.setItem(0, itemize(new ItemStack(Material.REDSTONE_BLOCK), ChatColor.DARK_GRAY + "Yes, leave."));
		this.inv.setItem(4, itemize(new ItemStack(Material.EMERALD_BLOCK), ChatColor.DARK_GRAY + "No, stay."));
	}
}
