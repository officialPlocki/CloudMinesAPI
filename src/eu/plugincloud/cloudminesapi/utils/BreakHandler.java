package eu.plugincloud.cloudminesapi.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import eu.plugincloud.core.utils.ConfigManager;
import eu.plugincloud.core.utils.economy.CoinsAPI;
import eu.plugincloud.core.utils.ranking.Manager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class BreakHandler implements Listener{
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(Bukkit.getServer().getMotd().contains("mine")) {
			Material type = e.getBlock().getType();
			if(type==Material.COAL_ORE||type==Material.DIAMOND_ORE||type==Material.EMERALD_ORE||type==Material.GOLD_ORE||type==Material.IRON_ORE||type==Material.OBSIDIAN||type==Material.COAL_BLOCK||type==Material.GOLD_BLOCK||type==Material.EMERALD_BLOCK||type==Material.IRON_BLOCK||type==Material.DIAMOND_BLOCK||type==Material.STONE||type==Material.COBBLESTONE) {
				Manager.setBlocks(e.getPlayer(), Manager.getBlocks(e.getPlayer())+1);
				if(ConfigManager.yml.getBoolean("AutoSell."+e.getPlayer().getName().toString())) {
					
					Player p = e.getPlayer();
					
					if(type == Material.COBBLESTONE) {
						CoinsAPI.addCoins(e.getPlayer(), 1.75);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e1,75 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.COAL_ORE) {
						CoinsAPI.addCoins(e.getPlayer(), 41.38);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e41,38 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.DIAMOND_ORE) {
						CoinsAPI.addCoins(e.getPlayer(), 44.68);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e44,68 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.EMERALD_ORE) {
						CoinsAPI.addCoins(e.getPlayer(), 45.43);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e45,43 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.GOLD_ORE) {
						CoinsAPI.addCoins(e.getPlayer(), 43.48);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e43,48 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.IRON_ORE) {
						CoinsAPI.addCoins(e.getPlayer(), 42.19);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e42,19 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.OBSIDIAN) {
						CoinsAPI.addCoins(e.getPlayer(), 1200.12);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e1200,12 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.COAL_BLOCK) {
						CoinsAPI.addCoins(e.getPlayer(), 372.42);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e372,42 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.GOLD_BLOCK) {
						CoinsAPI.addCoins(e.getPlayer(), 391.32);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e391,32 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.EMERALD_BLOCK) {
						CoinsAPI.addCoins(e.getPlayer(), 408.87);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e408,87 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.IRON_BLOCK) {
						CoinsAPI.addCoins(e.getPlayer(), 379.71);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e379,71 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.DIAMOND_BLOCK) {
						CoinsAPI.addCoins(e.getPlayer(), 402.12);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e402,12 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.STONE) {
						CoinsAPI.addCoins(e.getPlayer(), 41.12);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e41,12 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.DIAMOND) {
						CoinsAPI.addCoins(e.getPlayer(), 45.68);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e45,68 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.EMERALD) {
						CoinsAPI.addCoins(e.getPlayer(), 45.43);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e45,43 §7Euro erhalten."));
						
						e.setDropItems(false);
					} else if(type == Material.COAL) {
						CoinsAPI.addCoins(e.getPlayer(), 41.38);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b§lPlugin§f§lCloud §8» §7Du hast §e41,38 §7Euro erhalten."));
						
						e.setDropItems(false);
					}
				} else {
					e.setCancelled(false);
					e.setDropItems(true);
				}
			}
		} else {
			e.setCancelled(false);
			e.setDropItems(true);
		}
		
	}

}
