package eu.plugincloud.cloudminesapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.plugincloud.core.utils.Language;

public class ec implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player)arg0;
		
		if(p.hasPermission("core.ec")) {
			p.openInventory(p.getEnderChest());
		} else {
			p.sendMessage(Language.noperm);
		}
		return false;
	}

}
