package me.hammale.hp;

import org.bukkit.Location;
import org.getspout.spoutapi.material.item.GenericCustomItem;

public class CustomItem extends GenericCustomItem {
	 
	HarryPotter plugin;
	Location from;
	
	public CustomItem(HarryPotter plugin, String name, String texture) {
	        super(plugin, name, texture);
	        this.plugin = plugin;
	}
	
	public Location getFrom(){
		return this.from;
	}
	
	public void setFrom(Location from){
		this.from = from;
	}
}
