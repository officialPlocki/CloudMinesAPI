package eu.plugincloud.cloudminesapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.plugincloud.core.utils.Language;
import eu.plugincloud.core.utils.ranking.Manager;

public class autosell implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player)arg0;
		Manager.toggleAutoSell(p);
		if(Manager.AutoSellIsEnabled(p)) {
			p.sendMessage(Language.prefix+"AutoSell wurde deaktiviert.");
		} else {
			p.sendMessage(Language.prefix+"AutoSell wurde aktiviert.");
		}
		return false;
	}

}
