package com.animalsvsmonsters.tdm.inventories.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.tdm.inventories.GUIBase;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;

/**
 * Gui players will use when in the Drone mode.
 * @author Justis
 *
 */
public abstract class DroneGUI extends GUIBase {
	
	// Item positions
	public static final int sword = 0;
	public static final int strike = 1;
	public static final int leave = 8;

	// Create the GUI
	public DroneGUI() {
		super(InventoryType.PLAYER, "Drone controls");
	}

	/**
	 * Set the GUI
	 * @param inv inventory to set it to
	 * @param chicken Is the player disguised as a chicken or a bat? :/ 
	 * Chicken gets eggs, bat gets poop.
	 */
	public static void setGUI(Inventory inv, boolean chicken) {
		inv.clear();
		if (chicken) {
			inv.setItem(0, itemize(new ItemStack(Material.STONE_SWORD), "Peck"));
			inv.setItem(1, itemize(new ItemStack(Material.EGG), "Egg Strike!"));
		} else {
			inv.setItem(0, itemize(new ItemStack(Material.STONE_SWORD), "Bite"));
			inv.setItem(1, itemize(new ItemStack(Material.INK_SACK, 1, (short) 3), "Poop Strike!"));
		}
		inv.setItem(8, itemize(new ItemStack(Material.REDSTONE_BLOCK), "Exit"));
	}

	/**
	 * Set the player's inventory to the GUI, and change their GuiType.
	 * @param inv inventory to set it to
	 * @param chicken Is the player disguised as a chicken or a bat? :/ 
	 * Chicken gets eggs, bat gets poop.
	 */
	public static void setGUI(Player player, boolean chicken) {
		setGUI(player.getInventory(), chicken);
		GuiType.SKILL_DRONE.setType(player);
	}
}
