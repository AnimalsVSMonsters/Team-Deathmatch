package com.animalsvsmonsters.tdm.inventories.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.tdm.inventories.GUIBase;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;

import net.md_5.bungee.api.ChatColor;

/**
 * The GUI players receieve when they join the server.
 * @author Justis
 *
 */
public abstract class MainGUI extends GUIBase {
	
	// The item name for joining the animal team
	public static final String animal = ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Join Animals";
	// The item name for joining the monster team
	public static final String monster = ChatColor.DARK_AQUA.toString() + ChatColor.ITALIC + "Join Monsters";
	// The item name for leaving the server
	public static final String leave = ChatColor.RED.toString() + ChatColor.ITALIC + "Leave";

	/** 
	 * Create the GUI! DX
	 */
	private MainGUI() {
		super(InventoryType.PLAYER, "GUI");
	}

	/**
	 * Set passed inventory to the GUI inventory.
	 * @param inv
	 */
	public static void setGUI(Inventory inv) {
		inv.clear();
		inv.setItem(0, itemize(new ItemStack(Material.PORK), animal));
		inv.setItem(1, itemize(new ItemStack(Material.ROTTEN_FLESH), monster));
		inv.setItem(8, itemize(new ItemStack(Material.DARK_OAK_DOOR_ITEM), leave));
	}

	/**
	 * Set the player's inventory to the main GUI, and change their GuiType to that of Main.
	 * @param player
	 */
	public static void setGUI(Player player) {
		setGUI(player.getInventory());
		GuiType.MAIN.setType(player);
	}
}
