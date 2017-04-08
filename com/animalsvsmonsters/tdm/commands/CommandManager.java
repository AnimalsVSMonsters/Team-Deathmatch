package com.animalsvsmonsters.tdm.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.commands.subs.CheckSub;
import com.animalsvsmonsters.tdm.commands.subs.FlySub;
import com.animalsvsmonsters.tdm.commands.subs.LeaveSub;
import com.animalsvsmonsters.tdm.commands.subs.MainSub;
import com.animalsvsmonsters.tdm.commands.subs.Speed;
import com.animalsvsmonsters.tdm.commands.subs.ToPlayerSub;
import com.animalsvsmonsters.tdm.commands.subs.UnlockSub;
import com.animalsvsmonsters.tdm.commands.subs.UpdateSub;
import com.google.common.collect.Lists;

/**
 * 
 * @author Justis
 * 
 * Command manager for this plugin. Handles all incoming commands.
 *
 */
public class CommandManager implements CommandExecutor, TabCompleter {
	
	/**
	 * Sets up command manager, which handles all the commands for this plugin.
	 */
	public CommandManager() {
		for (String cmd : AVM.plugin().getDescription().getCommands().keySet())
			load(AVM.plugin().getCommand(cmd));
	}

	/**
	 * Sets the executor and tab completer for each command to this class.
	 * @param cmd The command
	 */
	private void load(PluginCommand cmd) {
		cmd.setExecutor(this);
		cmd.setTabCompleter(this);
	}

	/**
	 * Overrides default onCommand.
	 * Checks for which command it is, and what sub command it's using, if it's the main /AVM command.
	 * Adding a new sub command is very easy.
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CMD cmd = new CMD(command, args);
		if (cmd.is("fly" ))
			new FlySub(sender, 0, args);
		else if (cmd.is("leave" ))
			new LeaveSub(sender, 0, args);
		else if (cmd.is("AVM" )) {
			if (args.length > 0) {
				if (cmd.arg(0,"spectate"))
					new ToPlayerSub(sender, 0, args);
				else if (cmd.arg(0,"unlock", "free"))
					new UnlockSub(sender, 1, args);
				else if (cmd.arg(0,"none", "main", "restore"))
					new MainSub(sender, 1, args);
				else if (cmd.arg(0,"check" ))
					new CheckSub(sender, 1, args);
				else if (cmd.arg(0,"to" ))
					new ToPlayerSub(sender, 1, args);
				else if (cmd.arg(0,"update"))
					new UpdateSub(sender, 1, args);
				else if (cmd.arg(0,"speed"))
					new Speed(sender, 1, args);
				else if (cmd.arg(0,"dev", "developer", "justis")) {
					AVM.plugin().updater().checkForUpdates();
				} else {
					sender.sendMessage(ChatColor.RED + "/avm - and press tab.");
				}
			} else {
				sender.sendMessage(ChatColor.GREEN + "AVM - Developed by JustisR");
				sender.sendMessage(ChatColor.GRAY + "Questions? Contact me on skype @xyourfreindx");
			}
		} else
			sender.sendMessage(ChatColor.RED + "This command has not yet been implemented.");

		return false;
	}

	/**
	 * Tab complete. Returns the proper sub commands for /avm depending on arg legnth.
	 * If sub command is "to" returns all the possible entity change types.
	 */
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("avm")) {
			if (args.length == 1)
				return list(new String[] { "spectate", "unlock", "main", "to", "check" });
			if ((args.length == 2) && (args[0].equalsIgnoreCase("to"))) {
				return list(new String[] { "rabit", "pig", "wolf", "skelly", "ender", "zombie" });
			}
		}
		return null;
	}

	/** 
	 * Nothing special. Turns a string array into a list. Yum.
	 * @param args String array.
	 * @return List. 
	 * Boom.
	 */
	private static List<String> list(String[] args) {
		return Lists.newArrayList(args);
	}
}
