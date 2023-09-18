package eu.plugincloud.cloudminesapi.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;

import eu.plugincloud.core.Main.PluginCloudAPI;
import eu.plugincloud.core.utils.BungeeCordHandler;
import eu.plugincloud.core.utils.Language;
import eu.plugincloud.core.utils.economy.CoinsAPI;
import eu.plugincloud.core.utils.ranking.Manager;
import net.craftersland.data.bridge.api.events.InventoryArmorSyncCompleteEvent;

public class Navigator implements Listener, CommandExecutor {

	public static HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	private static boolean getCooldown(Player p) {
		int cooldownTime = 5;
		if (cooldowns.containsKey(p.getName())) {
			long secondsLeft = ((cooldowns.get(p.getName()) / 1000) + cooldownTime)
					- (System.currentTimeMillis() / 1000);
			if (secondsLeft > 0) {
				return true;
			}
		}
		cooldowns.put(p.getName(), System.currentTimeMillis());
		return false;
	}

	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlockDamage(EntityDamageByBlockEvent e) {
		e.setCancelled(true);
		if (!e.getEntityType().equals(EntityType.PLAYER)) {
			return;
		}
		e.getEntity().teleport(PluginCloudAPI.getAPI().getPositions().getLocation("Spawn"));
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getCause().equals(DamageCause.FALL)) {
			if (!e.getEntityType().equals(EntityType.PLAYER)) {
				return;
			}
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		e.getPlayer().getLocation().getWorld().setThunderDuration(0);
		e.getPlayer().getLocation().getWorld().setTime(2000);
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		e.getPlayer().teleport(PluginCloudAPI.getAPI().getPositions().getLocation("Spawn"));
	}

	@EventHandler
	public void onLoad(InventoryArmorSyncCompleteEvent e) {
		setInventory(e.getPlayer());
		e.getPlayer().setFoodLevel(9999);
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		setInventory(e.getPlayer());
		e.getPlayer().teleport(PluginCloudAPI.getAPI().getPositions().getLocation("Spawn"));
		Manager.getBlocks(e.getPlayer());
		Manager.getRank(e.getPlayer());
		e.getPlayer().sendMessage("");
		e.getPlayer().sendMessage("");
		e.getPlayer().sendMessage("");
		e.getPlayer().sendMessage("");
		e.getPlayer().sendMessage("");
		e.getPlayer().sendMessage("");
		e.getPlayer().sendMessage("");
		e.getPlayer().sendMessage("");
		e.getPlayer().sendMessage("");
		e.getPlayer().sendMessage("");
		if (Bukkit.getMotd().equals("cbnormal")) {
			e.getPlayer().sendMessage("§6Willkommen auf §eCityBuild §7Standard§6!");
		} else if (Bukkit.getMotd().contains("cbexpert")) {
			e.getPlayer().sendMessage("§6Willkommen auf §eCityBuild §bExpert§6!");
		} else if (Bukkit.getMotd().contains("cbmaster")) {
			e.getPlayer().sendMessage("§6Willkommen auf §eCityBuild §5Master§6!");
		} else if (Bukkit.getMotd().contains("cblegend")) {
			e.getPlayer().sendMessage("§6Willkommen auf §eCityBuild §6Legend§6!");
		} else if (Bukkit.getMotd().contains("cbhyper")) {
			e.getPlayer().sendMessage("§6Willkommen auf §eCityBuild §cHyper§6!");
		} else if (Bukkit.getMotd().contains("cbfree")) {
			e.getPlayer().sendMessage("§6Willkommen in der §aFreiheit§6!");
		} else if (Bukkit.getMotd().contains("hub")) {
			e.getPlayer().sendMessage("§6Willkommen auf der §eHub§6!");
		} else if (Bukkit.getMotd().contains("minecoal")) {
			e.getPlayer().sendMessage("§6Willkommen in der §7Kohle §eMine§6!");
		} else if (Bukkit.getMotd().contains("mineiron")) {
			e.getPlayer().sendMessage("§6Willkommen in der §fEisen §eMine§6!");
		} else if (Bukkit.getMotd().contains("minegold")) {
			e.getPlayer().sendMessage("§6Willkommen in der §6Gold §eMine§6!");
		} else if (Bukkit.getMotd().contains("minediamond")) {
			e.getPlayer().sendMessage("§6Willkommen in der §bDiamant §eMine§6!");
		} else if (Bukkit.getMotd().contains("mineemerald")) {
			e.getPlayer().sendMessage("§6Willkommen in der §aSmaragd §eMine!");
		}

		e.getPlayer().sendMessage("");
		e.getPlayer().sendMessage("");
		Scoreboard.sendScoreboard(e.getPlayer());

	}

	@EventHandler
	public void onSwitch(PlayerSwapHandItemsEvent e) {
		if (e.getOffHandItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bNavigator")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase("§bNavigator")) {
			e.setCancelled(true);
		}
	}

	public static void setInventory(Player p) {
		ItemStack nav = new ItemStack(Material.NETHER_STAR);
		ItemMeta navMeta = nav.getItemMeta();
		navMeta.setDisplayName("§bNavigator");
		nav.setItemMeta(navMeta);
		p.getInventory().setItem(8, nav);

	}

	private static List<String> free = new ArrayList<String>();
	private static List<String> buyCBExpert = new ArrayList<String>();
	private static List<String> buyCBMaster = new ArrayList<String>();
	private static List<String> buyCBLegend = new ArrayList<String>();
	private static List<String> buyCBHyper = new ArrayList<String>();
	private static List<String> buyMineIron = new ArrayList<String>();
	private static List<String> buyMineGold = new ArrayList<String>();
	private static List<String> buyMineDiamond = new ArrayList<String>();
	private static List<String> buyMineEmerald = new ArrayList<String>();
	private static List<String> buyCBFree = new ArrayList<String>();
	private static List<String> tp = new ArrayList<String>();

