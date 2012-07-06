package me.hammale.hp;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;

public class HarryPotter extends JavaPlugin {
	
	CustomItem snitch;
	CustomItem quaffle;
	CustomItem bludger;
	Cuboid cuboid;
	boolean running;
	
	public ArrayList<CustomItem> bludgers = new ArrayList<CustomItem>();
	
	public enum Team {
		GRYFFINDOR, HUFFLEPUFF, RAVENCLAW, SLYTHERIN
	}
	
	public enum Position {
		SNEEKER, CHASER, KEEPER
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
		snitch = new CustomItem(this, "Golden Snitch", "http://i.imgur.com/kDTgM.png");
		quaffle = new CustomItem(this, "Quaffle", "http://i.imgur.com/KEBX8.png");
		bludger = new CustomItem(this, "Bludger", "http://i.imgur.com/4G3KU.png");
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this,new GameTask(this),0,1);

	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String lbl,
			String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("test")){
				p.getInventory().addItem(new SpoutItemStack(snitch, 1));
				p.getInventory().addItem(new SpoutItemStack(quaffle, 1));
				p.getInventory().addItem(new SpoutItemStack(bludger, 1));
			}
		}
		return true;
	}
	
}