package com.animalsvsmonsters.tdm.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.inventories.guis.ChooseAnimal;
import com.animalsvsmonsters.tdm.inventories.guis.ChooseMonster;
import com.animalsvsmonsters.tdm.inventories.guis.FindPlayer;
import com.animalsvsmonsters.tdm.inventories.guis.LeaveGUI;
import com.animalsvsmonsters.tdm.inventories.guis.MainGUI;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;
import com.animalsvsmonsters.tdm.player.GamePlayer;
import com.animalsvsmonsters.tdm.player.NoClassPlayer;
import com.animalsvsmonsters.tdm.player.animals.BunnyPlayer;
import com.animalsvsmonsters.tdm.player.animals.PigPlayer;
import com.animalsvsmonsters.tdm.player.animals.WolfPlayer;
import com.animalsvsmonsters.tdm.player.monsters.EnderPlayer;
import com.animalsvsmonsters.tdm.player.monsters.SkellyPlayer;
import com.animalsvsmonsters.tdm.player.monsters.ZombiePlayer;
import com.animalsvsmonsters.tdm.skills.SkillTag;

/**
 * 
 * @author Justis
 *
 * In server events for blocking and handling the main gui and stuff... 
 * 
 * TODO: Organize in correlation with {@link com.animalsvsmonsters.tdm.events.GameInventory}
 * Or split it up.. Idk. Do something.
 * Honestly, this is a bit sloppy. Needs work.
 *
 */
public class ServerEvents implements Listener {
	public ServerEvents() {
		Bukkit.getServer().getPluginManager().registerEvents(this, AVM.plugin());
	}

