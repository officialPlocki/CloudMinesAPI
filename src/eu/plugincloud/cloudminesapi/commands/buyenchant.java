package eu.plugincloud.cloudminesapi.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.plugincloud.core.utils.Language;
import eu.plugincloud.core.utils.economy.CoinsAPI;

public class buyenchant implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(CoinsAPI.getCoins((Player) sender)==10000||CoinsAPI.getCoins((Player) sender)>=10000) {
			
			Player p = (Player) sender;
			
			ItemStack item = p.getItemInHand();
			ItemMeta itemMeta = p.getItemInHand().getItemMeta();
			item.setItemMeta(itemMeta);
			
			if(item.getType().equals(Material.WOODEN_PICKAXE)||item.getType().equals(Material.IRON_PICKAXE)||item.getType().equals(Material.GOLDEN_PICKAXE)||item.getType().equals(Material.DIAMOND_PICKAXE)||item.getType().equals(Material.NETHERITE_PICKAXE)||item.getType().equals(Material.STONE_PICKAXE)) {
				if(item.getEnchantments().containsKey(Enchantment.DIG_SPEED)) {
					
					if(item.getEnchantmentLevel(Enchantment.DIG_SPEED)==0) {
						for(Enchantment e : item.getEnchantments().keySet()) {
						    item.removeEnchantment(e);
						}
						item.addEnchantment(Enchantment.DIG_SPEED, 1);
					} else if(item.getEnchantmentLevel(Enchantment.DIG_SPEED)==1) {
						for(Enchantment e : item.getEnchantments().keySet()) {
						    item.removeEnchantment(e);
						}
						item.addEnchantment(Enchantment.DIG_SPEED, 2);
						item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
					} else if(item.getEnchantmentLevel(Enchantment.DIG_SPEED)==2) {
						for(Enchantment e : item.getEnchantments().keySet()) {
						    item.removeEnchantment(e);
						}
						item.addEnchantment(Enchantment.DIG_SPEED, 3);
						item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
					} else if(item.getEnchantmentLevel(Enchantment.DIG_SPEED)==3) {
						for(Enchantment e : item.getEnchantments().keySet()) {
						    item.removeEnchantment(e);
						}
						item.addEnchantment(Enchantment.DIG_SPEED, 4);
						item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
					} else if(item.getEnchantmentLevel(Enchantment.DIG_SPEED)==4) {
						for(Enchantment e : item.getEnchantments().keySet()) {
						    item.removeEnchantment(e);
						}
						item.addEnchantment(Enchantment.DIG_SPEED, 5);
						item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
					} else if(item.getEnchantmentLevel(Enchantment.DIG_SPEED)>=4) {
						p.sendMessage(Language.prefix+"Deine Spitzhacke hat bereits das höchste Level.");
						return false;
					}
					CoinsAPI.removeCoins(p, 10000);
					p.sendMessage(Language.prefix+"Deine Spitzhacke wurde erfolgreich verzaubert! ");
					p.setItemInHand(item);
				} else {
					item.addEnchantment(Enchantment.DIG_SPEED, 1);
					CoinsAPI.removeCoins(p, 10000);
					p.sendMessage(Language.prefix+"Deine Spitzhacke wurde erfolgreich verzaubert! ");
					p.setItemInHand(item);
				}
				
			} else {
				p.sendMessage(Language.prefix+"Du hälst keine Spitzhacke in der Hand.");
			}
			
			
			
		} else {
			sender.sendMessage(Language.prefix+"Du benötigst "+String.valueOf(10000-CoinsAPI.getCoins((Player) sender)+" Euro mehr um deine Spitzhacke zu verzaubern."));
		}
		
		return false;
	}

}
