package eu.plugincloud.cloudminesapi.utils;

import eu.plugincloud.core.utils.clouds.CloudAPI;
import eu.plugincloud.core.utils.economy.CoinsAPI;
import eu.plugincloud.core.utils.ranking.Manager;
import eu.plugincloud.core.utils.settings.SettingsHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

public class Scoreboard {
	
	private static HashMap<org.bukkit.scoreboard.Scoreboard, Player> boards = new HashMap<>();

	@SuppressWarnings("deprecation")
	public static void update(Plugin plugin) {
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				for(org.bukkit.scoreboard.Scoreboard board : boards.keySet()) {
					if(boards.get(board).isOnline()) {
						Player p = boards.get(board);

						int blocks = 0;
						if(Manager.getRank(p).equalsIgnoreCase("A")) {
							blocks = 10000;
						} else if(Manager.getRank(p).equalsIgnoreCase("B")) {
							blocks = 20000;
						} else if(Manager.getRank(p).equalsIgnoreCase("C")) {
							blocks = 30000;
						} else if(Manager.getRank(p).equalsIgnoreCase("D")) {
							blocks = 40000;
						} else if(Manager.getRank(p).equalsIgnoreCase("E")) {
							blocks = 50000;
						} else if(Manager.getRank(p).equalsIgnoreCase("F")) {
							blocks = 60000;
						} else if(Manager.getRank(p).equalsIgnoreCase("G")) {
							blocks = 70000;
						} else if(Manager.getRank(p).equalsIgnoreCase("H")) {
							blocks = 80000;
						} else if(Manager.getRank(p).equalsIgnoreCase("I")) {
							blocks = 90000;
						} else if(Manager.getRank(p).equalsIgnoreCase("J")) {
							blocks = 10000;
						} else if(Manager.getRank(p).equalsIgnoreCase("K")) {
							blocks = 110000;
						} else if(Manager.getRank(p).equalsIgnoreCase("L")) {
							blocks = 120000;
						} else if(Manager.getRank(p).equalsIgnoreCase("M")) {
							blocks = 130000;
						} else if(Manager.getRank(p).equalsIgnoreCase("N")) {
							blocks = 140000;
						} else if(Manager.getRank(p).equalsIgnoreCase("OP")) {
							blocks = 150000;
						}
						 	
						String s = "";
						if(!Manager.getRank(p).equalsIgnoreCase("OP")) {
							s = " §7(§e"+Manager.getBlocks(p)+"§7/§8"+blocks+"§7)";
						} else {
							s = "";
						}
						 	
						board.getTeam("clouds").setSuffix(""+CloudAPI.getClouds(p));
						board.getTeam("euro").setSuffix(""+CoinsAPI.getVisualCoins(p));
						board.getTeam("ranking").setSuffix("§d"+Manager.getRank(p) + s);
						
						s = "";
						blocks = 0;
					}
				}
			}
		}, 0, 40);
	}
	
    public static void sendScoreboard(Player p){
        int blocks = 0;
    	if(Manager.getRank(p).equalsIgnoreCase("A")) {
			blocks = 10000;
		} else if(Manager.getRank(p).equalsIgnoreCase("B")) {
			blocks = 20000;
		} else if(Manager.getRank(p).equalsIgnoreCase("C")) {
			blocks = 30000;
		} else if(Manager.getRank(p).equalsIgnoreCase("D")) {
			blocks = 40000;
		} else if(Manager.getRank(p).equalsIgnoreCase("E")) {
			blocks = 50000;
		} else if(Manager.getRank(p).equalsIgnoreCase("F")) {
			blocks = 60000;
		} else if(Manager.getRank(p).equalsIgnoreCase("G")) {
			blocks = 70000;
		} else if(Manager.getRank(p).equalsIgnoreCase("H")) {
			blocks = 80000;
		} else if(Manager.getRank(p).equalsIgnoreCase("I")) {
			blocks = 90000;
		} else if(Manager.getRank(p).equalsIgnoreCase("J")) {
			blocks = 10000;
		} else if(Manager.getRank(p).equalsIgnoreCase("K")) {
			blocks = 110000;
		} else if(Manager.getRank(p).equalsIgnoreCase("L")) {
			blocks = 120000;
		} else if(Manager.getRank(p).equalsIgnoreCase("M")) {
			blocks = 130000;
		} else if(Manager.getRank(p).equalsIgnoreCase("N")) {
			blocks = 140000;
		} else if(Manager.getRank(p).equalsIgnoreCase("OP")) {
			blocks = 150000;
		}
    	
    	String s = "";
    	if(!Manager.getRank(p).equalsIgnoreCase("OP")) {
    		s = " §7(§e"+Manager.getBlocks(p)+"§7/§8"+blocks+"§7)";
    	} else {
    		s = "";
    	}
    	
    	org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    	
    	Objective obj = board.registerNewObjective("aaa", "bbb");
    	obj.setDisplaySlot(DisplaySlot.SIDEBAR);
    	obj.setDisplayName("§8»§b§lPlugin§f§lCloud§8«");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.getScore("§f").setScore(12);
        obj.getScore("§7Name").setScore(11);
        obj.getScore("§e» "+p.getName()).setScore(10);
        obj.getScore("§a").setScore(9);
        obj.getScore("§7Ranking").setScore(8);
        obj.getScore("§k").setScore(6);
        obj.getScore("§7Euro").setScore(5);
        obj.getScore("§e").setScore(3);
        obj.getScore("§7Wolken").setScore(2);
        obj.getScore("§1").setScore(0);
        
        Team ranking = board.registerNewTeam("ranking");
        ranking.setPrefix("§d» ");
        ranking.setSuffix("§d"+Manager.getRank(p) + s);
        ranking.addEntry(ChatColor.BLACK.toString());
        
        Team euro = board.registerNewTeam("euro");
        euro.setPrefix("§f» ");
        euro.setSuffix(""+CoinsAPI.getVisualCoins(p));
        euro.addEntry(ChatColor.RED.toString());
        
        Team clouds = board.registerNewTeam("clouds");
        clouds.setPrefix("§a» ");
        clouds.setSuffix(""+CloudAPI.getClouds(p));
        clouds.addEntry(ChatColor.AQUA.toString());
        
        obj.getScore(ChatColor.AQUA.toString()).setScore(1);
        obj.getScore(ChatColor.RED.toString()).setScore(4);
        obj.getScore(ChatColor.BLACK.toString()).setScore(7);
        
        boards.put(board, p);
        p.setScoreboard(board);
    	
        s = "";
        
        blocks = 0;
        
    }
}
