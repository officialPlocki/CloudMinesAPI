package eu.plugincloud.cloudminesapi.commands;

import eu.plugincloud.core.utils.Language;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_sun
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        Player p = (Player) sender;
        if (p.hasPermission("core.sun")) {
            p.sendMessage(Language.prefix+ "Das Wetter wurde auf �eSonne�7 gestellt!");
            p.getWorld().setWeatherDuration(1);
            return false;
        }
        p.sendMessage(Language.noperm);
        return false;
    }
}