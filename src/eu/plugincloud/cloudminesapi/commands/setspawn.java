package eu.plugincloud.cloudminesapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.plugincloud.core.utils.Language;
import eu.plugincloud.core.utils.Position;

public class setspawn implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(arg0.hasPermission("core.setspawn")) {
			Position.saveLocation("Spawn", ((Player)arg0).getLocation());
		} else {
			arg0.sendMessage(Language.noperm);
		}
		
		return false;
	}

}
