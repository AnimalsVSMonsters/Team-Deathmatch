package com.animalsvsmonsters.tdm.inventories;

import com.animalsvsmonsters.tdm.inventories.utils.Rows;
import com.google.common.collect.Lists;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A nice little abstract class to handle the backend creation of all my inventories and stuff.
 * 
 * @author Justis
 *
 */
public abstract class GUIBase {
	
	// The inventory. ^.- Duh. 
	protected Inventory inv;

	/**
	 * Create an inventory with 
	 * @param row this many rows
	 * @param title and this title
	 */
	public GUIBase(Rows row, String title) {
		this.inv = Bukkit.createInventory(null, row.getSpaces(), title);
	}

	/**
	 * Create an inventory of
	 * @param type this type and
	 * @param title this string title
	 */
	public GUIBase(InventoryType type, String title) {
		this.inv = Bukkit.createInventory(null, type, title);
	}

	/**
	 * @return the raw inventory
	 */
	public Inventory getInventory() {
		return this.inv;
	}

	public abstract void loadDefaults();

	/**
	 * Add a display name to this item
	 * @param item to add display name to
	 * @param name to add to the item
	 * @return item with the given name
	 */
	public static ItemStack itemize(ItemStack item, String name) {
		return itemize(item, name, null, null);
	}

	/**
	 * Add a displayname and enchantments to an itemstack
	 * @param item to add the displayname and enchants to
	 * @param name to add to the itemstack
	 * @param en enchants to add to the itemstack
	 * @return itemstack with the given displayname and enchants on it
	 */
	public static ItemStack itemize(ItemStack item, String name, Enchantment[] en) {
		return itemize(item, name, en, null);
	}

	/**
	 * Add a displayname and lore to an itemstack
	 * @param item to add displayname and lore to
	 * @param name to add to the displayname
	 * @param lore to add to the itemstack
	 * @return itemstack with the name and lore added to it.
	 */
	public static ItemStack itemize(ItemStack item, String name, String[] lore) {
		return itemize(item, name, null, lore);
	}

	/**
	 * Add a displayname, a lore, and enchants to an itemstack.
	 * @param item to add displayname, lore, and enchants to.
	 * @param name to add to the item
	 * @param en enchants to add to the itme
	 * @param lore to add to the item
	 * @return itemstack with the displayname, lore, and enchants to it.
	 */
	public static ItemStack itemize(ItemStack item, String name, Enchantment[] en, String[] lore) {
		ItemMeta meta = item.getItemMeta();
		if (name != null)
			meta.setDisplayName(name);
		if ((en != null) && (en.length > 0))
			for (Enchantment e : en)
				meta.addEnchant(e, 1, false);
		if ((lore != null) && (lore.length > 0))
			meta.setLore(Lists.newArrayList(lore));
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Remove flags from an itemstack. (enchants in the lore and stuff)
	 * @param stack to remove flags from
	 * @return itemstack with the flags removed.
	 */
	public ItemStack removeFlags(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		meta.removeItemFlags(ItemFlag.values());
		stack.setItemMeta(meta);
		return stack;
	}
}
