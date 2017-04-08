package com.animalsvsmonsters.tdm.inventories.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Enum storing the damage value for stained glass pain colors.
 * 
 * @author Justis
 *
 */
public enum Pane {

	// You better be able to figure out what each of these mean. :P
	
	WHITE(0),

	ORANGE(1),

	LIGHT_PURPLE(2),

	LIGHT_BLUE(3),

	YELLOW(4),

	LIGHT_GREEN(5),

	PINK(6),

	DARK_GRAY(7),

	LIGHT_GRAY(8),

	AQUA(9),

	PURPLE(10),

	BLUE(11),

	BROWN(12),

	GREEN(13),

	RED(14),

	BLACK(15);

	private short index;

	/**
	 * Choosing a color. ;D
	 * @param damage
	 */
	private Pane(int damage) {
		this.index = ((short) damage);
	}

	/**
	 * Get the damage value necessary for this color.
	 * @return
	 */
	public int damage() {
		return this.index;
	}

	/**
	 * Just get a stained glass pain of this color.
	 * @return Stained glass pain itemstack of this color.
	 */
	public ItemStack getItem() {
		return new ItemStack(Material.STAINED_GLASS_PANE, 1, this.index);
	}

	/**
	 * Um, the material ID for a stained glass pain.
	 * @return
	 */
	public static int id() {
		return 160;
	}
}