	private static List<String> b = new ArrayList<String>();
	private static List<String> c = new ArrayList<String>();
	private static List<String> d = new ArrayList<String>();
	private static List<String> e = new ArrayList<String>();
	private static List<String> f = new ArrayList<String>();
	private static List<String> g = new ArrayList<String>();
	private static List<String> h = new ArrayList<String>();
	private static List<String> i = new ArrayList<String>();
	private static List<String> j = new ArrayList<String>();
	private static List<String> k = new ArrayList<String>();
	private static List<String> l = new ArrayList<String>();
	private static List<String> m = new ArrayList<String>();
	private static List<String> n = new ArrayList<String>();
	private static List<String> op = new ArrayList<String>();

	public static void openTeleportInventory(Player p) {
		p.closeInventory();
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "§bNavigator");
		ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName("§k");
		glass.setItemMeta(glassMeta);
		inv.setItem(0, glass);
		inv.setItem(1, glass);
		inv.setItem(2, glass);
		inv.setItem(3, glass);
		inv.setItem(4, glass);
		inv.setItem(5, glass);
		inv.setItem(6, glass);
		inv.setItem(7, glass);
		inv.setItem(8, glass);
		inv.setItem(9, glass);
		inv.setItem(10, glass);
		inv.setItem(11, glass);
		inv.setItem(12, glass);
		inv.setItem(13, glass);
		inv.setItem(14, glass);
		inv.setItem(15, glass);
		inv.setItem(16, glass);
		inv.setItem(17, glass);
		inv.setItem(18, glass);
		inv.setItem(19, glass);
		inv.setItem(20, glass);
		inv.setItem(21, glass);
		inv.setItem(22, glass);
		inv.setItem(23, glass);
		inv.setItem(24, glass);
		inv.setItem(25, glass);
		inv.setItem(26, glass);
		// general

		b.add("§8» §cDu benötigst erst das Level §aB §cum dir das Kaufen zu können.");
		c.add("§8» §cDu benötigst erst das Level §aC §cum dir das Kaufen zu können.");
		d.add("§8» §cDu benötigst erst das Level §aD §cum dir das Kaufen zu können.");
		e.add("§8» §cDu benötigst erst das Level §aE §cum dir das Kaufen zu können.");
		f.add("§8» §cDu benötigst erst das Level §aF §cum dir das Kaufen zu können.");
		g.add("§8» §cDu benötigst erst das Level §aG §cum dir das Kaufen zu können.");
		h.add("§8» §cDu benötigst erst das Level §aH §cum dir das Kaufen zu können.");
		i.add("§8» §cDu benötigst erst das Level §aI §cum dir das Kaufen zu können.");
		j.add("§8» §cDu benötigst erst das Level §aJ §cum dir das Kaufen zu können.");
		k.add("§8» §cDu benötigst erst das Level §aK §cum dir das Kaufen zu können.");
		l.add("§8» §cDu benötigst erst das Level §aL §cum dir das Kaufen zu können.");
		m.add("§8» §cDu benötigst erst das Level §aM §cum dir das Kaufen zu können.");
		n.add("§8» §cDu benötigst erst das Level §aN §cum dir das Kaufen zu können.");
		op.add("§8» §cDu benötigst erst das Level §aOP §cum dir das Kaufen zu können.");

		// other

		free.add("§8» §eFür 0 Kaufen §8«");

		tp.add("§8» §eJetzt Teleportieren §8«");

		// cbs

		buyCBExpert.add("§8» §eFür 250.000 Kaufen §8«");

		buyCBMaster.add("§8» §eFür 500.000 Kaufen §8«");

		buyCBLegend.add("§8» §eFür 1.000.000 Kaufen §8«");

		buyCBHyper.add("§8» §eFür 2.000.000 Kaufen §8«");

		buyCBFree.add("§8» §eFür 4.000.000 Kaufen §8«");

		// mines

		buyMineIron.add("§8» §eFür 100.000 Kaufen §8«");

		buyMineGold.add("§8» §eFür 200.000 Kaufen §8«");

		buyMineDiamond.add("§8» §eFür 400.000 Kaufen §8«");

		buyMineEmerald.add("§8» §eFür 800.000 Kaufen §8«");

		// items

		ItemStack shop = new ItemStack(Material.CHEST);

		ItemStack cbNormal = new ItemStack(Material.COARSE_DIRT);
		ItemStack cbExpert = new ItemStack(Material.GRASS_BLOCK);
		ItemStack cbMaster = new ItemStack(Material.PODZOL);
		ItemStack cbLegend = new ItemStack(Material.CRIMSON_NYLIUM);
		ItemStack cbHyper = new ItemStack(Material.WARPED_NYLIUM);
		ItemStack cbFree = new ItemStack(Material.NETHER_WART);
		ItemStack hub = new ItemStack(Material.NETHER_STAR);
		ItemStack mineCoal = new ItemStack(Material.COAL);
		ItemStack mineIron = new ItemStack(Material.IRON_INGOT);
		ItemStack mineGold = new ItemStack(Material.GOLD_INGOT);
		ItemStack mineDiamond = new ItemStack(Material.DIAMOND);
		ItemStack mineEmerald = new ItemStack(Material.EMERALD);

