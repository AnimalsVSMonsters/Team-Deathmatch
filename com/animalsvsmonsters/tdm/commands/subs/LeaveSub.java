package com.animalsvsmonsters.tdm.commands.subs;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.tdm.AVM;

/**
 * 
 * @author Justis
 *	For when a player wants to leave the game server and go back to the lobby.
 */
public class LeaveSub extends SubBase {
	
	/**
	 * Handle the sub command with the given inputs
	 * @param sender Who sent the command
	 * @param i The starting point of the subcommand for the SubBase to sort the args.
	 * @param a Args to the command.
	 */
	public LeaveSub(CommandSender sender, int i, String[] a) {
		super(sender, i, a);
		if ((sender instanceof Player))
			AVM.getGame().backToLobby((Player) sender);
		else
			msg(ChatColor.RED + "You must be a player!");
	}

	/**
	 * Not used at the moment.
	 */
	public void help() {
	}
}