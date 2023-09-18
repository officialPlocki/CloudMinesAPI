package eu.plugincloud.cloudminesapi.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import eu.plugincloud.cloudminesapi.commands.CMD_cc;
import eu.plugincloud.cloudminesapi.commands.CMD_day;
import eu.plugincloud.cloudminesapi.commands.CMD_freeze;
import eu.plugincloud.cloudminesapi.commands.CMD_heal;
import eu.plugincloud.cloudminesapi.commands.CMD_kopf;
import eu.plugincloud.cloudminesapi.commands.CMD_night;
import eu.plugincloud.cloudminesapi.commands.CMD_pay;
import eu.plugincloud.cloudminesapi.commands.CMD_rename;
import eu.plugincloud.cloudminesapi.commands.CMD_repair;
import eu.plugincloud.cloudminesapi.commands.CMD_sign;
import eu.plugincloud.cloudminesapi.commands.CMD_sun;
import eu.plugincloud.cloudminesapi.commands.CMD_wb;
import eu.plugincloud.cloudminesapi.commands.autosell;
import eu.plugincloud.cloudminesapi.commands.buyenchant;
import eu.plugincloud.cloudminesapi.commands.clearec;
import eu.plugincloud.cloudminesapi.commands.ec;
import eu.plugincloud.cloudminesapi.commands.fly;
import eu.plugincloud.cloudminesapi.commands.setspawn;
import eu.plugincloud.cloudminesapi.utils.BreakHandler;
import eu.plugincloud.cloudminesapi.utils.Navigator;
import eu.plugincloud.cloudminesapi.utils.Scoreboard;
import xyz.xplugins.devapi.utils.entity.EntityBuilder;
import xyz.xplugins.devapi.utils.entity.EntityBuilder.EntityAge;

public class Main extends JavaPlugin {
	
	private static Plugin instance;
	
	
	public static Entity buildEntity(EntityType entityType, Location location) {
		EntityBuilder e = new EntityBuilder(entityType, location)
				.setAge(EntityAge.ADULT)
				.setCustomName(name)
				.setCustomNameVisible(false)
				.setPassenger(passenger)
				.spawn();
	}
	
	public void onEnable() {
		instance = this;
		//commands
		if(Bukkit.getMotd().contains("cb")||Bukkit.getMotd().contains("mine")||Bukkit.getMotd().contains("hub")) {

			getCommand("clearec").setExecutor(new clearec());
			getCommand("cc").setExecutor(new CMD_cc());
			getCommand("clearchat").setExecutor(new CMD_cc());
			getCommand("chatclear").setExecutor(new CMD_cc());
			getCommand("day").setExecutor(new CMD_day());
			getCommand("freeze").setExecutor(new CMD_freeze());
			getCommand("heal").setExecutor(new CMD_heal());
			getCommand("kopf").setExecutor(new CMD_kopf());
			getCommand("skull").setExecutor(new CMD_kopf());
			getCommand("night").setExecutor(new CMD_night());
			getCommand("pay").setExecutor(new CMD_pay());
			getCommand("rename").setExecutor(new CMD_rename());
			getCommand("repair").setExecutor(new CMD_repair());
			getCommand("sign").setExecutor(new CMD_sign());
			getCommand("sun").setExecutor(new CMD_sun());
			getCommand("wb").setExecutor(new CMD_wb());
			getCommand("workbench").setExecutor(new CMD_wb());
			getCommand("ec").setExecutor(new ec());
			getCommand("enderchest").setExecutor(new ec());
			getCommand("fly").setExecutor(new fly());
			getCommand("setspawn").setExecutor(new setspawn());
			getCommand("autosell").setExecutor(new autosell());
			getCommand("minesshop").setExecutor(new Navigator());
			getCommand("buyenchant").setExecutor(new buyenchant());
			
			//Listener
			
			Bukkit.getPluginManager().registerEvents(new BreakHandler(), this);
			Bukkit.getPluginManager().registerEvents(new Navigator(), this);
			Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule announceAdvancements false");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule showDeathMessages false");
			Scoreboard.update(instance);
		} else {
			Bukkit.getPluginManager().disablePlugin(this);
		}
		
	}
	
	public static Plugin getInstance() {
		return instance;
	}
	
}