		// item metas
		ItemMeta shopMeta = shop.getItemMeta();
		ItemMeta mineCoalMeta = mineCoal.getItemMeta();
		ItemMeta mineIronMeta = mineIron.getItemMeta();
		ItemMeta mineGoldMeta = mineGold.getItemMeta();
		ItemMeta mineDiamondMeta = mineDiamond.getItemMeta();
		ItemMeta mineEmeraldMeta = mineEmerald.getItemMeta();
		ItemMeta cbNormalMeta = cbNormal.getItemMeta();
		ItemMeta cbExpertMeta = cbExpert.getItemMeta();
		ItemMeta cbMasterMeta = cbMaster.getItemMeta();
		ItemMeta cbLegendMeta = cbLegend.getItemMeta();
		ItemMeta cbHyperMeta = cbHyper.getItemMeta();
		ItemMeta cbFreeMeta = cbFree.getItemMeta();
		ItemMeta hubMeta = hub.getItemMeta();

		// item names
		shopMeta.setDisplayName("§6Shop öffnen");
		cbNormalMeta.setDisplayName("§8» §eCityBuild §7Standard");
		cbExpertMeta.setDisplayName("§8» §eCityBuild §bExpert");  //D
		cbMasterMeta.setDisplayName("§8» §eCityBuild §5Master");  //F
		cbLegendMeta.setDisplayName("§8» §eCityBuild §6Legend");  //H
		cbFreeMeta.setDisplayName("§8» §eFreiheit");              //OP
		cbHyperMeta.setDisplayName("§8» §eCityBuild §cHyper");    //J
		hubMeta.setDisplayName("§8» §eHub");

		mineCoalMeta.setDisplayName("§8» §7Kohle §eMine");        
		mineIronMeta.setDisplayName("§8» §fEisen §eMine");        //C
		mineGoldMeta.setDisplayName("§8» §6Gold §eMine");         //E
		mineDiamondMeta.setDisplayName("§8» §bDiamant §eMine");   //G
		mineEmeraldMeta.setDisplayName("§8» §aSmaragd §eMine");   //L

		if (p.hasPermission("cb.normal")) {
			cbNormalMeta.setLore(tp);
		} else {
			cbNormalMeta.setLore(free);
		}

		// sodkopasdkopasd

		if (p.hasPermission("cb.expert")) {
			cbExpertMeta.setLore(tp);
		} else {
			if(!(Manager.getRankNumber(p)==4)||!(Manager.getRankNumber(p)>=4)) {
				cbExpertMeta.setLore(d);
			} else {
				cbExpertMeta.setLore(buyCBExpert);
			}
		}

		// sodkopasdkopasd

		if (p.hasPermission("cb.master")) {
			cbMasterMeta.setLore(tp);
		} else {
			if(!(Manager.getRankNumber(p)==6)||!(Manager.getRankNumber(p)>=6)) {
				cbMasterMeta.setLore(f);
			} else {
				cbMasterMeta.setLore(buyCBMaster);
			}
		}

		// sodkopasdkopasd

		if (p.hasPermission("cb.legend")) {
			cbLegendMeta.setLore(tp);
		} else {
			if(!(Manager.getRankNumber(p)==8)||!(Manager.getRankNumber(p)>=8)) {
				cbLegendMeta.setLore(h);
			} else {
				cbLegendMeta.setLore(buyCBLegend);
			}
		}

		// sodkopasdkopasd

		if (p.hasPermission("cb.hyper")) {
			cbHyperMeta.setLore(tp);
		} else {
			if(!(Manager.getRankNumber(p)==10)||!(Manager.getRankNumber(p)>=10)) {
				cbHyperMeta.setLore(j);
			} else {
				cbHyperMeta.setLore(buyCBHyper);
			}
		}

		// sodkopasdkopasd

		if (p.hasPermission("mine.coal")) {
			mineCoalMeta.setLore(tp);
		} else {
			mineCoalMeta.setLore(free);
		}

		// sodkopasdkopasd

		if (p.hasPermission("mine.iron")) {
			mineIronMeta.setLore(tp);
		} else {
			if(!(Manager.getRankNumber(p)==3)||!(Manager.getRankNumber(p)>=3)) {
				mineIronMeta.setLore(c);
			} else {
				mineIronMeta.setLore(buyMineIron);
			}
		}

		// sodkopasdkopasd

		if (p.hasPermission("mine.gold")) {
			mineGoldMeta.setLore(tp);
		} else {
			if(!(Manager.getRankNumber(p)==5)||!(Manager.getRankNumber(p)>=5)) {
				mineGoldMeta.setLore(e);
			} else {
				mineGoldMeta.setLore(buyMineGold);
			}
		}

		// sodkopasdkopasd

		if (p.hasPermission("mine.diamond")) {
			mineDiamondMeta.setLore(tp);
		} else {
			if(!(Manager.getRankNumber(p)==7)||!(Manager.getRankNumber(p)>=7)) {
				mineDiamondMeta.setLore(g);
			} else {
				mineDiamondMeta.setLore(buyMineDiamond);
			}
		}

		// sodkopasdkopasd

		if (p.hasPermission("mine.emerald")) {
			mineEmeraldMeta.setLore(tp);
		} else {
			if(!(Manager.getRankNumber(p)==12)||!(Manager.getRankNumber(p)>=12)) {
				mineEmeraldMeta.setLore(l);
			} else {
				mineEmeraldMeta.setLore(buyMineEmerald);
			}
		}

		// sodkopasdkopasd

		if (p.hasPermission("cb.free")) {
			cbFreeMeta.setLore(tp);
		} else {
			if(!(Manager.getRankNumber(p)==15)||!(Manager.getRankNumber(p)>=15)) {
				cbFreeMeta.setLore(op);
			} else {
				cbFreeMeta.setLore(buyCBFree);
			}
		}

