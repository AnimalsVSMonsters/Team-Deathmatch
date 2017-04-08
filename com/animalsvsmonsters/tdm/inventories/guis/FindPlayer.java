package com.animalsvsmonsters.tdm.inventories.guis;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.inventories.GUIBase;
import com.animalsvsmonsters.tdm.inventories.utils.Rows;
import com.animalsvsmonsters.tdm.player.GamePlayer;
import com.google.common.collect.Lists;

import net.md_5.bungee.api.ChatColor;

/**
 * Find Player GUI. For locating other players on the server of the same class.
 * 
 * @author Justis
 *
 */
public class FindPlayer extends GUIBase {
	
	// Inventory location for the arrow {turn page}
	public static final int arrow = 53;
	// Inventory title
	public static final String title = "Find Player";
	// Game player type
	Class<GamePlayer> clazz;
	// page number
	private int page;

	/**
	 * Create the GUI for the player with the given class
	 * @param player
	 */
	@SuppressWarnings("unchecked")
	public FindPlayer(Player player) {
		this((Class<GamePlayer>) AVM.getGame().getPlayer(player).getClass());
	}

	/**
	 * Create the GUI for the player with the given class
	 * @param clazz
	 */
	public FindPlayer(Class<GamePlayer> clazz) {
		super(Rows.SIX, "Find Player");
		this.clazz = clazz;
		loadDefaults();
	}

	/**
	 * Load in the default items.
	 */
	public void loadDefaults() {
		loadPlayers();
	}

	/**
	 * Refresh the current page.
	 */
	public void reloadPage() {
		if (this.page > 1)
			this.page -= 1;
		loadPlayers();
	}

	/**
	 * Load the GUI with all the players of the given class.
	 */
	public void loadPlayers() {
		this.inv.clear();
		List<Player> players = Lists.newArrayList(AVM.getGame().getPlayersByClass(this.clazz));
		int length = 54;
		if (players.size() > 54) {
			length = 52;
			this.inv.setItem(53, itemize(new ItemStack(Material.ARROW), ChatColor.GOLD + "Next Page ->"));
		}
		for (int i = 0; i < length; i++) {
			int pos = 54 * this.page;
			if (players.size() > i + pos) {
				this.inv.setItem(i, playerHead(((Player) players.get(i + pos)).getName()));
			} else {
				this.page = 0;
				return;
			}
		}
		this.page += 1;
	}

	/**
	 * @param name of the player who's head to get
	 * @return the itemstack of the player's head
	 */
	private ItemStack playerHead(String name) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta skull = (SkullMeta) item.getItemMeta();
		skull.setOwner(name);
		skull.setDisplayName(name);
		item.setItemMeta(skull);
		return item;
	}
}
