package me.hammale.hp;

import org.bukkit.inventory.PlayerInventory;

import me.hammale.hp.HarryPotter.Position;
import me.hammale.hp.HarryPotter.Team;

public class GamePlayer {
	
	String name;
	Team team;
	Position position;
	PlayerInventory pi;
	
	public GamePlayer(String name, Team team, Position position){
		this.name = name;
		this.team = team;
		this.position = position;
	}
	
	public Team getTeam(){
		return this.team;
	}
	
	public Position getPosition(){
		return this.position;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setInventory(PlayerInventory pi){
		this.pi = pi;
	}
	
	public PlayerInventory getInventory(){
		return this.pi;
	}
	
}
