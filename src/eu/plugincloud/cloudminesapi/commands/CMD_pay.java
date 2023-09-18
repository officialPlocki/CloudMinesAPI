package eu.plugincloud.cloudminesapi.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.plugincloud.core.utils.Language;
import eu.plugincloud.core.utils.economy.CoinsAPI;


public class CMD_pay
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage(Language.prefix + "Bitte benutze: /pay <Spieler> <Anzahl>");
                return false;
            }
            if (args.length == 1) {
                p.sendMessage(Language.prefix+ "Bitte benutze: /pay <Spieler> <Anzahl>");
                return false;
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("*")) {
                    if (CoinsAPI.getCoins(p) == Bukkit.getOnlinePlayers().size() * Double.valueOf(args[1]).intValue() || CoinsAPI.getCoins(p) >= Bukkit.getOnlinePlayers().size() * Double.valueOf(args[1]).intValue()) {
                        p.sendMessage(Language.prefix + "Du hast erfolgreich §e" + args[1] + "€§7 an jeden Spieler gegeben!");
                        CoinsAPI.removeCoins(p, Double.valueOf(Bukkit.getOnlinePlayers().size() * Double.valueOf(args[1]).intValue()));
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            CoinsAPI.addCoins(all, Double.valueOf(args[1]));
                            all.sendMessage(Language.prefix+ "Du hast von §b" + p.getName() + " §e" + args[1] + "€" + "§7 erhalten!");
                        }
                        return true;
                    }
                    p.sendMessage(Language.prefix+ "Du hast nicht genügend Geld!");
                    return false;
                }

                if (!Bukkit.getPlayer(args[0]).isOnline()) {
                    p.sendMessage(Language.prefix+"Der Spieler ist nicht online.");
                    return false;
                }
                if (Bukkit.getPlayer(args[0]).isOnline()) {
                    if (CoinsAPI.getCoins(p) == Double.valueOf(args[1]) || CoinsAPI.getCoins(p) >= Double.valueOf(args[1]).intValue()) {
                        p.sendMessage(Language.prefix+ "Du hast erfolgreich §e" + args[1] + "€§7 an §b" + args[0] + " §7gegeben!");
                        Bukkit.getPlayer(args[0]).sendMessage(Language.prefix+ "Du hast von §b" + p.getName() + " §e" + args[1] + "€§7 erhalten!");
                        CoinsAPI.removeCoins(p, Double.valueOf(args[1]));
                        CoinsAPI.addCoins(Bukkit.getPlayer(args[0]), Double.valueOf(args[1]));
                        return true;
                    }
                    p.sendMessage(Language.prefix+ "Du hast nicht genügend Geld!");
                    return false;
                }

                p.sendMessage(Language.prefix+"Der Spieler ist nicht online.");
                return false;
            }

            p.sendMessage(Language.prefix+ "Bitte benutze: /pay <Spieler> <Anzahl>");
            return false;
        }

        return false;
    }
}