		hubMeta.setLore(tp);

		// item set meta
		shop.setItemMeta(shopMeta);
		cbNormal.setItemMeta(cbNormalMeta);
		cbExpert.setItemMeta(cbExpertMeta);
		cbMaster.setItemMeta(cbMasterMeta);
		cbLegend.setItemMeta(cbLegendMeta);
		cbFree.setItemMeta(cbFreeMeta);
		cbHyper.setItemMeta(cbHyperMeta);
		hub.setItemMeta(hubMeta);
		mineCoal.setItemMeta(mineCoalMeta);
		mineIron.setItemMeta(mineIronMeta);
		mineGold.setItemMeta(mineGoldMeta);
		mineDiamond.setItemMeta(mineDiamondMeta);
		mineEmerald.setItemMeta(mineEmeraldMeta);

		tp.clear();
		buyCBExpert.clear();
		buyCBFree.clear();
		buyCBHyper.clear();
		buyCBLegend.clear();
		buyCBMaster.clear();
		buyMineDiamond.clear();
		buyMineEmerald.clear();
		buyMineGold.clear();
		buyMineIron.clear();
		free.clear();
		inv.setItem(8, shop);
		inv.setItem(2, cbNormal);
		inv.setItem(3, cbExpert);
		inv.setItem(4, cbMaster);
		inv.setItem(5, cbLegend);
		inv.setItem(6, cbHyper);
		inv.setItem(11, mineCoal);
		inv.setItem(15, mineEmerald);
		inv.setItem(18, hub);
		inv.setItem(21, mineIron);
		inv.setItem(22, mineGold);
		inv.setItem(23, mineDiamond);
		inv.setItem(26, cbFree);
		p.openInventory(inv);
		
