package com.animalsvsmonsters.tdm.player.monsters;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.tdm.inventories.GUIBase;
import com.animalsvsmonsters.tdm.player.MonsterPlayer;
import com.animalsvsmonsters.tdm.skills.DroneSkill;
import com.animalsvsmonsters.tdm.skills.Hyper;
import com.animalsvsmonsters.tdm.skills.MPG;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;

/**
 * Zombie player! Turn into a zombie. Have zombie skills. Join monster team. Woot.
 * 
 * @author Justis
 *
 */
public class ZombiePlayer extends MonsterPlayer {
	
	/**
	 * Make the player a bunny player.
	 * load their skills.
	 * @param id of the player
	 */
	public ZombiePlayer(UUID id) {
		super(id, EntityType.ZOMBIE);
		super.addSkill(new MPG(this), 1);
		super.addSkill(new DroneSkill(this, DisguiseType.BAT), 2);
		super.addSkill(new Hyper(this, false), 3);
	}
	
	/**
	 * Load the bow and skill's items.
	 */
	public void loadItems() {
		loadPrimary();
		loadFirstSkill();
		loadSecondSkill();
		loadThirdSkill();
	}

	/**
	 * Load the first skill's item.
	 */
	public void loadFirstSkill() {
		getPlayer().getInventory().setItem(1, GUIBase.itemize(new ItemStack(Material.ACTIVATOR_RAIL, 3), "MPG"));
	}

	/**
	 * Load the second skill's item.
	 */
	public void loadSecondSkill() {
		getPlayer().getInventory().setItem(2, GUIBase.itemize(new ItemStack(Material.FEATHER), "Deploy Drone"));
	}

	/**
	 * Load the third skill's item.
	 */
	public void loadThirdSkill() {
		getPlayer().getInventory().setItem(3, GUIBase.itemize(new ItemStack(Material.CARROT_ITEM), "Zambie Gamp"));
	}
}