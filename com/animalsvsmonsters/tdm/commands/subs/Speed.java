package com.animalsvsmonsters.tdm.commands.subs;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.tdm.AVM;

/**
 * 
 * @author Justis
 * Sub command for changing a user's speed.
 * The default speed is quite annoying. 
 */
public class Speed extends SubBase {
	
	/**
	 * Handle the sub command with the given inputs
	 * @param sender Who sent the command
	 * @param i The starting point of the subcommand for the SubBase to sort the args.
	 * @param a Args to the command.
	 */
	public Speed(CommandSender sender, int i, String[] a) {
		super(sender, i, a);
		if ((sender instanceof Player)) {
			if (this.args.length > 0)
				try {
					if (((Player) sender).isFlying()) {
						sender.sendMessage(ChatColor.GREEN + "Setting fly speed set to " + this.args[0] + "...");
						((Player) sender).setFlySpeed(10.0F);
					} else {
						((Player) sender).setWalkSpeed(10.0F);
						sender.sendMessage(ChatColor.GREEN + "Setting walk speed set to " + this.args[0] + "...");
					}
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "Speed must be an integer, 0-10");
					AVM.console().sendMessage(e.getMessage());
				}
			else
				sender.sendMessage(ChatColor.RED + "/avm speed <speed>");
		} else
			sender.sendMessage(ChatColor.RED + "You must be a player to set your speed.");
	}

	/**
	 * Unused at the moment.
	 */
	public void help() {
	}
}