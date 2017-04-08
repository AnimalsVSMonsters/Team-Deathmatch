package com.animalsvsmonsters.tdm.skills;

/**
 * Certain skills have a cooldown on them.
 * We'd like to treat them all the same, so here's an interface to help do so.
 * 
 * @author Justis
 *
 */
public abstract interface SkillCoolDown extends Runnable {
	public abstract void resetTime();

	public abstract void end();

	public abstract double timeLeft();
}
