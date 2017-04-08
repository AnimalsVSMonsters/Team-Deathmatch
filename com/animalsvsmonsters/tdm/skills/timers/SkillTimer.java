package com.animalsvsmonsters.tdm.skills.timers;

import org.bukkit.scheduler.BukkitRunnable;

import com.animalsvsmonsters.tdm.skills.BaseSkill;

/**
 * TODO: The whole timer system needs to be re-done.
 * After working with it for a while with the current skills, I realized it doesn't completely suit my needs and there are better ways of doing this.
 * 
 * 
 * Currently just a wrapper for a bukkit runnable.
 * Using it for generic timers used in skills.
 * TODO: Make this more useful or something.
 * 
 * @author Justis
 *
 */
public abstract class SkillTimer extends BukkitRunnable {
	
	BaseSkill skill;

	/**
	 * Create a runnable timer with the associated skill
	 * @param skill
	 */
	public SkillTimer(BaseSkill skill) {
		this.skill = skill;
	}
}
