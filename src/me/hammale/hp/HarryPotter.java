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
import org.getspout.spoutapi.inventory.SpoutItemStack;

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
	String arenaSelect, keeperSelect, greenGoal, blueGoal;
	
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage("-1");
		if(sender instanceof Player){
			Player p = (Player) sender;
			p.sendMessage("0");
			if(cmd.getName().equalsIgnoreCase("test")){
				p.sendMessage("1");
				if(args.length == 2){
					p.sendMessage("2");
					if(args[0].equalsIgnoreCase("set")  && p.isOp()){
						p.sendMessage("3");
						if(args[1].equalsIgnoreCase("arena")){
							p.sendMessage("4");
							p.getInventory().setItemInHand(new ItemStack(Material.STICK, 1));
							arenaSelect = p.getName();
							p.sendMessage("Use stick to select arena cuboid.");
						}else if(args[1].equalsIgnoreCase("keeper")){
							p.getInventory().setItemInHand(new ItemStack(Material.STICK, 1));
							keeperSelect = p.getName();
							p.sendMessage("Use stick to select first keeper cuboid.");
						}else if(args[1].equalsIgnoreCase("goal")){
							if(greenGoal == null){
								p.getInventory().setItemInHand(new SpoutItemStack(selectorGreen, 64));
								p.sendMessage("Set first goal then type /quidditch set goal");
								greenGoal = p.getName();
							}else if(greenGoal != null && blueGoal == null){
								greenGoal = null;
								for(Location l : greenGoals){
									l.getBlock().setTypeId(0);
								}
								p.getInventory().setItemInHand(new SpoutItemStack(selectorGreen, 64));
								p.sendMessage("Set second goal then type /quidditch set goal");
								blueGoal = p.getName();
							}else if(greenGoal != null && blueGoal != null){
								blueGoal = null;
								for(Location l : blueGoals){
									l.getBlock().setTypeId(0);
								}
								p.getInventory().setItemInHand(new ItemStack(Material.AIR, 1));
								p.sendMessage("All goals set.");
							}
						}
					}
				}
			}
		}
		return true;
	}
	
}