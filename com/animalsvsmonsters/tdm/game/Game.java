package com.animalsvsmonsters.tdm.game;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.data.BungeeCom;
import com.animalsvsmonsters.tdm.inventories.guis.MainGUI;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;
import com.animalsvsmonsters.tdm.player.AnimalPlayer;
import com.animalsvsmonsters.tdm.player.GamePlayer;
import com.animalsvsmonsters.tdm.player.MonsterPlayer;
import com.animalsvsmonsters.tdm.player.NoClassPlayer;

import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author Justis
 * All of the beautiful code handling each game.
 * Game stuff.
 */
public abstract class Game {
	
	// Players currently loaded into the game.
	private final Map<UUID, GamePlayer> players = new HashMap<>();

	/**
	 * Create a game. ;D
	 */
	public Game() {
		loadBoards();
	}

	/**
	 * Load all the online players into the game.
	 */
	public void loadAllPlayers() {
		for (Player p : Bukkit.getServer().getOnlinePlayers())
			registerPlayer(p);
	}

	public abstract void setBoard(GamePlayer paramGamePlayer);

	public abstract void loadBoards();

	/**
	 * Puts the player into the game as a player without a class yet.
	 * @param player
	 */
	public void registerPlayer(Player player) {
		if (this.players.containsKey(id(player)))
			unregisterPlayer(player);
		this.players.put(player.getUniqueId(), new NoClassPlayer(player.getUniqueId(), GuiType.NONE));
		MainGUI.setGUI(player);
	}

	/**
	 * Remove the player from the game.
	 * Careful with this.
	 * @param player
	 */
	public void unregisterPlayer(Player player) {
		((GamePlayer) this.players.remove(id(player))).desolve();
		player.closeInventory();
	}

	/**
	 * Connects the player back to the bungee lobby.
	 * @param player
	 */
	public void backToLobby(Player player) {
		BungeeCom.sendConnect(player, "lobby");
	}

	/**
	 * @param id 
	 * @return the game player with the given id.
	 */
	public GamePlayer getPlayer(UUID id) {
		return (GamePlayer) this.players.get(id);
	}

	/**
	 * @param player
	 * @return the game player with the given name.
	 */
	public GamePlayer getPlayer(Player player) {
		return getPlayer(player.getUniqueId());
	}

	/**
	 * Get all game players who extend a certain class.
	 * e.g MonsterPlayer, BunnyPlayer etc.
	 * @param clazz
	 * @return a set of players in game who extend that class
	 */
	public Set<Player> getPlayersByClass(Class<? extends GamePlayer> clazz) {
		Set<Player> set = new HashSet<>();
		for (GamePlayer p : this.players.values()) {
			if (clazz.isInstance(p));
			set.add(p.getPlayer());
		}
		return set;
	}

	/**
	 * Unregisters a player and puts them back into the game but as a player without a class, and a GUI of your choice.
	 * @param player to change
	 * @param type GUI type.
	 */
	public void toOther(Player player, GuiType type) {
		unregisterPlayer(player);
		this.players.put(id(player), new NoClassPlayer(id(player), type));
	}

	/**
	 * Unregister the player and load them back into the game but as an animal player of the type you specify.
	 * @param player to change
	 * @param clazz that extends AnimalPlayer that you want to change the player to
	 */
	public void toAnimal(Player player, Class<? extends AnimalPlayer> clazz) {
		unregisterPlayer(player);
		AnimalPlayer pl = (AnimalPlayer) newGamePlayer(clazz, id(player));
		this.players.put(id(player), pl);
	}

	/**
	 * Unregister the player and load them back into the game but as a monster player of the type you specify.
	 * @param playerto change
	 * @param clazz that extends MonsterPlayer that you want to change the player to.
	 */
	public void toMonster(Player player, Class<? extends MonsterPlayer> clazz) {
		unregisterPlayer(player);
		MonsterPlayer pl = (MonsterPlayer) newGamePlayer(clazz, id(player));
		this.players.put(id(player), pl);
	}

	/**
	 * Dissolves all the players in game.
	 * TODO: When the game starts handling the map, we'll want to reset the map as well
	 */
	public void endGame() {
		for (GamePlayer player : this.players.values()) {
			player.getPlayer().sendMessage(ChatColor.YELLOW + "Resetting...");
			player.desolve();
		}
	}

	/**
	 * Creates a new gameplayer. 
	 * This is private, because there are a lot of inputs which won't work here, and I'd like to wrap them in some other specific methods, @see {@link #toMonster(Player, Class)} and {@link #toAnimal(Player, Class)} 
	 * @param clazz this is the class type you'll be wanting to change the player to, it must extend GamePlayer, obviously.
	 * @param param This is the param for the GamePlayer, normally the player's UUID.
	 * @return The new GamePlayer object, of the subclass you specified.
	 */
	private Object newGamePlayer(Class<? extends GamePlayer> clazz, Object param) {
		try {
			return clazz.getConstructors()[0].newInstance(new Object[] { param });
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			AVM.console().sendMessage(
					ChatColor.DARK_RED + "Problem making new instance of game player. Class: " + clazz.getSimpleName());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Shorthand for getting a player's ID.
	 * @param player
	 * @return
	 */
	private static UUID id(Player player) {
		return player.getUniqueId();
	}
}