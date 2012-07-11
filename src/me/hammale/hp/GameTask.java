package me.hammale.hp;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

public class GameTask implements Runnable {
	
	HarryPotter plugin;
	
	Item snitch, quaffle;
	
	Random ran = new Random();
	
	ArrayList<Item> bludgers = new ArrayList<Item>();
	
	public GameTask(HarryPotter plugin){
		this.plugin = plugin;
	}
	
	public void run() {
		if(plugin.running){
			double x = ran.nextInt(9) / 10;
			double y = ran.nextInt(9) / 10;
			double z = ran.nextInt(9) / 10;
			if(ran.nextInt(1) == 0){
				x = x*-1;
			}
			if(ran.nextInt(1) == 0){
				y = y*-1;
			}
			if(ran.nextInt(1) == 0){
				z = z*-1;
			}
			snitch.setVelocity(new Vector(x,y,z));
			
			x = ran.nextInt(9) / 10;
			y = ran.nextInt(9) / 10;
			z = ran.nextInt(9) / 10;
			if(ran.nextInt(1) == 0){
				x = x*-1;
			}
			if(ran.nextInt(1) == 0){
				y = y*-1;
			}
			if(ran.nextInt(1) == 0){
				z = z*-1;
			}
			quaffle.setVelocity(new Vector(x,y,z));
			
			for(Item i : bludgers){
				x = ran.nextInt(9) / 10;
				y = ran.nextInt(9) / 10;
				z = ran.nextInt(9) / 10;
				if(ran.nextInt(1) == 0){
					x = x*-1;
				}
				if(ran.nextInt(1) == 0){
					y = y*-1;
				}
				if(ran.nextInt(1) == 0){
					z = z*-1;
				}
				i.setVelocity(new Vector(x,y,z));
				if(!plugin.arena.isInCuboid(i)){
					i.setVelocity(i.getVelocity().multiply(-1));
				}
			}
			
			for(Location l : plugin.greenGoals){
				if(quaffle.getLocation().getBlockX() == l.getBlockX()
						&& quaffle.getLocation().getBlockY() == l.getBlockY()
						&& quaffle.getLocation().getBlockZ() == l.getBlockZ()){
					plugin.blueScore += 10;
				}
			}
			for(Location l : plugin.blueGoals){
				if(quaffle.getLocation().getBlockX() == l.getBlockX()
						&& quaffle.getLocation().getBlockY() == l.getBlockY()
						&& quaffle.getLocation().getBlockZ() == l.getBlockZ()){
					plugin.greenScore += 10;
				}
			}
			if(!plugin.arena.isInCuboid(snitch)){
				snitch.setVelocity(snitch.getVelocity().multiply(-1));
			}
			if(!plugin.arena.isInCuboid(quaffle)){
				quaffle.setVelocity(quaffle.getVelocity().multiply(-1));
			}
		}
	}

}
