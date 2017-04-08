package com.animalsvsmonsters.tdm.skills;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.player.GamePlayer;

/**
 * All skills will extends from this class.
 * 
 * @author Justis
 *
 */
public abstract class BaseSkill implements Listener {
	
	// The player associated with this skill
	private GamePlayer id;
	// The tag associated with your skill
	private SkillTag tag;
	// Is this skill registered?
	private boolean reg;
	// Is this skill currently enabled?
	protected boolean enabled;

	/**
	 * Each skill must have a player associated to it, and a tag.
	 * If your skill does not have a tag, create one, don't use another skill's tag.
	 * @param id
	 * @param tag
	 */
	public BaseSkill(GamePlayer id, SkillTag tag) {
		this.id = id;
		this.tag = tag;
	}

	/**
	 * This class already implements the listener, and has code for registering and unregistering events.
	 * Simply run this method in your skill's constructor to register your events.
	 */
	protected void registerEvents() {
		Bukkit.getPluginManager().registerEvents(this, AVM.plugin());
		this.reg = true;
	}

	/**
	 * Get the skill tag associated with this skill
	 * @return the skill tag associated with this skill
	 */
	public SkillTag getTag() {
		return this.tag;
	}

	/**
	 * Get the slot this skill is located in with the given player
	 * @return this skill's slot
	 */
	public int getSlot() {
		return getGamePlayer().getSlot(getTag()).intValue();
	}

	/**
	 * Is this skill enabled?
	 * @return true if the skill is enabled
	 * false if otherwise
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Return the game player associated with this skill
	 * @return the game player
	 */
	public GamePlayer getGamePlayer() {
		return this.id;
	}

	/**
	 * Return the raw player associated with this skill
	 * @return the player
	 */
	public Player getPlayer() {
		return this.id.getPlayer();
	}

	// Each skill has these methods
	public abstract void activate();

	public abstract void reset();

	/**
	 * Make sure if you override the desolve() method that you run the super.desolve() method as well. This.
	 * Unregistering the events and resetting is very important.
	 */
	public void desolve() {
		reset();
		if (this.reg)
			HandlerList.unregisterAll(this);
	}
}
