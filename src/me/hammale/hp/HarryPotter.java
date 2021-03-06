package me.hammale.hp;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
public class HarryPotter extends JavaPlugin {
	
	CustomItem snitch;
	CustomItem quaffle;
	CustomItem bludger;
	CustomItem broomstick;
	CustomBlock selectorGreen;
	CustomBlock selectorBlue;
	Cuboid arena = new Cuboid();
	Team greenTeam, blueTeam;
	Cuboid greenKeeper = new Cuboid();
	Cuboid blueKeeper = new Cuboid();
	
	int bludgerCount;
	
	boolean running;
	String arenaSelect, keeperSelect, greenGoal, blueGoal;
	
	GameTask task = new GameTask(this);
	
	int greenScore, blueScore;
	
	public ArrayList<GamePlayer> greenPlayers = new ArrayList<GamePlayer>();
	public ArrayList<GamePlayer> bluePlayers = new ArrayList<GamePlayer>();
	
	public HashSet<Location> greenGoals = new HashSet<Location>();
	public HashSet<Location> blueGoals = new HashSet<Location>();
	
	public HashMap<String,ItemStack[]> invs = new HashMap<String,ItemStack[]>();
	
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
		getServer().getScheduler().scheduleSyncRepeatingTask(this,task,0,1);
		bludgerCount = 2;

	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("quidditch")){
				if(args.length == 2){
					if(args[0].equalsIgnoreCase("set")  && p.isOp()){
						if(args[1].equalsIgnoreCase("arena")){
							p.getInventory().setItemInHand(new ItemStack(Material.STICK, 1));
							arenaSelect = p.getName();
							p.sendMessage("Use stick to select arena cuboid.");
						}else if(args[1].equalsIgnoreCase("keeper")){
							p.getInventory().setItemInHand(new ItemStack(Material.STICK, 1));
							keeperSelect = p.getName();
							p.sendMessage("Use stick to select first keeper cuboid.");
						}else if(args[1].equalsIgnoreCase("goal")){
							if(greenGoal == null){
								p.getInventory().setItemInHand(new ItemStack(Material.SPONGE, 1));
								p.sendMessage("Set first goal then type /quidditch set goal");
								greenGoal = p.getName();
							}else if(greenGoal != null && blueGoal == null){
								greenGoal = "done";
								for(Location l : greenGoals){
									l.getBlock().setTypeId(0);
								}
								p.getInventory().setItemInHand(new ItemStack(Material.SPONGE, 1));
								p.sendMessage("Set second goal then type /quidditch set goal");
								blueGoal = p.getName();
							}else if(greenGoal != null && blueGoal != null){
								for(Location l : blueGoals){
									l.getBlock().setTypeId(0);
								}
								p.getInventory().setItemInHand(new ItemStack(Material.AIR, 1));
								p.sendMessage("All goals set.");
							}
						}
					}
				}else if(args.length == 1){
					if(args[0].equalsIgnoreCase("join")){
						if(running){
							FPlayer fp = FPlayers.i.get(p);
							Team team = getTeamFromId(Integer.parseInt(fp.getFaction().getId()));
							if(onTeam(bluePlayers, p.getName())
									|| onTeam(greenPlayers, p.getName())){
								p.sendMessage(ChatColor.RED + "You are already on a team!");
								return true;
							}
							if(greenTeam == team){
								if(greenPlayers.size() >= 7){
									p.sendMessage(ChatColor.RED + "Sorry your team is full!");
									return true;
								}
								p.getInventory().addItem(new SpoutItemStack(broomstick, 1));
								GamePlayer gp = null;
								if(!hasSeeker(greenPlayers)){
									p.teleport(arena.getCenter());
									gp = new GamePlayer(p.getName(), team, Position.SEEKER);							
									p.sendMessage(ChatColor.BLUE + "You are now a seeker! Best of luck!");
								}else if(!hasKeeper(greenPlayers)){
									p.teleport(greenKeeper.getCenter());
									gp = new GamePlayer(p.getName(), team, Position.KEEPER);
									p.sendMessage(ChatColor.BLUE + "You are now a keeper! Best of luck!");
								}else{
									p.teleport(arena.getCenter());
									gp = new GamePlayer(p.getName(), team, Position.CHASER);				
									p.sendMessage(ChatColor.BLUE + "You are now a chaser! Best of luck!");
								}
								saveInv(gp);
								greenPlayers.add(gp);
								fly(gp);
							}else if(blueTeam == team){
								if(bluePlayers.size() >= 7){
									p.sendMessage(ChatColor.RED + "Sorry your team is full!");
									return true;
								}
								p.getInventory().addItem(new SpoutItemStack(broomstick, 1));
								GamePlayer gp = null;
								if(!hasSeeker(bluePlayers)){
									p.teleport(arena.getCenter());
									gp = new GamePlayer(p.getName(), team, Position.SEEKER);							
									p.sendMessage(ChatColor.BLUE + "You are now a seeker! Best of luck!");
								}else if(!hasKeeper(bluePlayers)){
									p.teleport(blueKeeper.getCenter());
									gp = new GamePlayer(p.getName(), team, Position.KEEPER);
									p.sendMessage(ChatColor.BLUE + "You are now a keeper! Best of luck!");
								}else{
									p.teleport(arena.getCenter());
									gp = new GamePlayer(p.getName(), team, Position.CHASER);				
									p.sendMessage(ChatColor.BLUE + "You are now a chaser! Best of luck!");
								}
								saveInv(gp);
								bluePlayers.add(gp);
								fly(gp);
							}else{
								p.sendMessage(ChatColor.RED + "Sorry your team isn't playing!");
								return true;
							}
						}
					}
				}else if(args.length == 3){
					if(args[0].equalsIgnoreCase("start")){
						startGame(p, args[1], args[2]);
					}
				}
			}
		}
		return true;
	}
	
	private void fly(GamePlayer gp) {
		Player p = getServer().getPlayer(gp.getName());
		p.setGameMode(GameMode.SURVIVAL);
		p.setAllowFlight(true);
	}

	public void startGame(Player p, String team1, String team2){
		try{
			greenTeam = Team.valueOf(team1.toUpperCase());
		}catch(Exception e){
			p.sendMessage(ChatColor.RED + team1 + " is not a valid team name!");
			return;
		}
		try{
			blueTeam = Team.valueOf(team2.toUpperCase());
		}catch(Exception e){
			p.sendMessage(ChatColor.RED + team2 + " is not a valid team name!");
			return;
		}	
		if(!arena.isReady()
				|| !greenKeeper.isReady()
				|| !blueKeeper.isReady()
				|| greenGoals == null
				|| blueGoals == null){
			p.sendMessage(ChatColor.RED + "Not all required regions defined! Cannot start game!");
			return;
		}
		task.snitch = new GameItem(arena.getWorld().dropItemNaturally(arena.getCenter(), new SpoutItemStack(snitch)));
		task.quaffle = new GameItem(arena.getWorld().dropItemNaturally(arena.getCenter(), new SpoutItemStack(quaffle)));
		for(int i=1;i<=bludgerCount;i++){
			task.bludgers.add(new GameItem(arena.getWorld().dropItemNaturally(arena.getCenter(), new SpoutItemStack(bludger))));
		}
		running = true;
		p.sendMessage(ChatColor.YELLOW + team1 + " vs " + team2 + " has begun!");
	}
	
	public void saveInv(GamePlayer gp) {
		ItemStack[] is = getServer().getPlayer(gp.getName()).getInventory().getContents();
		invs.put(gp.getName(), is);
	}
	
	public void restoreInv(GamePlayer gp) {
		Player p = getServer().getPlayer(gp.getName());
		if(p != null){
			p.getInventory().clear();
			for(ItemStack is : invs.get(gp.getName())){
				if(is != null){
					p.getInventory().addItem(is);
				}
			}
			invs.remove(gp.getName());
		}
	}
	
	public boolean hasSeeker(ArrayList<GamePlayer> players){
		for(GamePlayer gp : players){
			if(gp.getPosition() == Position.SEEKER){
				return true;
			}
		}
		return false;
	}
	
	public boolean onTeam(ArrayList<GamePlayer> players, String s){
		for(GamePlayer gp : players){
			if(gp.getName().equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasKeeper(ArrayList<GamePlayer> players){
		for(GamePlayer gp : players){
			if(gp.getPosition() == Position.KEEPER){
				return true;
			}
		}
		return false;
	}
	
	public Team getTeamFromId(int id){
		if(id == 7){
			return Team.GRYFFINDOR;
		}else if(id == 3){
			return Team.RAVENCLAW;
		}else  if(id == 4){
			return Team.HUFFLEPUFF;
		}else if(id == 6){
			return Team.SLYTHERIN;
		}else{
			return null;
		}
	}
	
	public void endGame() {
		// TODO
	}

	public void showScores() {
		getServer().broadcastMessage(greenTeam.toString() + ": " + greenScore);
		getServer().broadcastMessage(blueTeam.toString() + ": " + blueScore);		
	}
	
}