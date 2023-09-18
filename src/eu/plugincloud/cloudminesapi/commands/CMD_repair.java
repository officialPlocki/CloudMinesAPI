package eu.plugincloud.cloudminesapi.commands;

import eu.plugincloud.core.utils.Language;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class CMD_repair
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("core.repair")) {
                if (args.length == 0) {
                    if (p.getItemInHand().getType() != Material.AIR) {
                        ItemStack current = p.getItemInHand();
                        current.setDurability((short) 0);
                        p.getInventory().setItemInHand(current);
                        p.sendMessage(Language.prefix+ "§7Das Item §e" + p.getItemInHand().getType() + " §7wurde repariert.");
                    } else {
                        p.sendMessage(Language.prefix+ "Bitte gehe auf ein Item!");
                    }
                } else {
                    p.sendMessage(Language.prefix+ "§cBitte benutze: /repair");
                }
            } else {
                p.sendMessage(Language.noperm);
            }
        }
        return true;
    }
}