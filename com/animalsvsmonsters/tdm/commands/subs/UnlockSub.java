package com.animalsvsmonsters.tdm.commands.subs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;

/**
 * 
 * @author Justis
 * Sub command for unlocking a player's inventory, and removing their GUI.
 * They are of no player type, free from normal plugin restrictions.
 */
public class UnlockSub extends SubBase {
	
	/**
	 * Handle the sub command with the given inputs
	 * @param sender Who sent the command
	 * @param i The starting point of the subcommand for the SubBase to sort the args.
	 * @param a Args to the command.
	 */
	public UnlockSub(CommandSender sender, int i, String[] a) {
		super(sender, i, a);
		if (this.args.length > 0)
			if (!arg(0, new String[] { sender.getName() })) {
				Player other = Bukkit.getPlayer(this.args[0]);
				if (other != null) {
					AVM.getGame().toOther(other, GuiType.NONE);
					other.sendMessage(ChatColor.YELLOW + sender.getName() + ": You have been unlocked.");
					msg(ChatColor.YELLOW + "Unlocked: " + other.getName());
					return;
				}
				msg(ChatColor.RED + this.args[0] + " is not online.");

				return;
			}
		help();
	}

	/**
	 * Used when the sender wants to unlock themselves instead of entering an input player to unlock.
	 */
	public void help() {
		AVM.getGame().toOther((Player) this.sender, GuiType.NONE);
		msg(ChatColor.YELLOW + "Unlocked.");
	}
}
