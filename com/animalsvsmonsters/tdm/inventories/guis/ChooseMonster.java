package com.animalsvsmonsters.tdm.inventories.guis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.tdm.inventories.GUIBase;

/**
 * GUI for choosing a monster class
 * 
 * @author Justis
 *
 */
public class ChooseMonster extends GUIBase {
	
	// Gui Title
	public static final String title = "Choose a monster class";
	// Item names
	public static final String ender = ChatColor.DARK_AQUA + "Enderman";
	public static final String skelly = ChatColor.DARK_AQUA + "Skeleton";
	public static final String zombie = ChatColor.DARK_AQUA + "Zombie";

	/**
	 * Create the GUI!
	 */
	public ChooseMonster() {
		super(InventoryType.HOPPER, "Choose a monster class");
		loadDefaults();
	}

	/**
	 * Load in the defaults. 
	 */
	public void loadDefaults() {
		this.inv.setItem(0, itemize(new ItemStack(Material.ENDER_PEARL), ender,
				new String[] { "- EnderSpy", "- EnderWeed", "- HealBeam" }));
		this.inv.setItem(2, itemize(new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.SKELETON.ordinal()), skelly,
				new String[] { "- BoneBom", "- GoldenMaiden", "- LandMine" }));
		this.inv.setItem(4, itemize(new ItemStack(Material.ROTTEN_FLESH), zombie,
				new String[] { "- ZPG", "- BatDrone", "- ZombieGamp" }));
	}
}
