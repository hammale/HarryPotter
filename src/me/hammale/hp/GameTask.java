package me.hammale.hp;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class GameTask implements Runnable {
	
	HarryPotter plugin;
	
	GameItem snitch, quaffle;
	
	Random ran;
	
	int i = 0;
	
	ArrayList<GameItem> bludgers = new ArrayList<GameItem>();
	
	public GameTask(HarryPotter plugin){
		this.plugin = plugin;
		this.ran = new Random();
	}
	
	public void run() {
		if(plugin.running){
			snitch.getItem().setFallDistance(0F);
			quaffle.getItem().setFallDistance(0F);
			for(GameItem i : bludgers){
				i.getItem().setFallDistance(0F);
			}
			
			if(i == 100){
				moveStuff();
			}
			
			for(GameItem i : bludgers){
				if(plugin.arena.isInCuboid(i.getItem())){
					i.getItem().teleport(i.getFrom());
				}
				i.setFrom(i.getItem().getLocation());
			}
			
			
			for(Location l : plugin.greenGoals){
				if(quaffle.getItem().getLocation().getBlockX() == l.getBlockX()
						&& quaffle.getItem().getLocation().getBlockY() == l.getBlockY()
						&& quaffle.getItem().getLocation().getBlockZ() == l.getBlockZ()){
					plugin.blueScore += 10;
					plugin.getServer().broadcastMessage(plugin.blueTeam.toString() + " has scored!");
					plugin.showScores();
				}
			}
			for(Location l : plugin.blueGoals){
				if(quaffle.getItem().getLocation().getBlockX() == l.getBlockX()
						&& quaffle.getItem().getLocation().getBlockY() == l.getBlockY()
						&& quaffle.getItem().getLocation().getBlockZ() == l.getBlockZ()){
					plugin.greenScore += 10;
					plugin.getServer().broadcastMessage(plugin.greenTeam.toString() + " has scored!");
					plugin.showScores();
				}
			}
			if(!plugin.arena.isInCuboid(snitch.getItem())){
				snitch.getItem().teleport(snitch.getFrom());
			}
			if(!plugin.arena.isInCuboid(quaffle.getItem())){
				quaffle.getItem().teleport(quaffle.getFrom());
			}
			snitch.setFrom(snitch.getItem().getLocation());
			quaffle.setFrom(snitch.getItem().getLocation());
			if(i>1000){
				i = 0;
			}else{
				i++;
			}
		}
	}

	private void moveStuff() {			
		double x = ran.nextDouble();
		double y = ran.nextDouble();
		double z = ran.nextDouble();
		if(x > 0.4){
			x = 0.4;
		}
		if(y > 0.4){
			y = 0.4;
		}
		if(z > 0.4){
			z = 0.4;
		}
		if(ran.nextInt(2) == 0){
			x = x*-1;
		}
		if(ran.nextInt(2) == 0){
			y = y*-1;
		}
		if(ran.nextInt(2) == 0){
			z = z*-1;
		}
		snitch.getItem().setVelocity(new Vector(x,y,z));
		x = 0.2;
		y = 0.2;
		z = 0.2;
		if(ran.nextInt(2) == 0){
			x = x*-1;
		}
		if(ran.nextInt(2) == 0){
			y = y*-1;
		}
		if(ran.nextInt(2) == 0){
			z = z*-1;
		}
		quaffle.getItem().setVelocity(new Vector(x,y,z));
		
		for(GameItem i : bludgers){
			x = 0.2;
			y = 0.2;
			z = 0.2;
			if(ran.nextInt(2) == 0){
				x = x*-1;
			}
			if(ran.nextInt(2) == 0){
				y = y*-1;
			}
			if(ran.nextInt(2) == 0){
				z = z*-1;
			}
			i.getItem().setVelocity(new Vector(x,y,z));
		}
	}

}