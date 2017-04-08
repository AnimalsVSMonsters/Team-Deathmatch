package com.animalsvsmonsters.tdm.player;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.tdm.events.GameInventory;
import com.animalsvsmonsters.tdm.inventories.GUIBase;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;

/**
 * Animal player class.
 * This is the superclass to any animal type player.
 * Handles all the skills and stuff.
 * @author Justis
 *
 */
public abstract class AnimalPlayer extends GamePlayer implements SkilledPlayer {
	
	/**
	 * The gameinventory listener associated with this player. 
	 * @see {@link com.animalsvsmonsters.tdm.events.GameInventory}
	 */
	private GameInventory listener;

	/**
	 * Create an AnimalPlayer with the given ID and EntityType to disguise as.
	 * @param id of the player you're making into an animal player
	 * @param t entity type to disguise the player as
	 */
	protected AnimalPlayer(UUID id, EntityType t) {
		super(id, t);
		this.listener = new GameInventory(this);
		setGUIType(GuiType.GAME_PLAYER);
		loadItems();
	}

	/**
	 * Load in the bow. 
	 * Primary weapon.
	 */
	public void loadPrimary() {
		ItemStack bow = GUIBase.itemize(new ItemStack(Material.BOW), "Primary",
				new Enchantment[] { Enchantment.ARROW_INFINITE });
		getPlayer().getInventory().setItem(0, bow);
		getPlayer().getInventory().setItem(9, new ItemStack(Material.ARROW));
	}

	// Each animal player has three skills.
	public abstract void loadFirstSkill();

	public abstract void loadSecondSkill();

	public abstract void loadThirdSkill();

	/**
	 * Load skill by number, because why not? :P
	 */
	public void loadSkill(int i) {
		if (i == 0)
			loadPrimary();
		else if (i == 1)
			loadFirstSkill();
		else if (i == 2)
			loadSecondSkill();
		else if (i == 3)
			loadThirdSkill();
	}

	/**
	 * Desolve the player. 
	 * Unregister their listeners.
	 */
	public void desolve() {
		this.listener.unregister();
		super.desolve();
	}
}
