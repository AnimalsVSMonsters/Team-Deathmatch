package com.animalsvsmonsters.tdm.commands.subs;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.animalsvsmonsters.tdm.AVM;

/**
 * 
 * @author Justis
 * Sub command class for the update sub command
 */
public class UpdateSub extends SubBase {
	
	/**
	 * Handle the sub command with the given inputs
	 * @param sender Who sent the command
	 * @param i The starting point of the subcommand for the SubBase to sort the args.
	 * @param a Args to the command.
	 */
	public UpdateSub(CommandSender sender, int i, String[] a) {
		super(sender, i, a);
		if (sender.isOp())
			AVM.plugin().updater().update();
		else
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
	}

	/**
	 * Unused for now.
	 */
	public void help() {
	}
}