	/**
	 * Registers the player on the server.
	 * @param e
	 */
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		AVM.getGame().registerPlayer(e.getPlayer());
	}

	/**
	 * Unregisters the player from the server.
	 * @param e
	 */
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if (AVM.getGame().getPlayer(e.getPlayer()) != null)
			AVM.getGame().unregisterPlayer(e.getPlayer());
	}

	/**
	 * Prevents a player from interacting with the surrounding world if they possess a GUI.
	 * @param e
	 */
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (GuiType.isLocked(e.getPlayer()))
			e.setCancelled(true);
	}
	
	/**
	 * Disables natural map changes such as fire spread vine spread etc.
	 * @param e
	 */
	@EventHandler
	public void onSpread(BlockSpreadEvent e) {
		e.setCancelled(true);
	}

	/**
	 * If the player is locked in a GUI, prevent them from losing food levels.
	 * @param e
	 */
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		if (GuiType.isLocked(e.getEntity()))
			e.setCancelled(true);
	}
	
	/**
	 * If the player is locked in a GUI and tries to do that new stupid 1.9 duelweilding thing, fek them. 
	 * I put the GUI items in those positions for a reason. DON'T MOVE THEM AROUND YOU IDIOT. Thanks love.
	 * @param e
	 */
	@EventHandler
	public void duelWeild(PlayerSwapHandItemsEvent e) {
		if (GuiType.isLocked(e.getPlayer()))
				e.setCancelled(true);
	}


	/**
	 * Gently handle the death of a player.
	 * @param e
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onDeath(PlayerDeathEvent e) {
		if (GuiType.isLocked(e.getEntity())) {
			e.setKeepInventory(true);
			e.setDroppedExp(0);
			e.setKeepLevel(true);
		}
	}

	/**
	 * I forbid players locked in a GUI from picking up items! Hazah!
	 * @param e
	 */
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		if (GuiType.isLocked(e.getPlayer()))
			e.setCancelled(true);
	}

	/**
	 * I forbid players locked in a GUI from dropping items! Double Hazah!
	 * @param e
	 */
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (GuiType.isLocked(e.getPlayer()))
			e.setCancelled(true);
	}

	/**
	 * Creeper explosions no longer do damage to the terrain.
	 * @param e
	 */
	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		e.blockList().clear();
	}

	/**
	 * TNT explosions no longer do damage to the terrain.
	 * @param e
	 */
	@EventHandler
	public void onExplode(BlockExplodeEvent e) {
		e.blockList().clear();
	}

	/**
	 * If a fireball is shot from a player currently in possession of the MPG skill, the fireball will explode where it hits.
	 * @param e
	 */
	@EventHandler
	public void onProj(ProjectileHitEvent e) {
		Projectile ent = e.getEntity();
		if ((ent.getShooter() instanceof Player)) {
			GamePlayer player = AVM.getGame().getPlayer((Player) ent.getShooter());
			if ((GuiType.isDamageable(player.getPlayer())) && ((ent instanceof Fireball))
					&& (player.getSkill(SkillTag.MPG) != null))
				ent.getLocation().getWorld().createExplosion(ent.getLocation(), 1.7F, true);
		}
	}

	/**
	 * Prevents the spawning of mobs other than armor stands. :/ Cuz I need those.
	 * @param e
	 */
	@EventHandler
	public void onMobSpawn(EntitySpawnEvent e) {
		if (e.getEntityType() != EntityType.ARMOR_STAND)
			e.setCancelled(true);
	}

	/**
	 * Spawners don't spawn things no more. 
	 */
	@EventHandler
	public void onSpawner(SpawnerSpawnEvent e) {
		e.setCancelled(true);
	}

	/**
	 * Prevents players from dying of damage. Puts them into spectate mode. 
	 * @param e
	 */
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			if (!GuiType.isDamageable(e.getEntity())) {
				e.setCancelled(true);
				return;
			}
			if (e.getDamage() >= ((Player) e.getEntity()).getHealth()) {
				e.setCancelled(true);
				((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getMaxHealth());

				AVM.getGame().toOther((Player) e.getEntity(), GuiType.SPECTATE);
			}

		} else if (e.getEntityType() == EntityType.ARMOR_STAND) {
			e.setCancelled(true);
		}
	}

	/**
	 * Handles the monster/animal/find-player/leave  GUIS
	 * @param e
	 */
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack currentItem = e.getCurrentItem();
		ItemStack cursor = e.getCursor();
		if (((currentItem == null) || (cursor == null)) || (!isTopView(e))) {
			e.setCancelled(guiAction(p, currentItem));
		} else if (e.getInventory().getTitle().equals("Choose an animal class")) {
			e.setCancelled(true);
			if ((currentItem != null) && (currentItem.hasItemMeta())) {
				if (currentItem.getItemMeta().getDisplayName().equals(ChooseAnimal.bunny))
					AVM.getGame().toAnimal(p, BunnyPlayer.class);
				else if (currentItem.getItemMeta().getDisplayName().equals(ChooseAnimal.pig))
					AVM.getGame().toAnimal(p, PigPlayer.class);
				else if (currentItem.getItemMeta().getDisplayName().equals(ChooseAnimal.wolf)) {
					AVM.getGame().toAnimal(p, WolfPlayer.class);
				}
				p.closeInventory();
			}
		} else if (e.getInventory().getTitle().equals("Choose a monster class")) {
			e.setCancelled(true);
			if ((currentItem != null) && (currentItem.hasItemMeta())) {
				if (currentItem.getItemMeta().getDisplayName().equals(ChooseMonster.ender))
					AVM.getGame().toMonster(p, EnderPlayer.class);
				else if (currentItem.getItemMeta().getDisplayName().equals(ChooseMonster.skelly))
					AVM.getGame().toMonster(p, SkellyPlayer.class);
				else if (currentItem.getItemMeta().getDisplayName().equals(ChooseMonster.zombie)) {
					AVM.getGame().toMonster(p, ZombiePlayer.class);
				}
				p.closeInventory();
			}
		} else if (e.getInventory().getTitle().equals(LeaveGUI.title)) {
			e.setCancelled(true);
			if (e.getSlot() == 0)
				p.closeInventory();
			else if (e.getSlot() == 4)
				AVM.getGame().backToLobby(p);
		} else if (e.getInventory().getTitle().equals("Find Player")) {
			e.setCancelled(true);
			if ((currentItem != null) && (currentItem.hasItemMeta())) {
				String display = currentItem.getItemMeta().getDisplayName();
				if (currentItem.getType() == Material.SKULL_ITEM) {
					Player otherPlayer = Bukkit.getPlayer(display);
					if (otherPlayer == null)
						((FindPlayer) e.getInventory()).reloadPage();
					else
						p.teleport(otherPlayer);
				} else if (currentItem.getType() == Material.ARROW) {
					((FindPlayer) e.getInventory()).loadPlayers();
				}
			}
		}
	}

	/**
	 * We want usage of a GUI item to be available to players when they click WITH it as well as when they click it.
	 * This just passed the click with to a method which will handle both. Yay.
	 * @param e
	 */
	@EventHandler
	public void onClickWithItem(PlayerInteractEvent e) {
		ItemStack currentItem = e.getItem();
		Player p = e.getPlayer();
		e.setCancelled(guiAction(p, currentItem));
	}

	/**
	 * Handles the player based on the item being interacted with.
	 * @param p
	 * @param currentItem
	 * @return
	 */
	private boolean guiAction(Player p, ItemStack currentItem) {
		if (GuiType.getType(p) != null) {
			switch (GuiType.getType(p)) {
			case MAIN:
				if ((currentItem != null) && (currentItem.hasItemMeta())) {
					if (currentItem.getItemMeta().getDisplayName().equals(MainGUI.animal))
						p.openInventory(new ChooseAnimal().getInventory());
					else if (currentItem.getItemMeta().getDisplayName().equals(MainGUI.monster))
						p.openInventory(new ChooseMonster().getInventory());
					else if (currentItem.getItemMeta().getDisplayName().equals(MainGUI.leave))
						AVM.getGame().backToLobby(p);
					else {
						p.sendMessage(ChatColor.RED + "Not implemented yet.");
					}
				}
				return true;
			case SPECTATE:
				if ((currentItem != null) && (currentItem.hasItemMeta())) {
					if (currentItem.getItemMeta().getDisplayName().equals(NoClassPlayer.SPEC_COMPASS)) {
						p.openInventory(new FindPlayer(GamePlayer.class).getInventory());
					} else if (currentItem.getItemMeta().getDisplayName().equals(NoClassPlayer.SPEC_EXIT)) {
						AVM.getGame().unregisterPlayer(p);
						AVM.getGame().registerPlayer(p);
					} else {
						p.sendMessage(ChatColor.RED + "Not implemented yet.");
					}
				}
				return true;
			default:
			}
			return false;
		}
		return false;
	}

	/**
	 * Is the inventory being clicked above the player's inventory?
	 * @param e
	 * @return
	 */
	private boolean isTopView(InventoryClickEvent e) {
		if (e.getView().getTopInventory().getSize() > e.getRawSlot())
			return true;
		return false;
	}

	/**
	 * Is the click outside of the inventory? As in, like, when you drop an item..
	 * @param e
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean isOutsideInv(InventoryClickEvent e) {
		if (e.getSlot() == -999)
			return true;
		return false;
	}
}