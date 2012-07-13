package me.hammale.hp;

import org.bukkit.Location;
import org.bukkit.entity.Item;

public class GameItem {
	
	Location from;
	Item item;
	
	public GameItem(Item item){
		this.item = item;
		this.from = item.getLocation();
	}
	
	public Item getItem(){
		return this.item;
	}
	
	public void setItem(Item item){
		this.item = item;
	}
	
	public Location getFrom(){
		return this.from;
	}
	
	public void setFrom(Location from){
		this.from = from;
	}
	
}
