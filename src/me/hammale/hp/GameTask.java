package me.hammale.hp;

public class GameTask implements Runnable {
	
	HarryPotter plugin;
	
	public GameTask(HarryPotter plugin){
		this.plugin = plugin;
	}
	
	public void run() {
		if(plugin.running){
			
		}
	}

}
