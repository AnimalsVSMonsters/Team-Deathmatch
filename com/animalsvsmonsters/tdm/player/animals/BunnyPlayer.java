package com.animalsvsmonsters.tdm.player.animals;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.tdm.inventories.GUIBase;
import com.animalsvsmonsters.tdm.player.AnimalPlayer;
import com.animalsvsmonsters.tdm.skills.DroneSkill;
import com.animalsvsmonsters.tdm.skills.GatlingGun;
import com.animalsvsmonsters.tdm.skills.Hyper;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;

/**
 * Bunny player! Turn into a bunny. Have bunny skills. Join Animal team. Woot.
 * 
 * @author Justis
 *
 */
public class BunnyPlayer extends AnimalPlayer {
	
	/**
	 * Make the player a bunny player.
	 * load their skills.
	 * @param id of the player
	 */
	public BunnyPlayer(UUID id) {
		super(id, EntityType.RABBIT);
		super.addSkill(new GatlingGun(this), 1);
		super.addSkill(new DroneSkill(this, DisguiseType.CHICKEN), 2);
		super.addSkill(new Hyper(this), 3);
	}

	/**
	 * Load the Bow and the skill' items.
	 */
	public void loadItems() {
		loadPrimary();
		loadFirstSkill();
		loadSecondSkill();
		loadThirdSkill();
	}

	/**
	 * Loading the first skill's item. :P
	 */
	public void loadFirstSkill() {
		getPlayer().getInventory().setItem(1, GUIBase.itemize(new ItemStack(Material.DETECTOR_RAIL), "Gatling Gun"));
	}
	
	/**
	 * Loading the second skill's item. :P
	 */
	public void loadSecondSkill() {
		getPlayer().getInventory().setItem(2, GUIBase.itemize(new ItemStack(Material.FEATHER), "Deploy Drone"));
	}
	
	/**
	 * Loading the third skill's item. :P
	 */
	public void loadThirdSkill() {
		getPlayer().getInventory().setItem(3, GUIBase.itemize(new ItemStack(Material.CARROT_ITEM), "Hyper Mode"));
	}

	/**
	 * Desolve the player's data and shet.
	 */
	public void desolve() {
		super.desolve();
	}
}
