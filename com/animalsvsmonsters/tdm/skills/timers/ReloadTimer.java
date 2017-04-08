package com.animalsvsmonsters.tdm.skills.timers;

import org.bukkit.scheduler.BukkitTask;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.skills.BaseSkill;

/**
 * TODO: The whole timer system needs to be re-done.
 * After working with it for a while with the current skills, I realized it doesn't completely suit my needs and there are better ways of doing this.
 * 
 * The reload timer keeps track of how long an item has to reload.
 * TODO: With 1.9, you can set an item's reload time. Consider using that instead of making timers? Or would visual aids be better... Ask Croc.
 * @author Justis
 *
 */
public class ReloadTimer extends SkillTimer {
	
	// Time remaining
	private int i;
	// Duh, the actual task
	BukkitTask task;

	/**
	 * Create a timer, and associate it with a skill
	 * @param skill
	 * @param time
	 */
	private ReloadTimer(BaseSkill skill, int time) {
		super(skill);
		this.i = time;
	}

	/**
	 * Creates a new timer.
	 * I avoided using the constructor for implementation purposes in the past. These have since been removed.
	 * @param skill to associate with this timer
	 * @param time for the timer to count down from
	 * @return
	 */
	public static ReloadTimer newTimer(BaseSkill skill, int time) {
		ReloadTimer timer = new ReloadTimer(skill, time);
		timer.task = timer.runTaskTimer(AVM.plugin(), 20L, 20L);
		return timer;
	}

	/**
	 * BOOP, decreased the timer by 1, or canceled it if it's out of juice.
	 */
	public void run() {
		if (this.i > 2)
			this.i -= 1;
		else
		cancel();
	}
}
