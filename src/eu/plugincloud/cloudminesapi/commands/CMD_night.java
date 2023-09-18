package eu.plugincloud.cloudminesapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.plugincloud.core.utils.Language;

public class CMD_night
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        Player p = (Player) sender;
        if (p.hasPermission("core.night")) {
            p.sendMessage(Language.prefix+"Die Zeit wurde auf Nacht gestellt!");
            p.getWorld().setTime(20000L);
            return false;
        }
        p.sendMessage(Language.noperm);
        return false;
    }
}