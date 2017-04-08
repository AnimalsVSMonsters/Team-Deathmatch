package com.animalsvsmonsters.tdm.skills;

import org.bukkit.scheduler.BukkitTask;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.inventories.utils.ActionBar;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;
import com.animalsvsmonsters.tdm.player.GamePlayer;

/**
 * All equipable skills are sensitive to equipping and unequipping, and all have a cooldown. 
 * This superclass adds these implementations for you and handles some backend stuff to do with the timers.
 * 
 * @author Justis
 *
 */
public abstract class EquipableSkill extends BaseSkill implements SkillCoolDown, Sensitive {
	
	// Cooldown timer
	private double timeLeft;
	
	// Cooldown task
	private BukkitTask task;
	
	// How long is the cooldown?
	private int cool;

	/**
	 * Create this skill for this player with this long of a cooldown
	 * @param id Player
	 * @param tag SkillTag
	 * @param cool Cooldown 
	 */
	public EquipableSkill(GamePlayer id, SkillTag tag, int cool) {
		super(id, tag);
		this.cool = cool;
	}

	/**
	 * Restart the cooldown
	 */
	public void resetTime() {
		this.timeLeft = this.cool;
		if (this.task != null)
			this.task.cancel();
		this.task = AVM.sched().runTaskTimer(AVM.plugin(), this, 20L, 20L);
		msg("Reloading... (" + (int) timeLeft() + ")");
	}

	/**
	 * End the cooldown.
	 */
	public void end() {
		this.timeLeft = 0.0D;
		if (this.task != null)
			this.task.cancel();
		this.task = null;
		msg("");
	}

	/**
	 * Um.. Stops the skill. 
	 */
	public void stopSkill() {
		stopSkill(false);
	}

	public abstract void updateItem();

	public abstract void stopSkill(boolean paramBoolean);

	/**
	 * @return the time left for the cooldown
	 */
	public double timeLeft() {
		return this.timeLeft;
	}

	/**
	 * Reduce the remaining cooldown time by 1 and update the actionbar message if the player is holding the item
	 */
	public void run() {
		this.timeLeft -= 1.0D;
		msg("Reloading... (" + (int) timeLeft() + ")");
		if (this.timeLeft < 1.0D)
			end();
	}

	/**
	 * Desolve the skill
	 */
	public void desolve() {
		end();
		super.desolve();
	}

	/**
	 * A player has equipt the item.
	 * Let's make sure that they see the current time remaining on their skill's cooldown, if there is one.
	 */
	public void equip() {
		if (this.timeLeft > 1.0D)
			msg("Reloading... (" + (int) timeLeft() + ")");
	}

	/**
	 * They unequipped the item.
	 * Let's remove the remaining time on that skill from their action bar.
	 */
	public void unequip() {
		msg("");
	}

	/**
	 * If a player has this skill selected in their hotbar, send them this actionbar message
	 * @param msg to send to the player's actionbar.
	 */
	private void msg(String msg) {
		if ((getGamePlayer().getPlayer().getInventory().getHeldItemSlot() == getSlot())
				&& (getGamePlayer().getGUIType() == GuiType.GAME_PLAYER))
			ActionBar.sendActionBar(getGamePlayer().getPlayer(), msg);
	}
}