		b.clear();
		c.clear();
		d.clear();
		e.clear();
		f.clear();
		g.clear();
		h.clear();
		i.clear();
		j.clear();
		k.clear();
		l.clear();
		m.clear();
		n.clear();
		op.clear();

	}

	static List<String> oakLore = new ArrayList<String>();
	static List<String> spruceLore = new ArrayList<String>();
	static List<String> birchLore = new ArrayList<String>();
	static List<String> jungleLore = new ArrayList<String>();
	static List<String> acaciaLore = new ArrayList<String>();
	static List<String> dark_oakLore = new ArrayList<String>();
	static List<String> basaltLore = new ArrayList<String>();
	static List<String> enchanterLore = new ArrayList<String>();
	static List<String> ecLore = new ArrayList<String>();

	public static void openShopInventory(Player p) {

		oakLore.add("");
		oakLore.add("§eKaufen§8: §b210 Euro");
		oakLore.add("");

		spruceLore.add("");
		spruceLore.add("§eKaufen§8: §b380 Euro");
		spruceLore.add("");

		birchLore.add("");
		birchLore.add("§eKaufen§8: §b420 Euro");
		birchLore.add("");

		jungleLore.add("");
		jungleLore.add("§eKaufen§8: §b540 Euro");
		jungleLore.add("");

		acaciaLore.add("");
		acaciaLore.add("§eKaufen§8: §b660 Euro");
		acaciaLore.add("");

		dark_oakLore.add("");
		dark_oakLore.add("§eKaufen§8: §b730 Euro");
		dark_oakLore.add("");

		basaltLore.add("");
		basaltLore.add("§eKaufen§8: §b370 Euro");
		basaltLore.add("");

		enchanterLore.add("");
		enchanterLore.add("§eKaufen§8: §b1220 Euro");
		enchanterLore.add("");

		ecLore.add("");
		ecLore.add("§eKaufen§8: §b2360 Euro");
		ecLore.add("");

		p.closeInventory();
		try {
			Thread.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.closeInventory();
		Inventory inv = Bukkit.createInventory(null, 6 * 9, "§bNavigator");
		ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName("§k");
		glass.setItemMeta(glassMeta);
		inv.setItem(0, glass);
		inv.setItem(1, glass);
		inv.setItem(2, glass);
		inv.setItem(3, glass);
		inv.setItem(4, glass);
		inv.setItem(5, glass);
		inv.setItem(6, glass);
		inv.setItem(7, glass);
		inv.setItem(8, glass);
		inv.setItem(9, glass);
		inv.setItem(10, glass);
		inv.setItem(11, glass);
		inv.setItem(12, glass);
		inv.setItem(13, glass);
		inv.setItem(14, glass);
		inv.setItem(15, glass);
		inv.setItem(16, glass);
		inv.setItem(17, glass);
		inv.setItem(18, glass);
		inv.setItem(19, glass);
		inv.setItem(20, glass);
		inv.setItem(21, glass);
		inv.setItem(22, glass);
		inv.setItem(23, glass);
		inv.setItem(24, glass);
		inv.setItem(25, glass);
		inv.setItem(26, glass);
		inv.setItem(27, glass);
		inv.setItem(28, glass);
		inv.setItem(29, glass);
		inv.setItem(30, glass);
		inv.setItem(31, glass);
		inv.setItem(32, glass);
		inv.setItem(33, glass);
		inv.setItem(34, glass);
		inv.setItem(35, glass);
		inv.setItem(36, glass);
		inv.setItem(37, glass);
		inv.setItem(38, glass);
		inv.setItem(39, glass);
		inv.setItem(40, glass);
		inv.setItem(41, glass);
		inv.setItem(42, glass);
		inv.setItem(43, glass);
		inv.setItem(44, glass);
		inv.setItem(45, glass);
		inv.setItem(46, glass);
		inv.setItem(47, glass);
		inv.setItem(48, glass);
		inv.setItem(49, glass);
		inv.setItem(50, glass);
		inv.setItem(51, glass);
		inv.setItem(52, glass);
		inv.setItem(53, glass);
		ItemStack oak = new ItemStack(Material.OAK_WOOD);
		ItemStack spruce = new ItemStack(Material.SPRUCE_WOOD);
		ItemStack birch = new ItemStack(Material.BIRCH_WOOD);
		ItemStack jungle = new ItemStack(Material.JUNGLE_WOOD);
		ItemStack acacia = new ItemStack(Material.ACACIA_WOOD);
		ItemStack dark_oak = new ItemStack(Material.DARK_OAK_WOOD);
		ItemStack basalt = new ItemStack(Material.POLISHED_BASALT);
		ItemStack enchanter = new ItemStack(Material.ENCHANTING_TABLE);
		ItemStack ec = new ItemStack(Material.ENDER_CHEST);

		ItemStack soon = new ItemStack(Material.TRIPWIRE_HOOK);

		ItemMeta oakMeta = oak.getItemMeta();
		ItemMeta spruceMeta = spruce.getItemMeta();
		ItemMeta birchMeta = birch.getItemMeta();
		ItemMeta jungleMeta = jungle.getItemMeta();
		ItemMeta acaciaMeta = acacia.getItemMeta();
		ItemMeta dark_oakMeta = dark_oak.getItemMeta();
		ItemMeta basaltMeta = basalt.getItemMeta();
		ItemMeta enchanterMeta = enchanter.getItemMeta();
		ItemMeta ecMeta = ec.getItemMeta();
		ItemMeta soonMeta = soon.getItemMeta();

		oakMeta.setDisplayName("§eEichenstamm §8(16x)");
		spruceMeta.setDisplayName("§eFichtenstamm §8(16x)");
		birchMeta.setDisplayName("§eBirkenstamm §8(16x)");
		jungleMeta.setDisplayName("§eTropenstamm §8(16x)");
		acaciaMeta.setDisplayName("§eAkazienstamm §8(16x)");
		dark_oakMeta.setDisplayName("§eSchwarzeichenstamm §8(16x)");
		basaltMeta.setDisplayName("§eBasalt §8(16x)");
		enchanterMeta.setDisplayName("§eVerzauberungstisch §8(1x)");
		ecMeta.setDisplayName("§eEndertruhe §8(1x)");

		soonMeta.setDisplayName("§cNoch nicht verfügbar.");

		oakMeta.setLore(oakLore);
		spruceMeta.setLore(spruceLore);
		birchMeta.setLore(birchLore);
		jungleMeta.setLore(jungleLore);
		acaciaMeta.setLore(acaciaLore);
		dark_oakMeta.setLore(dark_oakLore);
		basaltMeta.setLore(basaltLore);
		enchanterMeta.setLore(enchanterLore);
		ecMeta.setLore(ecLore);

		oak.setItemMeta(oakMeta);
		spruce.setItemMeta(spruceMeta);
		birch.setItemMeta(birchMeta);
		jungle.setItemMeta(jungleMeta);
		acacia.setItemMeta(acaciaMeta);
		dark_oak.setItemMeta(dark_oakMeta);
		basalt.setItemMeta(basaltMeta);
		enchanter.setItemMeta(enchanterMeta);
		ec.setItemMeta(ecMeta);
		soon.setItemMeta(soonMeta);

		oakLore.clear();
		spruceLore.clear();
		birchLore.clear();
		jungleLore.clear();
		acaciaLore.clear();
		dark_oakLore.clear();
		basaltLore.clear();
		enchanterLore.clear();
		ecLore.clear();

		inv.setItem(10, oak);
		inv.setItem(11, spruce);
		inv.setItem(12, birch);
		inv.setItem(13, jungle);
		inv.setItem(14, acacia);
		inv.setItem(15, dark_oak);
		inv.setItem(16, basalt);
		inv.setItem(19, enchanter);
		inv.setItem(20, ec);

		inv.setItem(37, soon);
		inv.setItem(39, soon);
		inv.setItem(41, soon);
		inv.setItem(43, soon);
		p.openInventory(inv);

	}

	public static void openMainInventory(Player p) {
		p.closeInventory();
		try {
			Thread.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.closeInventory();
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "§bNavigator");
		ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName("§k");
		glass.setItemMeta(glassMeta);
		inv.setItem(0, glass);
		inv.setItem(1, glass);
		inv.setItem(2, glass);
		inv.setItem(3, glass);
		inv.setItem(4, glass);
		inv.setItem(5, glass);
		inv.setItem(6, glass);
		inv.setItem(7, glass);
		inv.setItem(8, glass);
		inv.setItem(9, glass);
		inv.setItem(10, glass);
		inv.setItem(11, glass);
		inv.setItem(12, glass);
		inv.setItem(13, glass);
		inv.setItem(14, glass);
		inv.setItem(15, glass);
		inv.setItem(16, glass);
		inv.setItem(17, glass);
		inv.setItem(18, glass);
		inv.setItem(19, glass);
		inv.setItem(20, glass);
		inv.setItem(21, glass);
		inv.setItem(22, glass);
		inv.setItem(23, glass);
		inv.setItem(24, glass);
		inv.setItem(25, glass);
		inv.setItem(26, glass);
		ItemStack Teleporter = new ItemStack(Material.LAPIS_LAZULI);
		ItemMeta TeleporterMeta = Teleporter.getItemMeta();
		TeleporterMeta.setDisplayName("§8» §eJetzt Teleportieren §8«");
		Teleporter.setItemMeta(TeleporterMeta);

		ItemStack shop = new ItemStack(Material.CHEST);
		ItemMeta shopMeta = shop.getItemMeta();
		shopMeta.setDisplayName("§6Shop");
		shop.setItemMeta(shopMeta);

		inv.setItem(11, Teleporter);
		inv.setItem(15, shop);
		p.openInventory(inv);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bNavigator")) {
			e.setCancelled(true);
		}
			if (e.getView().getTitle().equalsIgnoreCase("§bNavigator")) {
				e.setCancelled(true);
				// main
				
				if(e.getCurrentItem().getItemMeta().getLore().contains("Level")) {
					if(e.getView().getTitle().equalsIgnoreCase("§bNavigator")) {
						e.getWhoClicked().sendMessage(Language.prefix + "Du benötigst ein höheres Level (siehe Beschreibung)!");
						return;
					}
				}
				
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Shop öffnen")) {
					e.getWhoClicked().closeInventory();
					openShopInventory((Player) e.getWhoClicked());
				} else if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase("§8» §eJetzt Teleportieren §8«")) {
					e.getWhoClicked().closeInventory();
					openTeleportInventory((Player) e.getWhoClicked());
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§eEndertruhe")) {

					// shop

					if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 2360
							|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 2360) {
						CoinsAPI.removeCoins((Player) e.getWhoClicked(), 2360);
						e.getWhoClicked().getInventory().addItem(new ItemStack(Material.ENDER_CHEST, 16));
						e.getWhoClicked().sendMessage(Language.prefix + "Der Shop Einkauf war erfolgreich!");
					} else {
						e.getWhoClicked()
								.sendMessage(Language.prefix + "Dir fehlen "
										+ String.valueOf(2360 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
										+ " um dir dieses Item Kaufen zu können.");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase("§eVerzauberungstisch §8(1x)")) {
					if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 1220
							|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 1220) {
						CoinsAPI.removeCoins((Player) e.getWhoClicked(), 1220);
						e.getWhoClicked().getInventory().addItem(new ItemStack(Material.ENCHANTING_TABLE));
						e.getWhoClicked().sendMessage(Language.prefix + "Der Shop Einkauf war erfolgreich!");
					} else {
						e.getWhoClicked()
								.sendMessage(Language.prefix + "Dir fehlen "
										+ String.valueOf(1220 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
										+ " um dir dieses Item Kaufen zu können.");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eBasalt §8(16x)")) {
					if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 370
							|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 370) {
						CoinsAPI.removeCoins((Player) e.getWhoClicked(), 370);
						e.getWhoClicked().getInventory().addItem(new ItemStack(Material.POLISHED_BASALT, 16));
						e.getWhoClicked().sendMessage(Language.prefix + "Der Shop Einkauf war erfolgreich!");
					} else {
						e.getWhoClicked()
								.sendMessage(Language.prefix + "Dir fehlen "
										+ String.valueOf(370 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
										+ " um dir dieses Item Kaufen zu können.");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§eSchwarzeichenstamm")) {
					if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 730
							|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 730) {
						CoinsAPI.removeCoins((Player) e.getWhoClicked(), 730);
						e.getWhoClicked().getInventory().addItem(new ItemStack(Material.DARK_OAK_WOOD, 16));
						e.getWhoClicked().sendMessage(Language.prefix + "Der Shop Einkauf war erfolgreich!");
					} else {
						e.getWhoClicked()
								.sendMessage(Language.prefix + "Dir fehlen "
										+ String.valueOf(730 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
										+ " um dir dieses Item Kaufen zu können.");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eAkazienstamm §8(16x)")) {
					if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 660
							|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 660) {
						CoinsAPI.removeCoins((Player) e.getWhoClicked(), 660);
						e.getWhoClicked().getInventory().addItem(new ItemStack(Material.ACACIA_WOOD, 16));
						e.getWhoClicked().sendMessage(Language.prefix + "Der Shop Einkauf war erfolgreich!");
					} else {
						e.getWhoClicked()
								.sendMessage(Language.prefix + "Dir fehlen "
										+ String.valueOf(660 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
										+ " um dir dieses Item Kaufen zu können.");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eTropenstamm §8(16x)")) {
					if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 540
							|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 540) {
						CoinsAPI.removeCoins((Player) e.getWhoClicked(), 540);
						e.getWhoClicked().getInventory().addItem(new ItemStack(Material.JUNGLE_WOOD, 16));
						e.getWhoClicked().sendMessage(Language.prefix + "Der Shop Einkauf war erfolgreich!");
					} else {
						e.getWhoClicked()
								.sendMessage(Language.prefix + "Dir fehlen "
										+ String.valueOf(540 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
										+ " um dir dieses Item Kaufen zu können.");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eBirkenstamm §8(16x)")) {
					if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 420
							|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 420) {
						CoinsAPI.removeCoins((Player) e.getWhoClicked(), 420);
						e.getWhoClicked().getInventory().addItem(new ItemStack(Material.BIRCH_WOOD, 16));
						e.getWhoClicked().sendMessage(Language.prefix + "Der Shop Einkauf war erfolgreich!");
					} else {
						e.getWhoClicked()
								.sendMessage(Language.prefix + "Dir fehlen "
										+ String.valueOf(420 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
										+ " um dir dieses Item Kaufen zu können.");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eFichtenstamm §8(16x)")) {
					if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 380
							|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 380) {
						CoinsAPI.removeCoins((Player) e.getWhoClicked(), 380);
						e.getWhoClicked().getInventory().addItem(new ItemStack(Material.SPRUCE_WOOD, 16));
						e.getWhoClicked().sendMessage(Language.prefix + "Der Shop Einkauf war erfolgreich!");
					} else {
						e.getWhoClicked()
								.sendMessage(Language.prefix + "Dir fehlen "
										+ String.valueOf(380 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
										+ " um dir dieses Item Kaufen zu können.");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eEichenstamm §8(16x)")) {
					if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 210
							|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 210) {
						CoinsAPI.removeCoins((Player) e.getWhoClicked(), 210);
						e.getWhoClicked().getInventory().addItem(new ItemStack(Material.OAK_WOOD, 16));
						e.getWhoClicked().sendMessage(Language.prefix + "Der Shop Einkauf war erfolgreich!");
					} else {
						e.getWhoClicked()
								.sendMessage(Language.prefix + "Dir fehlen "
										+ String.valueOf(210 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
										+ " um dir dieses Item Kaufen zu können.");
					}

					// minen

				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §7Kohle §eMine")) {
					if (e.getCurrentItem().getItemMeta().getLore().toString().contains("§8» §eFür")) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"lp user " + e.getWhoClicked().getName().toString() + " permission set mine.coal");
						e.getWhoClicked().closeInventory();
						e.getWhoClicked().sendMessage(Language.prefix + "Die Mine wurde erfolgreich freigeschaltet!");
						openTeleportInventory((Player) e.getWhoClicked());
					} else if (e.getCurrentItem().getItemMeta().getLore().toString()
							.contains("§8» §eJetzt Teleportieren §8«")) {
						e.getWhoClicked().closeInventory();
						BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "Mine-Coal");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §fEisen §eMine")) {
					if (e.getCurrentItem().getItemMeta().getLore().toString().contains("§8» §eFür")) {
						if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 100000
								|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 100000) {
							CoinsAPI.removeCoins((Player) e.getWhoClicked(), 100000);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"lp user " + e.getWhoClicked().getName().toString() + " permission set mine.iron");
							e.getWhoClicked().sendMessage(Language.prefix + "Die Mine wurde erfolgreich freigeschaltet!");
							e.getWhoClicked().closeInventory();
							openTeleportInventory((Player) e.getWhoClicked());
						} else {
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Dir fehlen "
											+ String.valueOf(100000 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
											+ " um dir diese Mine Kaufen zu können.");
						}
					} else if (e.getCurrentItem().getItemMeta().getLore().toString()
							.contains("§8» §eJetzt Teleportieren §8«")) {
						e.getWhoClicked().closeInventory();
						BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "Mine-Iron");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §6Gold §eMine")) {
					if (e.getCurrentItem().getItemMeta().getLore().toString().contains("§8» §eFür")) {
						if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 200000
								|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 200000) {
							CoinsAPI.removeCoins((Player) e.getWhoClicked(), 200000);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"lp user " + e.getWhoClicked().getName().toString() + " permission set mine.gold");
							e.getWhoClicked().sendMessage(Language.prefix + "Die Mine wurde erfolgreich freigeschaltet!");
							e.getWhoClicked().closeInventory();
							openTeleportInventory((Player) e.getWhoClicked());
						} else {
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Dir fehlen "
											+ String.valueOf(200000 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
											+ " um dir diese Mine Kaufen zu können.");
						}
					} else if (e.getCurrentItem().getItemMeta().getLore().toString()
							.contains("§8» §eJetzt Teleportieren §8«")) {
						e.getWhoClicked().closeInventory();
						BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "Mine-Gold");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §bDiamant §eMine")) {
					if (e.getCurrentItem().getItemMeta().getLore().toString().contains("§8» §eFür")) {
						if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 400000
								|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 400000) {
							CoinsAPI.removeCoins((Player) e.getWhoClicked(), 400000);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"lp user " + e.getWhoClicked().getName().toString() + " permission set mine.diamond");
							e.getWhoClicked().sendMessage(Language.prefix + "Die Mine wurde erfolgreich freigeschaltet!");
							e.getWhoClicked().closeInventory();
							openTeleportInventory((Player) e.getWhoClicked());
						} else {
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Dir fehlen "
											+ String.valueOf(400000 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
											+ " um dir diese Mine Kaufen zu können.");
						}
					} else if (e.getCurrentItem().getItemMeta().getLore().toString()
							.contains("§8» §eJetzt Teleportieren §8«")) {
						e.getWhoClicked().closeInventory();
						BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "Mine-Diamond");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aSmaragd §eMine")) {
					if (e.getCurrentItem().getItemMeta().getLore().toString().contains("§8» §eFür")) {
						if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 800000
								|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 800000) {
							CoinsAPI.removeCoins((Player) e.getWhoClicked(), 800000);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"lp user " + e.getWhoClicked().getName().toString() + " permission set mine.emerald");
							e.getWhoClicked().sendMessage(Language.prefix + "Die Mine wurde erfolgreich freigeschaltet!");
							e.getWhoClicked().closeInventory();
							openTeleportInventory((Player) e.getWhoClicked());
						} else {
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Dir fehlen "
											+ String.valueOf(800000 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
											+ " um dir diese Mine Kaufen zu können.");
						}
					} else if (e.getCurrentItem().getItemMeta().getLore().toString()
							.contains("§8» §eJetzt Teleportieren §8«")) {
						e.getWhoClicked().closeInventory();
						BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "Mine-Emerald");
					}

					// cbs

				} else if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase("§8» §eCityBuild §7Standard")) {
					if (e.getCurrentItem().getItemMeta().getLore().toString().contains("§8» §eFür")) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"lp user " + e.getWhoClicked().getName().toString() + " permission set cb.normal");
						e.getWhoClicked().closeInventory();
						e.getWhoClicked().sendMessage(Language.prefix + "Der CityBuild wurde erfolgreich freigeschaltet!");
						openTeleportInventory((Player) e.getWhoClicked());
					} else if (e.getCurrentItem().getItemMeta().getLore().toString()
							.contains("§8» §eJetzt Teleportieren §8«")) {
						e.getWhoClicked().closeInventory();
						BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "CB-Normal");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eCityBuild §bExpert")) {
					if (e.getCurrentItem().getItemMeta().getLore().toString().contains("§8» §eFür")) {
						if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 250000
								|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 250000) {
							CoinsAPI.removeCoins((Player) e.getWhoClicked(), 250000);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"lp user " + e.getWhoClicked().getName().toString() + " permission set cb.expert");
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Der CityBuild wurde erfolgreich freigeschaltet!");
							e.getWhoClicked().closeInventory();
							openTeleportInventory((Player) e.getWhoClicked());
						} else {
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Dir fehlen "
											+ String.valueOf(250000 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
											+ " um dir diesen CityBuild freischalten zu können.");
						}
					} else if (e.getCurrentItem().getItemMeta().getLore().toString()
							.contains("§8» §eJetzt Teleportieren §8«")) {
						e.getWhoClicked().closeInventory();
						BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "CB-Expert");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eCityBuild §5Master")) {
					if (e.getCurrentItem().getItemMeta().getLore().toString().contains("§8» §eFür")) {
						if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 500000
								|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 500000) {
							CoinsAPI.removeCoins((Player) e.getWhoClicked(), 500000);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"lp user " + e.getWhoClicked().getName().toString() + " permission set cb.master");
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Der CityBuild wurde erfolgreich freigeschaltet!");
							e.getWhoClicked().closeInventory();
							openTeleportInventory((Player) e.getWhoClicked());
						} else {
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Dir fehlen "
											+ String.valueOf(500000 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
											+ " um dir diesen CityBuild freischalten zu können.");
						}
					} else if (e.getCurrentItem().getItemMeta().getLore().toString()
							.contains("§8» §eJetzt Teleportieren §8«")) {
						e.getWhoClicked().closeInventory();
						BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "CB-Master");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eCityBuild §6Legend")) {
					if (e.getCurrentItem().getItemMeta().getLore().toString().contains("§8» §eFür")) {
						if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 1000000
								|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 1000000) {
							CoinsAPI.removeCoins((Player) e.getWhoClicked(), 1000000);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"lp user " + e.getWhoClicked().getName().toString() + " permission set cb.legend");
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Der CityBuild wurde erfolgreich freigeschaltet!");
							e.getWhoClicked().closeInventory();
							openTeleportInventory((Player) e.getWhoClicked());
						} else {
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Dir fehlen "
											+ String.valueOf(1000000 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
											+ " um dir diesen CityBuild freischalten zu können.");
						}
					} else if (e.getCurrentItem().getItemMeta().getLore().toString()
							.contains("§8» §eJetzt Teleportieren §8«")) {
						e.getWhoClicked().closeInventory();
						BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "CB-Legend");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eCityBuild §cHyper")) {
					if (e.getCurrentItem().getItemMeta().getLore().toString().contains("§8» §eFür")) {
						if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 2000000
								|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 2000000) {
							CoinsAPI.removeCoins((Player) e.getWhoClicked(), 2000000);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"lp user " + e.getWhoClicked().getName().toString() + " permission set cb.hyper");
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Der CityBuild wurde erfolgreich freigeschaltet!");
							e.getWhoClicked().closeInventory();
							openTeleportInventory((Player) e.getWhoClicked());
						} else {
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Dir fehlen "
											+ String.valueOf(2000000 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
											+ " um dir diesen CityBuild freischalten zu können.");
						}
					} else if (e.getCurrentItem().getItemMeta().getLore().toString()
							.contains("§8» §eJetzt Teleportieren §8«")) {
						e.getWhoClicked().closeInventory();
						BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "CB-Hyper");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eFreiheit")) {
					if (e.getCurrentItem().getItemMeta().getLore().toString().contains("§8» §eFür")) {
						if (CoinsAPI.getCoins((Player) e.getWhoClicked()) == 4000000
								|| CoinsAPI.getCoins((Player) e.getWhoClicked()) >= 4000000) {
							CoinsAPI.removeCoins((Player) e.getWhoClicked(), 4000000);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"lp user " + e.getWhoClicked().getName().toString() + " permission set cb.free");
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Der CityBuild wurde erfolgreich freigeschaltet!");
							e.getWhoClicked().closeInventory();
							openTeleportInventory((Player) e.getWhoClicked());
						} else {
							e.getWhoClicked()
									.sendMessage(Language.prefix + "Dir fehlen "
											+ String.valueOf(4000000 - CoinsAPI.getCoins((Player) e.getWhoClicked()))
											+ " um dir diesen CityBuild freischalten zu können.");
						}
					} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§8» §eJetzt Teleportieren §8«")) {
						e.getWhoClicked().closeInventory();
						BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "CB-Free");
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §eHub")) {
					e.getWhoClicked().closeInventory();
					BungeeCordHandler.sendPlayer((Player) e.getWhoClicked(), "hub");
				}
			}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bNavigator")) {
				openTeleportInventory(e.getPlayer());
		} else {
			return;
		}
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		openShopInventory((Player) arg0);
		return false;
	}

}
