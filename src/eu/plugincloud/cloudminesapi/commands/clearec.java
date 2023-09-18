package eu.plugincloud.cloudminesapi.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.plugincloud.core.utils.Language;

public class clearec implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player)arg0;
		
		if(p.hasPermission("core.clearec")) {
			if(arg3.length==0) {
				p.getEnderChest().clear();
				p.sendMessage(Language.prefix+"Du hast deine Enderchest erfolgreich geleert.");
				return true;
			} else if(arg3.length==1) {
				if(Bukkit.getPlayer(arg3[0]).isOnline()) {
					Bukkit.getPlayer(arg3[0]).getEnderChest().clear();
					p.sendMessage(Language.prefix+"Du hast die Enderchest von "+arg3[0]+" erfolgreich geleert.");
					return true;
				} else {
					Bukkit.getOfflinePlayer(arg3[0]).getPlayer().getEnderChest().clear();
					p.sendMessage(Language.prefix+"Du hast die Enderchest von "+arg3[0]+" erfolgreich geleert.");
					return true;
				}
			}
		} else {
			p.sendMessage(Language.noperm);
		}
		
		return false;
	}

}
