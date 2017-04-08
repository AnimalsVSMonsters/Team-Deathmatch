package com.animalsvsmonsters.tdm.commands.subs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.commands.CMD;

/**
 * 
 * @author Justis
 * Sub command base utility for my sub commands to use for quick and easy addition of new sub commands.
 * Simply extend this class, and pass it it's inputs, you'll have yourself a good ol' sub command util.
 */
public abstract class SubBase extends CMD {
	
	/**
	 * Who sent the message?
	 */
	protected final CommandSender sender;
	/**
	 * Used for quickly listing help messages and such.
	 */
	protected static final String t = ChatColor.DARK_GRAY + "- " + ChatColor.GRAY;

	/**
	 * Initiate the sub command, sort the args, etc.
	 * @param sender Who sent the command?
	 * @param index Where does the sub command start?
	 * @param a args list
	 */
	public SubBase(CommandSender sender, int index, String[] a) {
		super(null, sort(index, a));
		this.sender = sender;
	}
	
	/**
	 * Send a message to the sender, supports chat colors for console.
	 * @param message to send the command sender.
	 */
	public void msg(String message) {
		if (sender instanceof Player)
			sender.sendMessage(message);
		else
			AVM.console().sendMessage(message);
	}

	/**
	 * Sorts the args so that the args the sub command that extends this class will get, will be equal in length, and at the same location, regardless of what index the sub command was triggered from in the initial command.
	 * For example... 
	 * The command "/avm awesome sub" will have the same effect as "/awesome sub". Sub will appear at the same index, cutting off the front end of the command.
	 * This allows me to treat all sub commands the same way.
	 * @param start Starting index of the sub command
	 * @param a Args of the sub command.
	 * @return A new list of args, sorted so that the sub command and anything previous will be chipped off from the args.
	 */
	private static String[] sort(int start, String[] a) {
		List<String> newArgs = new ArrayList<>();
		for (int i = start; a.length > i; i++) {
			newArgs.add(a[i]);
		}
		return (String[]) newArgs.toArray(new String[newArgs.size()]);
	}

	/**
	 * Cuz nearly all my sub commands offer the players some help.
	 * #Organize!
	 */
	public abstract void help();
}
