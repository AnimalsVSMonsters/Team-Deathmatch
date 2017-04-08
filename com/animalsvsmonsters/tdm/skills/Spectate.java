package com.animalsvsmonsters.tdm.skills;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.animalsvsmonsters.tdm.player.GamePlayer;

/**
 * Spectate skill, allows players to spectate. Obviously. 
 * 
 * TODO: Implement this, I guess? I got you babe - Huli
 * Never mind, I broke it; idk what the fuck I did Justis, plz forgive me
 * @author Justis
 *
 */
public class Spectate extends BaseSkill {
	
	/**
	 * Create this skill for this player
	 * @param id the player
	 */
	public Spectate(GamePlayer id) {
		super(id, SkillTag.SPECTATE);
	}

	/**
	 * Enable spectate mode for this player.
	 */
	public void activate() {
		this.enabled = true;
		Player player = getGamePlayer().getPlayer();
		player.setAllowFlight(true);
		player.setCanPickupItems(false);
		resetPlayer(player);
		player.getInventory().clear();
	}

	/**
	 * Disable spectate mode for this player.
	 */
	public void stopSkill() {
		this.enabled = false;
		Player player = getGamePlayer().getPlayer();
		player.setAllowFlight(false);
		player.setCanPickupItems(true);
		resetPlayer(player);
		getGamePlayer().loadItems();
	}

	/**
	 * Undo any damage to the player, any potion effects, invisibilities, food, fire, etc.
	 */
	private void resetPlayer(Player player) {
		for (Player pl : Bukkit.getServer().getOnlinePlayers())
			pl.showPlayer(player);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.resetMaxHealth();
		player.setFireTicks(0);
		player.setFoodLevel(10);
		player.setHealth(10.0D);
	}

	/**
	 * Nothing at the moment.
	 */
	public void reset() {
	}
}
