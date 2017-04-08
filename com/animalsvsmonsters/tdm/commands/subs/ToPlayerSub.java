package com.animalsvsmonsters.tdm.commands.subs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.animalsvsmonsters.tdm.AVM;
import com.animalsvsmonsters.tdm.inventories.utils.GuiType;
import com.animalsvsmonsters.tdm.player.animals.BunnyPlayer;
import com.animalsvsmonsters.tdm.player.animals.PigPlayer;
import com.animalsvsmonsters.tdm.player.animals.WolfPlayer;
import com.animalsvsmonsters.tdm.player.monsters.EnderPlayer;
import com.animalsvsmonsters.tdm.player.monsters.SkellyPlayer;
import com.animalsvsmonsters.tdm.player.monsters.ZombiePlayer;

/**
 * 
 * @author Justis
 *	Sub command for changing a user's class type.
 */
public class ToPlayerSub extends SubBase {
	
	/**
	 * Handle the sub command with the given inputs
	 * @param sender Who sent the command
	 * @param i The starting point of the subcommand for the SubBase to sort the args.
	 * @param a Args to the command.
	 */
	public ToPlayerSub(CommandSender sender, int i, String[] a) {
		super(sender, i, a);
		Player player = (Player) sender;
		if (this.args.length > 1) {
			player = Bukkit.getPlayer(this.args[1]);
			if (player == null) {
				msg(ChatColor.YELLOW + this.args[1] + " is not online");
				return;
			}
			msg(ChatColor.YELLOW + this.args[1] + "'s class changed to " + this.args[0]);
		} else if (this.args.length < 1) {
			msg(ChatColor.RED + "/avm to <class> [player]");
			return;
		}
		if (arg(0, "bunny", "rabit")) {
			AVM.getGame().toAnimal(player, BunnyPlayer.class);
		} else if (arg(0, "pig")) {
			AVM.getGame().toAnimal(player, PigPlayer.class);
		} else if (arg(0, "wolf")) {
			AVM.getGame().toAnimal(player, WolfPlayer.class);
		} else if (arg(0, "enderman", "ender")) {
			AVM.getGame().toMonster(player, EnderPlayer.class);
		} else if (arg(0, "skeleton", "skelly")) {
			AVM.getGame().toMonster(player, SkellyPlayer.class);
		} else if (arg(0, "zombie")) {
			AVM.getGame().toMonster(player, ZombiePlayer.class);
		} else if (arg(0, "spectate")) {
			AVM.getGame().toOther(player, GuiType.SPECTATE);
		} else if (arg(0, "unlock", "unlocked", "free", "nothing")) {
			AVM.getGame().toOther(player, GuiType.NONE);
		} else if (arg(0, "none", "main")) {
			AVM.getGame().registerPlayer(player);
		} else {
			player.sendMessage(ChatColor.RED + this.args[0] + " is not a valid player class.");
			return;
		}
		if (!player.getName().equalsIgnoreCase(sender.getName()))
			player.sendMessage(ChatColor.YELLOW + "Class changed to "
					+ AVM.getGame().getPlayer(player).getDisguiseType().toString());
	}

	/**
	 * Unused at the moment.
	 */
	public void help() {
	}
}