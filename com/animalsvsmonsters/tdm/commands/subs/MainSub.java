package com.animalsvsmonsters.tdm.commands.subs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.tdm.AVM;

/**
 * 
 * @author Justis
 * Restores the player to their onJoin state within the plugin.
 */
public class MainSub extends SubBase {
	
	/**
	 * Handle the sub command with the given inputs
	 * @param sender Who sent the command
	 * @param i The starting point of the subcommand for the SubBase to sort the args.
	 * @param a Args to the command.
	 */
	public MainSub(CommandSender sender, int i, String[] a) {
		super(sender, i, new String[0]);
		if (this.args.length > 0)
			if (!arg(0, new String[] { sender.getName() })) {
				Player other = Bukkit.getPlayer(this.args[0]);
				if (other != null) {
					AVM.getGame().registerPlayer(other);
					other.sendMessage(ChatColor.YELLOW + sender.getName() + ": Your GUI has been restored.");
					msg(ChatColor.YELLOW + "Restored: " + other.getName());
					return;
				}
				msg(ChatColor.RED + this.args[0] + " is not online.");

				return;
			}
		help();
	}

	/**
	 * Used when a player restores themselves instead of inputting another player to restore.
	 */
	public void help() {
		AVM.getGame().registerPlayer((Player) this.sender);
		msg(ChatColor.YELLOW + "Restored.");
	}
}
