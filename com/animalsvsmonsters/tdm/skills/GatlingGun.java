package com.animalsvsmonsters.tdm.skills;

import org.bukkit.entity.Arrow;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.player.GamePlayer;
import com.animalsvsmonsters.tdm.skills.timers.SkillTimer;

/**
 * The gatling gun was the last skill that I was working on before this project's sudden halt, and losing the source, needing to decompile it from the jar and restore everything.
 * 
 * The gatling gun plants the player in the ground so they can't run or jump when activated.
 * It also reduces their healthy to half scale.
 * It then proceeds to rapid fire without trigger, until out of ammo or the player ends it.
 * 
 * TODO: Add ammo, and a cooldown.
 * 
 * @author Justis
 *
 */
public class GatlingGun extends BaseSkill implements Sensitive, Moments {
	
	/**
	 * Create this skill for this player
	 * @param id player to apply this skill to.
	 */
	public GatlingGun(GamePlayer id) {
		super(id, SkillTag.GATLING);
	}

	/**
	 * Activate the gatling gun.
	 * If it's already activated, turn it off
	 */
	public void activate() {
		if (timers.containsKey("gun")) {
			timers.get("gun").cancel();
			timers.remove("gun");
		}
		else {
			timers.put("gun", new SkillTimer(this) {
				{
					this.runTaskTimer(AVM.plugin(), 0, 5);
				}
				public void run() {
					getPlayer().launchProjectile(Arrow.class);
				}
			});
		}
	}

	/**
	 * Uh.. Nothing at the moment.
	 */
	public void reset() {
	}

	/**
	 * The player has equip the gatling gun.
	 * This removes the player's ability to jump/walk.
	 * Reduces their hearts to half.
	 * 
	 * TODO: Disable the player's ability to jump when they equip this item
	 */
	public void equip() {
		getPlayer().setWalkSpeed(0.0F);
	    double d = getGamePlayer().getPlayer().getHealth() / 1.66666666666666D;
	    getPlayer().setHealth((int)d);
		getPlayer().setMaxHealth(12.0D);
		getPlayer().setHealthScale(12.0D);
	}
	
	
	/*
	 * Justis isaDweebforlife!
	 * 
	 * - Signed, Justis' best friend.
	 */

	/**
	 * Restore the player to non-gatlinggun state
	 * 
	 * TODO: Return the player's ability to jump when they equip this item
	 */
	public void unequip() {
		if (getPlayer().getMaxHealth() < 20) {
			double d = getGamePlayer().getPlayer().getHealth() * 1.666666666666667D;
			getPlayer().setWalkSpeed(0.2F);
			getPlayer().setHealthScale(20.0D);
			getPlayer().setMaxHealth(20.0D);
			getPlayer().setHealth((int) d);
		}
		if (timers.containsKey("gun"))
			activate();
	}
	
	public void desolve() {
		unequip();
		if (timers.containsKey("gun"))
			activate();
		super.desolve();	
	}
}