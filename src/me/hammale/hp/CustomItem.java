package me.hammale.hp;

import org.getspout.spoutapi.material.item.GenericCustomItem;

public class CustomItem extends GenericCustomItem {
	 
	HarryPotter plugin;
	
	public CustomItem(HarryPotter plugin, String name, String texture) {
	        super(plugin, name, texture);
	        this.plugin = plugin;
	}
}
