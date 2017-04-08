package com.animalsvsmonsters.tdm.skills;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.inventories.GUIBase;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;
import com.animalsvsmonsters.tdm.inventories.utils.Pane;
import com.animalsvsmonsters.tdm.player.GamePlayer;
import com.animalsvsmonsters.tdm.player.SkilledPlayer;

import me.libraryaddict.disguise.disguisetypes.MobDisguise;

/**
 * This skill turns the player into a baby version of themselves, and they can then run and jump extremely fast and high for a short period of time.
 * 
 * @author Justis
 *
 */
public class Hyper extends EquipableSkill {
	
	// Should the hyper disguise be an adult or baby?
	private boolean adult;

	
	/**
	 * Creates a new hyper skill for player.
	 * Will default to adult.
	 * @param id
	 */
	public Hyper(GamePlayer id) {
		this(id, true);
	}
	
	/**
	 * Creates new hyper skill for player.
	 * @param id player
	 * @param adult
	 * Should the player be turned into an adult or a baby version of themselves for this thing.
	 * The bunny does not have babies, as far as I am aware, so you would say "true" for this, to keep it an adult.
	 * Say "false" if a baby version is available.
	 */
	public Hyper(GamePlayer id, boolean adult) {
		super(id, SkillTag.HYPER, 15);
		this.adult = adult;
	}

	/**
	 * Apply speed and jump and stuff to the player if it's not already enabled, and the cooldown is up.
	 * Change their disguise to the hyper disguise
	 */
	public void activate() {
		if ((timeLeft() < 1.0D) && (!this.enabled)) {
			this.enabled = true;
			getGamePlayer().softDisguise(new MobDisguise(getGamePlayer().getDisguiseType(), this.adult));
			getGamePlayer().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1, false, true),
					true);
			getGamePlayer().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 300, 1, false, true),
					true);
			AVM.sched().runTaskLater(AVM.plugin(), new Runnable() {
				public void run() {
					if (Hyper.this.getGamePlayer().getSkill(Hyper.this.getTag()) != null)
						Hyper.this.stopSkill(true);
				}
			}, 300L);
		}
	}

	/**
	 * Update the item, and end the cooldown.
	 */
	public void end() {
		updateItem();
		super.end();
	}

	/**
	 * Return the player to normal disguise, speed, and jump.
	 * Reset the cooldown. Update the items.
	 */
	public void stopSkill(boolean restart) {
		if (this.enabled) {
			this.enabled = false;
			getGamePlayer().getPlayer().removePotionEffect(PotionEffectType.SPEED);
			getGamePlayer().getPlayer().removePotionEffect(PotionEffectType.JUMP);
			if (restart) {
				getGamePlayer().softDisguise(new MobDisguise(getGamePlayer().getDisguiseType(), true));
				resetTime();
			}
			updateItem();
		}
	}

	/**
	 * If the player is in cooldown, change the hotbar item to a yellow glass pane.
	 * If the player is not in cooldown, restore the item back to it's oritinal state.
	 */
	public void updateItem() {
		if (getGamePlayer().getGUIType() == GuiType.GAME_PLAYER) {
			int slot = getGamePlayer().getSlot(getTag()).intValue();
			if (timeLeft() < 1.0D) {
				if ((getGamePlayer() instanceof SkilledPlayer))
					((SkilledPlayer) getGamePlayer()).loadSkill(getSlot());
			} else
				getGamePlayer().getPlayer().getInventory().setItem(slot, GUIBase.itemize(Pane.YELLOW.getItem(), " "));
		}
	}

	/**
	 * Nothing at the moment.
	 */
	public void reset() {
	}
}
