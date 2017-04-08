package com.animalsvsmonsters.tdm.skills;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.inventories.GUIBase;
import com.animalsvsmonsters.tdm.inventories.guis.DroneGUI;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;
import com.animalsvsmonsters.tdm.inventories.utils.Pane;
import com.animalsvsmonsters.tdm.player.GamePlayer;
import com.animalsvsmonsters.tdm.player.SkilledPlayer;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.TargetedDisguise;

/**
 * Drone skill allows the player to leave a entity representative of themselves at their location, then fly away as a bat/chicken, with a different GUI, and special skills.
 * At least until the timer runs out, and they're returned to their body.
 * When the entity is damaged, the damage reflects onto the player, and they are removed from drone mode, and put back at their old location.
 *  
 * TODO: The drone's GUI has it's own set of skills that have not yet been implemented. 
 * @author Justis
 *
 */
public class DroneSkill extends EquipableSkill {
	
	// The armor stand thing that will server as a placeholder for the player, making it look like they stopped to stand still and control their drone.
	private LivingEntity ent;
	// The disguise type of the entity ^
	private DisguiseType t;

	/**
	 * Create a drone skill for the specified player, of the specified disguise type for the drone.
	 * @param id The game player to give this skill to
	 * @param type the disguise type of the drone.
	 */
	public DroneSkill(GamePlayer id, DisguiseType type) {
		super(id, SkillTag.DRONE, 30);
		this.t = type;
	}

	/**
	 * Activate the drone for 300 ticks if it's not currently on cooldown.
	 * Spawn a still player clone at their location.
	 */
	@SuppressWarnings("deprecation")
	public void activate() {
		if ((timeLeft() < 1.0D) && (!this.enabled)) {
			this.enabled = true;
			this.ent = ((LivingEntity) getGamePlayer().getPlayer().getWorld().spawnEntity(getGamePlayer().getLocation(), EntityType.ARMOR_STAND));
			DisguiseAPI.getDisguise(getGamePlayer().getPlayer()).clone().setEntity(this.ent).startDisguise();
			this.ent.addPotionEffects(getGamePlayer().getPlayer().getActivePotionEffects());
			this.ent.getEquipment().setItemInHand(getGamePlayer().getPlayer().getItemInHand());
			this.ent.getEquipment().setArmorContents(getGamePlayer().getPlayer().getInventory().getArmorContents());
			for (PotionEffect eff : this.ent.getActivePotionEffects())
				getGamePlayer().getPlayer().removePotionEffect(eff.getType());
			getGamePlayer().disguise(new MobDisguise(this.t));
			getGamePlayer().getPlayer().setAllowFlight(true);
			getGamePlayer().getPlayer().setFlying(true);
			getGamePlayer().getPlayer().setVelocity(getGamePlayer().getPlayer().getVelocity().setY(0.5D));
			DroneGUI.setGUI(getGamePlayer().getPlayer(), this.t == DisguiseType.CHICKEN);
			AVM.sched().runTaskLater(AVM.plugin(), new Runnable() {
				public void run() {
					if (DroneSkill.this.getGamePlayer().getSkill(DroneSkill.this.getTag()) != null)
						DroneSkill.this.stopSkill(true);
				}
			}, 300L);
		}
	}

	/**
	 * Exit drone mode from the player.
	 * Remove the other entity. 
	 */
	public void stopSkill(boolean restart) {
		if (this.enabled) {
			this.enabled = false;
			getGamePlayer().resetGUI();
			if (restart) {
				if (this.ent != null)
					getGamePlayer().disguise((TargetedDisguise) DisguiseAPI.getDisguise(this.ent));
				resetTime();
			}
			updateItem();
			if (this.ent != null) {
				getGamePlayer().getPlayer().teleport(this.ent);
				getGamePlayer().getPlayer().setAllowFlight(false);
				getGamePlayer().getPlayer().setFlying(false);
				getGamePlayer().getPlayer().addPotionEffects(this.ent.getActivePotionEffects());
				for (Disguise disk : DisguiseAPI.getDisguises(this.ent)) {
					disk.stopDisguise();
					disk.removeDisguise();
				}
				this.ent.remove();
				this.ent = null;
			}
		}
	}

	/**
	 * Restore the player's drone item, end the cooldown timer thing.
	 * @see {@link com.animalsvsmonsters.tdm.skills.EquipableSkill#end()}
	 */
	public void end() {
		updateItem();
		super.end();
	}

	/**
	 * Get the entity pretending to be a player while they're in drone mode.
	 * @return the entity (armorstand)
	 * null if no entity is there.
	 */
	public Entity getEntity() {
		return this.ent;
	}

	/**
	 * Update the skill item.
	 * This is necessary because currently, when a skill is on cool-down, the item in their bar gets changed to a yellow stained glass pane.
	 * It gets changed back to the regular item when the cooldown is up.
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
	 * I guess this doesn't do anything right now.
	 * TODO: Make this do something important. xD
	 */
	public void reset() {
	}
}
