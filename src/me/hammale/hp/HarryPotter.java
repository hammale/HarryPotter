package me.hammale.hp;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;

public class HarryPotter extends JavaPlugin {
	
	CustomItem snitch;
	CustomItem quaffle;
	CustomItem bludger;
	CustomItem broomstick;
	CustomBlock selectorGreen;
	CustomBlock selectorBlue;
	Cuboid arena = new Cuboid();
	Cuboid greenKeeper = new Cuboid();
	Cuboid blueKeeper = new Cuboid();
	boolean running;
	String arenaSelect, keeperSelect;
	
	public HashSet<Location> greenGoals = new HashSet<Location>();
	public HashSet<Location> blueGoals = new HashSet<Location>();
	
	public ArrayList<CustomItem> bludgers = new ArrayList<CustomItem>();
	
	public enum Team {
		GRYFFINDOR, HUFFLEPUFF, RAVENCLAW, SLYTHERIN
	}
	
	public enum Position {
		SEEKER, CHASER, KEEPER
	}
	
	public HashSet<String> seekers = new HashSet<String>();
	
	@Override
	public void onDisable() {
		System.out.println("[Quidditch] v1.0 Disabled!");
	}

	@Override
	public void onEnable() {
		System.out.println("[Quidditch] v1.0 Enabled!");
		SpoutManager.getFileManager().addToPreLoginCache(this, "http://i.imgur.com/kDTgM.png");
		SpoutManager.getFileManager().addToPreLoginCache(this, "http://i.imgur.com/HXkeD.png");
		SpoutManager.getFileManager().addToPreLoginCache(this, "http://i.imgur.com/4G3KU.png");
		SpoutManager.getFileManager().addToPreLoginCache(this, "http://i.imgur.com/ZqztO.png");	
		SpoutManager.getFileManager().addToPreLoginCache(this, "http://i.imgur.com/oa3Ua.png");
		SpoutManager.getFileManager().addToPreLoginCache(this, "http://i.imgur.com/uNMXE.png");
		snitch = new CustomItem(this, "Golden Snitch", "http://i.imgur.com/kDTgM.png");
		quaffle = new CustomItem(this, "Quaffle", "http://i.imgur.com/KEBX8.png");
		bludger = new CustomItem(this, "Bludger", "http://i.imgur.com/4G3KU.png");
		broomstick = new CustomItem(this, "Broomstick", "http://i.imgur.com/BSEVX.png");
		selectorGreen = new CustomBlock(this, "Green Selector", "http://i.imgur.com/ZqztO.png");
		selectorBlue = new CustomBlock(this, "Blue Selector", "http://i.imgur.com/oa3Ua.png");
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this,new GameTask(this),0,1);

	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String lbl,
			String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("quidditch")){
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("arena") && p.isOp()){
						p.getInventory().setItemInHand(new ItemStack(Material.STICK, 1));
						arenaSelect = p.getName();
						p.sendMessage("Use stick to select arena cuboid.");
					}else if(args[0].equalsIgnoreCase("keeper")){
						p.getInventory().setItemInHand(new ItemStack(Material.STICK, 1));
						keeperSelect = p.getName();
						p.sendMessage("Use stick to select first keeper cuboid.");
					}else if(args[0].equalsIgnoreCase("keeper")){
						
					}
				}
			}
		}
		return true;
	}
	
}