package com.animalsvsmonsters.tdm.skills;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;

import com.animalsvsmonsters.tdm.inventories.GUIBase;
import com.animalsvsmonsters.tdm.inventories.utils.ActionBar;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;
import com.animalsvsmonsters.tdm.inventories.utils.Pane;
import com.animalsvsmonsters.tdm.player.GamePlayer;
import com.animalsvsmonsters.tdm.player.SkilledPlayer;

/**
 * A rocket launcher like gun that shoots explosive fireballs.
 * 3 missiles per reload.
 * Player walks slower while holding the item, due to it's weight.
 * @author Justis
 *
 */
public class MPG extends EquipableSkill {
	
	// How many missiles available?
	private int missiles;

	/**
	 * Create this skill for this player
	 * @param id this player
	 */
	public MPG(GamePlayer id) {
		super(id, SkillTag.MPG, 15);
		reloadMissiles();
	}

	/**
	 * Shoot the fireball, decrease the amount of missiles remaining by 1.
	 * Update the item.
	 * 
	 * If there are no missiles left, start the cooldown for reloading.
	 */
	public void activate() {
		if (this.missiles < 1)
			return;
		this.enabled = true;
		Player player = getGamePlayer().getPlayer();
		Fireball fb = (Fireball) player.launchProjectile(SmallFireball.class);
		fb.setDirection(player.getLocation().getDirection());
		fb.setShooter(player);
		this.missiles -= 1;
		updateItem();
		if (this.missiles == 0)
			resetTime();
	}

	/**
	 * Reload the missiles, end the cooldown.
	 */
	public void end() {
		reloadMissiles();
		super.end();
	}

	/**
	 * Reload the missiles.
	 * Update the items.
	 */
	private void reloadMissiles() {
		this.missiles = 3;
		updateItem();
		this.enabled = false;
	}

	/**
	 * @see {@link #reloadMissiles()} 
	 * @see {@link #end()} 
	 */
	public void stopSkill(boolean restart) {
		reloadMissiles();
		end();
	}

	/**
	 * Player walks slower when the MPG is equip'd do to weight.
	 */
	public void equip() {
		super.equip();
		getGamePlayer().getPlayer().setWalkSpeed(0.1F);
	}

	/**
	 * Restore the player's walk speed. 
	 * Clear their action bar.
	 */
	public void unequip() {
		super.unequip();
		ActionBar.sendActionBar(getGamePlayer().getPlayer(), "");
		getGamePlayer().getPlayer().setWalkSpeed(0.2F);
	}

	/**
	 * Update the amount of missiles in the player'sinventory.
	 * If the 
	 */
	public void updateItem() {
		if (getGamePlayer().getGUIType() == GuiType.GAME_PLAYER) {
			int slot = getGamePlayer().getSlot(getTag()).intValue();
			if (this.missiles > 0) {
				if ((getGamePlayer() instanceof SkilledPlayer)) {
					((SkilledPlayer) getGamePlayer()).loadSkill(getSlot());
					if (this.missiles == 3)
						((SkilledPlayer) getGamePlayer()).loadSkill(slot);
					else
						getGamePlayer().getPlayer().getInventory().getItem(slot)
								.setAmount(this.missiles == 0 ? -1024 : this.missiles);
					// This should never be run because the missiles were just checked in the previous if statement.
					// I kept this here as a reminder of the choices we have.
					// Either a yellow glass pane, or make the itemstack say 0.
				}
			} else
				getGamePlayer().getPlayer().getInventory().setItem(slot, GUIBase.itemize(Pane.YELLOW.getItem(), " "));
		}
	}

	/**
	 * Nothing currently.
	 */
	public void reset() {
	}
}