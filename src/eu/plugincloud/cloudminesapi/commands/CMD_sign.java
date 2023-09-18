package eu.plugincloud.cloudminesapi.commands;

import eu.plugincloud.core.utils.Language;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class CMD_sign
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("core.sign")) {
                if (args.length >= 1) {
                    if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
                        ItemStack itemnew = p.getItemInHand();
                        ItemMeta itemnewmeta = itemnew.getItemMeta();
                        try {
                            if (itemnewmeta.hasLore()) {
                                if (!((String) itemnewmeta.getLore().get(2)).contains("§7Signiert")) {
                                    String text = "";
                                    for (int i = 0; i < args.length; i++) {
                                        text = String.valueOf(String.valueOf(text)) + args[i] + " ";
                                    }
                                    p.sendMessage(Language.prefix+ "§7Das Item wurde signiert.");
                                    Date date = new Date(System.currentTimeMillis());
                                    String mm_dd_yyyy = (new SimpleDateFormat("dd.MM.yyyy")).format(date);
                                    itemnewmeta.setLore(Arrays.asList(new String[]{" ", text.replaceAll("&", "§"), "§7Signiert von §6" + p.getName() + " §7am §e" + mm_dd_yyyy}));
                                    itemnew.setItemMeta(itemnewmeta);
                                } else {
                                    p.sendMessage(Language.prefix+ "Das Item ist bereits signiert!");
                                }
                            } else {
                                String text = "";
                                for (int i = 0; i < args.length; i++) {
                                    text = String.valueOf(String.valueOf(text)) + args[i] + " ";
                                }
                                p.sendMessage(Language.prefix+ "§7Das Item wurde signiert.");
                                Date date = new Date(System.currentTimeMillis());
                                String mm_dd_yyyy = (new SimpleDateFormat("dd.MM.yyyy")).format(date);
                                itemnewmeta.setLore(Arrays.asList(new String[]{" ", text.replaceAll("&", "§"), "§7Signiert von §6" + p.getName() + " §7am §e" + mm_dd_yyyy}));
                                itemnew.setItemMeta(itemnewmeta);
                                p.setItemInHand(itemnew);
                            }

                        } catch (Exception exception) {
                        }
                    } else {
                        p.sendMessage(Language.prefix+ "§cBitte gehe auf ein Item!");
                    }
                } else {
                    p.sendMessage(Language.prefix+ "§cBenutze: /sign <Text>");
                }
            } else {
                p.sendMessage(Language.noperm);
            }
        }
        return true;
    }
}