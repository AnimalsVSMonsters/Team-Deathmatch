package com.animalsvsmonsters.tdm;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.animalsvsmonsters.tdm.commands.CommandManager;
import com.animalsvsmonsters.tdm.data.BungeeCom;
import com.animalsvsmonsters.tdm.data.Updater;
import com.animalsvsmonsters.tdm.events.ServerEvents;
import com.animalsvsmonsters.tdm.game.AVMGame;
import com.animalsvsmonsters.tdm.game.Game;

/**
 * 
 * @author Justis
 * 
 * The main AVM class
 *
 */
public final class AVM extends JavaPlugin {
	
	/*
	 * The active games.
	 * Currently, this plugin only uses one game. The default game.
	 * This is here to support future additional games, and multiple games at a time.
	 */
	private final Map<String, Game> games = new HashMap<>();
	// Updater util.
	private final Updater updater = new Updater(getDescription().getVersion());

	/**
	 * Sets up the plugin, obviously. :P
	 * By default, and for testing purposes, it starts a game, which players will automatically be added to when they join.
	 * Loads all currently online players into the default game.
	 */
	public void onEnable() {
		new BungeeCom();
		new ServerEvents();
		new CommandManager();
		this.games.put("AVM", new AVMGame());
		((Game) this.games.get("AVM")).loadAllPlayers();
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[AVM] Animals Vs Monsters has been enabled!");
	}

	/**
	 * Ends all currently active games.
	 * Checks for updates.
	 */
	public void onDisable() {
		for (Game game : games.values())
			game.endGame();
		this.updater.checkForUpdates();
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[AVM] Animals Vs Monsters has been disabled!");
	}

	/**
	 * @return The plugin updater.
	 */
	public Updater updater() {
		return this.updater;
	}

	/**
	 * @return The plugin's main class.
	 */
	public static AVM plugin() {
		return getPlugin(AVM.class);
	}

	/**
	 * For convenience sake. 
	 * @return Console sender.
	 */
	public static ConsoleCommandSender console() {
		return Bukkit.getConsoleSender();
	}

	/**
	 * For testing purposes.
	 * @return The default game. "AVM".
	 */
	public static Game getGame() {
		return (Game) plugin().games.get("AVM");
	}

	/**
	 * Convenience.
	 * @return scheduler.
	 */
	public static BukkitScheduler sched() {
		return Bukkit.getScheduler();
	}

	/**
	 * Also for testing purposes, toggles the default game. 
	 */
	public void toggleGame() {
		((Game) this.games.get("AVM")).endGame();
		this.games.put("AVM", new AVMGame());
		((Game) this.games.get("AVM")).loadAllPlayers();
	}
}