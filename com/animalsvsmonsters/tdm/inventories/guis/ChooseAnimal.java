package com.animalsvsmonsters.tdm.inventories.guis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.tdm.inventories.GUIBase;

/**
 * 
 * @author Justis 
 * 
 * GUI for choosing an Animal class
 */
public class ChooseAnimal extends GUIBase {

	// GUI Title
	public static final String title = "Choose an animal class";
	// Item names
	public static final String bunny = ChatColor.LIGHT_PURPLE + "Rabbit";
	public static final String pig = ChatColor.LIGHT_PURPLE + "Pig";
	public static final String wolf = ChatColor.LIGHT_PURPLE + "Wolf";

	/**
	 * Create the inventory.
	 */
	public ChooseAnimal() {
		super(InventoryType.HOPPER, "Choose an animal class");
		loadDefaults();
	}

	/**
	 * Load the inventory defaults, derp.
	 */
	public void loadDefaults() {
		this.inv.setItem(0, itemize(new ItemStack(Material.RABBIT_HIDE), bunny,
				new String[] { "- BunnyGatling", "- ChickenDrone", "- Hyper" }));
		this.inv.setItem(2, itemize(new ItemStack(Material.PORK), pig,
				new String[] { "- BaconBomb", "- IronMaiden", "- LandMine" }));
		this.inv.setItem(4, itemize(new ItemStack(Material.BONE), wolf,
				new String[] { "- InvisaWolf", "- SpikeWeed", "- HealBeam" }));
	}
}
