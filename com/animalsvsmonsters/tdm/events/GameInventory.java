package com.animalsvsmonsters.tdm.events;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;
import com.animalsvsmonsters.tdm.player.GamePlayer;
import com.animalsvsmonsters.tdm.skills.BaseSkill;
import com.animalsvsmonsters.tdm.skills.DroneSkill;
import com.animalsvsmonsters.tdm.skills.Sensitive;
import com.animalsvsmonsters.tdm.skills.SkillTag;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;

/**
 * 
 * @author Justis
 *
 * In game player specific inventory related events for distributing to skills and stuff.
 * 
 * TODO: Organize in correlation with {@link com.animalsvsmonsters.tdm.events.ServerEvents}
 * Or split it up. Idk.. Do something.
 * Honestly, this is very sloppy. Needs work.
 *
 */
public class GameInventory implements Listener {
	private GamePlayer player;

	/**
	 * Initialize deez events for this player.
	 * @param player to listen to these events for.
	 */
	public GameInventory(GamePlayer player) {
		this.player = player;
		AVM.plugin().getServer().getPluginManager().registerEvents(this, AVM.plugin());
	}

	/**
	 * This handles what to do with a player clicking with an item in their hotbar GUI. Specifically their skill items. 
	 * @param e
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onClickWithItem(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getUniqueId().equals(this.player.getPlayer().getUniqueId())) {
			if (guiAction(this.player.getPlayer().getInventory().getHeldItemSlot()))
				e.setCancelled(true);
			if ((e.getPlayer().getInventory().getHeldItemSlot() == 0)
					&& (this.player.getGUIType() == GuiType.GAME_PLAYER)
					&& (e.getAction().name().toUpperCase().startsWith("RIGHT_CLICK")))
				e.setCancelled(false);
		}
	}

	/**
	 * This handles what to do with a player clicking in their inventory in their GUI.
	 * @param e
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if ((p.getUniqueId().equals(this.player.getPlayer().getUniqueId())) && (guiAction(e.getRawSlot())))
			e.setCancelled(true);
	}

	/**
	 * This handles what to do when a player switches the item in their hand.
	 * @param e
	 */
	@EventHandler
	public void invSlot(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		if (p.getUniqueId().equals(this.player.getPlayer().getUniqueId()));
		update(e.getNewSlot(), e.getPreviousSlot());
	}
	
	/**
	 * If the player's GUI is of a game player's, this will update all their equip sensitive skills.
	 * @param newSlot
	 * @param oldSlot
	 * TODO: MAKE THE BOW AND ARROW SLOT IT'S OWN SKILL, STOP HANDLING IT SEPERATELY, IT'S CLOGGING UP MY EVENTS. JESUS CHRIST. WHAT WAS I THINKING.
	 */
	protected void update(int newSlot, int oldSlot) {
		if (this.player.getGUIType() == GuiType.GAME_PLAYER) {
			BaseSkill oldSkill = this.player.getSkill(Integer.valueOf(oldSlot));
			if ((oldSkill instanceof Sensitive))
				((Sensitive) oldSkill).unequip();
			BaseSkill newSkill = this.player.getSkill(Integer.valueOf(newSlot));
			if ((newSkill instanceof Sensitive))
				((Sensitive) newSkill).equip();
			if (newSlot == 0) {
				this.player.getPlayer().getInventory().setItem(9, new ItemStack(Material.ARROW));
			}
			if (oldSlot == 0)
				this.player.getPlayer().getInventory().setItem(9, null);
		}
	}

	/**
	 * Removes the arrow from slot 9 of the player's bar... Cuz it's ugly. I don't want the player to know it's there.
	 * TODO: SERIOUSLY. MAKE IT IT'S OWN SKILL. THIS CLOGGAGE OF BOW AND ARROW STUFF IS MAKING ME UPSET. 
	 * @param e
	 */
	@EventHandler
	public void onOpenn(InventoryOpenEvent e) {
		if (this.player.getGUIType() == GuiType.GAME_PLAYER)
			this.player.getPlayer().getInventory().setItem(9, null);
	}

	/**
	 * Activates the skill at the given slot.
	 * TODO: Remove the skill drone and other GUI types.
	 * Move the skill drone shet to it's own skill, and activate it like everything else. Jesus Christ.
	 */
	protected boolean guiAction(int slot) {
		switch (player.getGUIType()) {
		case GAME_PLAYER:
			if ((slot != 0) && (this.player.getSkill(Integer.valueOf(slot)) != null))
				this.player.getSkill(Integer.valueOf(slot)).activate();
			return true;
			// TODO: Right here. Gross. Move it. Into it's own skill. Thank you!!!
		case SKILL_DRONE:
			if (slot == 0) {
				this.player.getPlayer().launchProjectile(Arrow.class,
						this.player.getLocation().getDirection().multiply(10));
				return false;
			}
			if (slot == 1) {
				// Yeah, this wasn't really working out how I wanted anyways.. Just.... TODO: Delete this and make the skill actually work.
				for (double z = 5.0D; Math.abs(z) <= 5.0D; z -= 1.0D) {
					for (double x = 5.0D; Math.abs(x) <= 5.0D; x -= 1.0D) {
						if (((Math.abs(x) == 5.0D) || (Math.abs(z) == 5.0D)) && ((x == 0.0D) || (z == 0.0D)))
							dropItem(x, z);
					}
				}
				dropItem(0.0D, 0.0D);
			} else if (slot == 8) {
				((DroneSkill) this.player.getSkill(SkillTag.DRONE)).stopSkill(true);
			}
			return true;
		case DIED:
		case MAIN:
		case NONE:
		case SPECTATE:
		}
		return false;
	}

	/**
	 * I was using this to try and drop the bomb items around the player...
	 * Use it or don't... I don't care. X-X 
	 * @param x
	 * @param z
	 */
	private void dropItem(double x, double z) {
		if (this.player.getDisguiseType() == DisguiseType.ZOMBIE)
			this.player.getPlayer().getWorld().dropItem(this.player.getLocation().add(x, 0.0D, z),
					new ItemStack(Material.INK_SACK, 1, (short) 3));
		else if (this.player.getDisguiseType() == DisguiseType.RABBIT)
			this.player.getPlayer().getWorld().dropItem(this.player.getLocation().add(x, 0.0D, z),
					new ItemStack(Material.EGG));
	}

	/**
	 * Get the decimal at the end of a double...
	 * I was using this for something... Forgot what it was though.
	 * @param original
	 * @return
	 */
	@SuppressWarnings("unused")
	private static double getDeci(double original) {
		return original - (int) original;
	}

	/**
	 * Unregister the events for this instance.
	 * (This specific player will not be limited by these events any longer)
	 */
	public void unregister() {
		HandlerList.unregisterAll(this);
	}
}
