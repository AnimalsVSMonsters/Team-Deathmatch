package com.animalsvsmonsters.tdm.commands.subs;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 
 * @author Justis
 * Dude. Let's fly. xD
 * Yeah, this sub command is for when players wanna fly. 
 */
public class FlySub extends SubBase {
	
	/**
	 * Handle the sub command with the given inputs
	 * @param sender Who sent the command
	 * @param i The starting point of the subcommand for the SubBase to sort the args.
	 * @param a Args to the command.
	 */
	public FlySub(CommandSender sender, int i, String[] a) {
		super(sender, i, a);
		if ((sender instanceof Player)) {
			Player player = (Player) sender;
			// Should probably make a Permissions enum.....
			if (player.hasPermission("avm.fly")) {
				if (player.getAllowFlight()) {
					player.setAllowFlight(false);
					player.setFlying(false);
					msg(ChatColor.GREEN + "Fly disabled!");
				} else {
					player.setAllowFlight(true);
					msg(ChatColor.GREEN + "Fly enabled!");
				}
			} else
				msg(ChatColor.RED + "You don't have permission to fly.");
		} else {
			msg(ChatColor.RED + "You must be a player to fly!");
		}
	}

	/** 
	 * Not used at the moment.
	 */
	public void help() {
	}
}
