package com.animalsvsmonsters.tdm.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;
import com.animalsvsmonsters.tdm.skills.BaseSkill;
import com.animalsvsmonsters.tdm.skills.DeathSkill;
import com.animalsvsmonsters.tdm.skills.DroneSkill;
import com.animalsvsmonsters.tdm.skills.EquipableSkill;
import com.animalsvsmonsters.tdm.skills.SkillTag;
import com.google.common.collect.Sets;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.TargetedDisguise;
import net.md_5.bungee.api.ChatColor;

/**
 * The game player.
 * The super duper awesome player wrapper that all players in game extend from.
 * This is the bulk of each player's stuff.
 * @author Justis
 *
 */
public abstract class GamePlayer {
	
	// The player's id
	private UUID id;
	// The player's disguise type.
	private DisguiseType type;
	// The player's GUIType
	private GuiType guiType;
	// The Skill object associated with the skill types of this player
	private Map<SkillTag, BaseSkill> tagSkill = new HashMap<>();
	// The skill object associated with the slot numbers of this player's inventory
	private Map<Integer, BaseSkill> slotSkill = new HashMap<>();
	// The slot number associated with the skill types of this player
	private Map<SkillTag, Integer> tagSlot = new HashMap<>();

	/**
	 * Make a game player. :P
	 * @param id the id of this player
	 * @param type the entity to disguise this player as
	 */
	protected GamePlayer(UUID id, EntityType type) {
		this.id = id;
		loadDisguise(type);
		AVM.getGame().setBoard(this);
	}

	/**
	 * Set the player's disguise as the entity type
	 * @param type to set the player's disguise to.
	 */
	private void loadDisguise(EntityType type) {
		try {
			DisguiseType t = DisguiseType.getType(type);
			if (t.isMob())
				disguise(new MobDisguise(t, true));
			else if (t.isMisc()) {
				disguise(new MiscDisguise(t));
			}
			this.type = t;
		} catch (NoClassDefFoundError e) {
			getPlayer().sendMessage(ChatColor.RED + "Disguises are currently unavailable.");
			if (getPlayer().isOp())
				getPlayer().sendMessage(ChatColor.YELLOW
						+ "Recomend shutting down immediately, and installing necessary dependancies.");
		}
	}

	/**
	 * Remove the disguises, and update the player.
	 * @param disks
	 */
	public void undisguise(Disguise[] disks) {
		for (Disguise disk : disks) {
			disk.stopDisguise();
			disk.removeDisguise();
		}
		getPlayer().setAllowFlight(getPlayer().getAllowFlight());
	}

	/**
	 * Get all the disguises on the player
	 * @return
	 */
	public Disguise[] getDisguises() {
		return DisguiseAPI.getDisguises(getPlayer());
	}

	/**
	 * Disguises the player's body.
	 * Normally, the player's body is the player's disguise, however, when the player is in a drone, their body will be left over unattended.
	 * Soft disguise will disguise that left over entity as whater you are disguising the palyer as.
	 * If the player is not in drone mode, it will have no different effect than regular {@link #disguise(TargetedDisguise)}
	 * @param disk disguise to disguise the player's body as
	 */
	public void softDisguise(TargetedDisguise disk) {
		if (this.tagSkill.containsKey(SkillTag.DRONE)) {
			DroneSkill skill = (DroneSkill) this.tagSkill.get(SkillTag.DRONE);
			if (skill.isEnabled()) {
				disk.clone().setEntity(skill.getEntity()).startDisguise();
				return;
			}
		}
		disguise(disk);
	}

	/**
	 * Disguises the player.
	 * Normally you'll want to use {@link #softDisguise(TargetedDisguise)}
	 * @param disk disguise to disguise the player as
	 */
	public void disguise(TargetedDisguise disk) {
		undisguise(getDisguises());
		disk = (TargetedDisguise) disk.clone();
		disk.setViewSelfDisguise(false);
		disk.setShowName(true);
		disk.setHearSelfDisguise(true);
		disk.setEntity(getPlayer()).startDisguise();
		disk.getEntity().setCustomNameVisible(true);
		disk.getEntity().setCustomName(getPlayer().getDisplayName());
	}

	/**
	 * @return default disguise type for this game player class
	 */
	public DisguiseType getDisguiseType() {
		return this.type;
	}
	
	/**
	 * Gets the skill object for the given skill tag, if the player posesses it.
	 * @param tag Skill tag to get the skill object of
	 * @return the skill object of the skill tag type.
	 */
	public BaseSkill getSkill(SkillTag tag) {
		return (BaseSkill) this.tagSkill.get(tag);
	}

