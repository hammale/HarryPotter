package me.hammale.hp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;

public class HarryPotter extends JavaPlugin {
	
	CustomItem snitch;
	CustomItem snitch1;
	CustomItem quaffle;
	CustomItem bludger;
	
	@Override
	public void onDisable() {
		System.out.println("[Quidditch] v1.0 Disabled!");
	}

	@Override
	public void onEnable() {
		System.out.println("[Quidditch] v1.0 Enabled!");
		SpoutManager.getFileManager().addToPreLoginCache(this, "http://i.imgur.com/kDTgM.png");
		SpoutManager.getFileManager().addToPreLoginCache(this, "http://i.imgur.com/HXkeD.png");		
		snitch = new CustomItem(this, "Snitch Wings", "http://i.imgur.com/kDTgM.png");
		snitch1 = new CustomItem(this, "Snitch", "http://i.imgur.com/HXkeD.png");
		quaffle = new CustomItem(this, "Quaffle", "http://i.imgur.com/KEBX8.png");
		bludger = new CustomItem(this, "Bludger", "http://i.imgur.com/4G3KU.png");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String lbl,
			String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("test")){
				p.getInventory().addItem(new SpoutItemStack(snitch, 1));
				p.getInventory().addItem(new SpoutItemStack(snitch1, 1));
				p.getInventory().addItem(new SpoutItemStack(quaffle, 1));
				p.getInventory().addItem(new SpoutItemStack(bludger, 1));
			}
		}
		return true;
	}
	
}