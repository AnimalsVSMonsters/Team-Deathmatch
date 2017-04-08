package com.animalsvsmonsters.tdm.commands;

import org.bukkit.command.Command;

/**
 * 
 * @author Justis
 * My solution to a cluttered onCommand.
 */
public class CMD {
	
	// The command. Derp.
	private final Command command;
	// The args for the command. Derp.
	protected final String[] args;

	/**
	 * Initialize this beauty.
	 * @param cmd Command
	 * @param args Args for this command
	 */
	public CMD(Command cmd, String[] args) {
		this.command = cmd;
		this.args = args;
	}

	/**
	 * Check to see if the arg at the given length matched any of the possibilities.
	 * Reduces a huge list of if(true || true || true || true) into a single if(true).
	 * @param i Index of the arg.
	 * @param possibilities List of all the acceptable arg possibilities. 
	 * @return True if the arg at index i matches any of the possibilities.
	 * False if otherwise.
	 */
	public boolean arg(int i, String... possibilities) {
		for (String pos : possibilities)
			if (this.args[i].equalsIgnoreCase(pos))
				return true;
		return false;
	}

	/**
	 * Checks to see if the command is any of the possibilities or "AVM".
	 * The AVM check is because people should be able to do /AVM [command] for any of the normal /commands in this plugin.
	 * @param possibilities to check if the command matches
	 * @return true if the command matches any of the possibilities, or is AVM. 
	 */
	public boolean is(String... possibilities) {
		for (String pos : possibilities)
			if ((this.command.getName().equalsIgnoreCase(pos)) || ((this.command.getName().equalsIgnoreCase("AVM"))
					&& (this.args.length > 0) && (this.args[0].equalsIgnoreCase(pos))))
				return true;
		return false;
	}
}