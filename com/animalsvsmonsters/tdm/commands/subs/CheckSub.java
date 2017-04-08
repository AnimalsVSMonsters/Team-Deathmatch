package com.animalsvsmonsters.tdm.commands.subs;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;
import com.animalsvsmonsters.tdm.player.GamePlayer;

import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author Justis
 * For when a player wants to get their current state within the plugin.
 * What they are currently disguised as, what their GUI type is.
 * Feel free to add more information in the future.
 */
public class CheckSub extends SubBase {
	
	/**
	 * Handle the sub command with the given inputs
	 * @param sender Who sent the command
	 * @param i The starting point of the subcommand for the SubBase to sort the args.
	 * @param a Args to the command.
	 */
	public CheckSub(CommandSender sender, int i, String[] a) {
		super(sender, i, a);
		Set<Player> players = new HashSet<>();
		if (this.args.length > 0) {
			if (this.args[0].equalsIgnoreCase("players"))
				players.addAll(Bukkit.getOnlinePlayers());
			else
				for (String p : this.args) {
					Player player = Bukkit.getPlayer(p);
					if (player != null)
						players.add(player);
				}
		} else {
			players.add((Player) sender);
		}
		if (players.size() > 0)
			for (Player player : players) {
				GamePlayer gp = AVM.getGame().getPlayer(player);
				sender.sendMessage(ChatColor.YELLOW.toString() + player.getName() + ":");
				sender.sendMessage(ChatColor.GRAY + "- Disguise: " + gp.getDisguiseType());
				sender.sendMessage(ChatColor.GRAY + "- GUIType: " + GuiType.getType(player));
			}
		else
			sender.sendMessage(ChatColor.RED + "No such player(s) online.");
	}

	/**
	 * Not used at the moment.
	 */
	public void help() {
	}
}