	/**
	 * Gets the skill object at the given slot number
	 * @param slot slot number to get the skill object from
	 * @return the skill object at the given slot number
	 */
	public BaseSkill getSkill(Integer slot) {
		return (BaseSkill) this.slotSkill.get(slot);
	}

	/**
	 * Get the slot that the specific skill tag is located at.
	 * @param tag Skill tag to get the slot of
	 * @return the slot that the skill tag is being held
	 */
	public Integer getSlot(SkillTag tag) {
		return Integer.valueOf(this.tagSlot.get(tag) != null ? ((Integer) this.tagSlot.get(tag)).intValue() : -1);
	}

	/**
	 * Get all the skill tags this game player possesses
	 * @return all the skill tags this game player possesses
	 */
	public Set<SkillTag> getSkillTags() {
		return this.tagSkill.keySet();
	}

	/**
	 * Get all the slots that this game player has skills in
	 * @return a set of slot numbers in which the player has a skill
	 */
	public Set<Integer> getSkillSlots() {
		return this.slotSkill.keySet();
	}

	/**
	 * Add a skill to this player
	 * @param skill Skill object to add
	 * @param slot slot to add this skill
	 * @return the skill that has been added
	 */
	protected BaseSkill addSkill(BaseSkill skill, int slot) {
		this.tagSkill.put(skill.getTag(), skill);
		if (slot < 0)
			return skill;
		this.slotSkill.put(Integer.valueOf(slot), skill);
		this.tagSlot.put(skill.getTag(), Integer.valueOf(slot));
		return skill;
	}

	/**
	 * Remove the skill at the given slot
	 * @param slot to remove the skill from
	 */
	protected void removeSkill(Integer slot) {
		removeSkill(((BaseSkill) this.slotSkill.get(slot)).getTag());
	}

	/**
	 * Remove the skill associated with the given tag
	 * @param tag identifies the skill to remove.
	 */
	protected void removeSkill(SkillTag tag) {
		this.tagSkill.remove(tag);
		this.slotSkill.remove(this.tagSlot.get(tag));
		this.tagSlot.remove(tag);
	}

	public abstract void loadItems();
	
	/**
	 * Get all the skills
	 * @return a set of all the skills that this player possesses
	 */
	public Set<BaseSkill> getAllSkills() {
		return Sets.newHashSet(this.tagSkill.values());
	}

	/**
	 * Get all the skills that are loaded in the player's slots
	 * @return a set of all the skills accessable by a slot
	 */
	public Set<BaseSkill> getSlottedSkills() {
		return Sets.newHashSet(this.slotSkill.values());
	}

	/**
	 * Get this gam player's UUID
	 * @return the player's UUID
	 */
	public UUID getID() {
		return this.id;
	}

	/**
	 * Get the player associated with this game player
	 * @return the player
	 */
	public Player getPlayer() {
		return Bukkit.getPlayer(this.id);
	}

	/**
	 * Gets the location of the player
	 * Add support for the death skill, which I intend on using to make the player appear dead, while they wander around the map in spectator or something
	 * This gets the location of the player's most physical presence on the server
	 * @return
	 */
	public Location getLocation() {
		if (this.tagSkill.containsKey(SkillTag.DEATH)) {
			DeathSkill skill = (DeathSkill) this.tagSkill.get(SkillTag.DEATH);
			if (skill.isEnabled())
				return skill.getDisguiseLocation();
		}
		return getPlayer().getLocation().clone();
	}

	/**
	 * Is the player online?
	 * SHOULD always be true
	 * @return true if this game player is online.
	 */
	public boolean isOnline() {
		return Bukkit.getPlayer(this.id) != null;
	}

	/**
	 * Get this game player's GUIType @see {@link com.animalsvsmonsters.tdm.inventories.utils.GuiType}
	 * @return the current GUIType of this player
	 */
	public GuiType getGUIType() {
		return this.guiType;
	}

	/**
	 * Set this player's GUIType
	 * @param t guiType to set the player to
	 */
	public void setGUIType(GuiType t) {
		this.guiType = t;
	}

	/**
	 * Reset the player's inventory to that of the default for this current class.
	 */
	public void resetGUI() {
		getPlayer().getInventory().clear();
		setGUIType(GuiType.GAME_PLAYER);
		loadItems();
		for (BaseSkill skill : this.tagSkill.values())
			if ((skill instanceof EquipableSkill))
				((EquipableSkill) skill).updateItem();
	}

	/**
	 * Desolve the player's data, skills, inventory, and disguise.
	 */
	public void desolve() {
		for (BaseSkill skill : this.tagSkill.values())
			skill.desolve();
		this.tagSkill.clear();
		this.slotSkill.clear();
		this.tagSlot.clear();
		getPlayer().getInventory().clear();
		undisguise(getDisguises());
	}
}