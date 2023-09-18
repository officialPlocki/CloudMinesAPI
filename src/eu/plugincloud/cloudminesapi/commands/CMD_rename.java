package eu.plugincloud.cloudminesapi.commands;

import eu.plugincloud.core.utils.Language;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class CMD_rename
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("core.rename")) {
                if (args.length == 1) {
                    String format = args[0];
                    if (p.getItemInHand().getType() != Material.AIR) {
                        ItemStack current = renameItem(p.getItemInHand(), format);
                        p.getInventory().remove(p.getItemInHand());
                        p.getInventory().addItem(new ItemStack[]{p.getItemOnCursor(), current});
                        p.sendMessage(Language.prefix + "§7Du hast das Item umbenannt.");
                    } else {
                        p.sendMessage(Language.prefix+ "Bitte halte ein Item in deiner Hand!");
                    }
                } else {
                    p.sendMessage(Language.prefix+ "Bitte benutze: /rename <Text>");
                }
            } else {
                p.sendMessage(Language.noperm);
            }
        }
        return true;
    }

    public String formatAll(String format) {
        format = format.replaceAll("&", "§");
        return format;
    }

    public ItemStack renameItem(ItemStack item, String format) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(formatAll(format));
        item.setItemMeta(meta);
        return item;
    }
}