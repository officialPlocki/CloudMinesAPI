package eu.plugincloud.cloudminesapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.plugincloud.core.utils.Language;

public class fly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		Player p = (Player)sender;
		
		if(p.hasPermission("core.fly")) {
			if(p.getAllowFlight()) {
				p.setAllowFlight(false);
				p.sendMessage(Language.prefix+"Du hast deinen Fly Modus deaktiviert.");
			} else {
				p.setAllowFlight(true);
				p.sendMessage(Language.prefix+"Du hast deinen Fly Modus aktiviert.");
			}
		} else {
			p.sendMessage(Language.noperm);
		}
		
		return false;
	}

}
