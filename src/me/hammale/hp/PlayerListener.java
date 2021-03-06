package me.hammale.hp;

import me.hammale.hp.HarryPotter.Position;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.inventory.SpoutItemStack;

public class PlayerListener implements Listener {
	
	HarryPotter plugin;
	
	public PlayerListener(HarryPotter plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e){
		if(plugin.running){
			Player p = e.getPlayer();
			for(GamePlayer gp : plugin.greenPlayers){
				if(p.getName().equalsIgnoreCase(gp.getName())){
					if(e.getItem() == plugin.task.snitch.getItem()){
						e.setCancelled(true);
						if(gp.getTeam() == plugin.greenTeam){
							plugin.greenScore += 150;
							plugin.getServer().broadcastMessage(plugin.greenTeam.toString() + " has scored!");
							plugin.showScores();
						}else if(gp.getTeam() == plugin.blueTeam){
							plugin.blueScore += 150;
							plugin.getServer().broadcastMessage(plugin.blueTeam.toString() + " has scored!");
							plugin.showScores();
						}
						plugin.endGame();
						return;
					}
					for(GameItem i : plugin.task.bludgers){
						if(e.getItem() == i.getItem()){
							e.setCancelled(true);
							p.setHealth(p.getHealth()-3);
						}
					}
				}
			}
			for(GamePlayer gp : plugin.bluePlayers){
				if(p.getName().equalsIgnoreCase(gp.getName())){
					if(e.getItem() == plugin.task.snitch.getItem()){
						e.setCancelled(true);
						if(gp.getTeam() == plugin.greenTeam){
							plugin.greenScore += 150;
							plugin.getServer().broadcastMessage(plugin.greenTeam.toString() + " has scored!");
							plugin.showScores();
						}else if(gp.getTeam() == plugin.blueTeam){
							plugin.blueScore += 150;
							plugin.getServer().broadcastMessage(plugin.blueTeam.toString() + " has scored!");
							plugin.showScores();
						}
						plugin.endGame();
						return;
					}
					for(GameItem i : plugin.task.bludgers){
						if(e.getItem() == i.getItem()){
							e.setCancelled(true);
							p.setHealth(p.getHealth()-3);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onItemDespawn(ItemDespawnEvent e){
		if(e.getEntity() == plugin.task.snitch
				|| e.getEntity() == plugin.task.quaffle){
			e.setCancelled(true);
		}
		for(GameItem i : plugin.task.bludgers){
			if(e.getEntity() == i.getItem()){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		if(plugin.running){
			Player p = e.getPlayer();
			for(GamePlayer gp : plugin.greenPlayers){
				if(gp.getName().equalsIgnoreCase(p.getName())){
					if(gp.getPosition() == Position.KEEPER
						&& !plugin.greenKeeper.isInCuboid(p)){
						e.setTo(e.getFrom());
						return;
					}
					if(!plugin.arena.isInCuboid(p)){
						e.setTo(e.getFrom());
						return;
					}
				}
			}
			for(GamePlayer gp : plugin.bluePlayers){
				if(gp.getName().equalsIgnoreCase(p.getName())){
					if(gp.getPosition() == Position.KEEPER
						&& !plugin.blueKeeper.isInCuboid(p)){
						e.setTo(e.getFrom());
						return;
					}
					if(!plugin.arena.isInCuboid(p)){
						e.setTo(e.getFrom());
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		Player p = e.getPlayer();
		if(e.getBlock().getType() == Material.SPONGE){
			if(p.getName().equalsIgnoreCase(plugin.greenGoal)){
				plugin.greenGoals.add(e.getBlock().getLocation());
			}else if(p.getName().equalsIgnoreCase(plugin.blueGoal)){
				plugin.blueGoals.add(e.getBlock().getLocation());
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		Player p = e.getPlayer();
		if(p.getName().equalsIgnoreCase(plugin.greenGoal) && plugin.greenGoals.contains(e.getBlock().getLocation())){
			plugin.greenGoals.remove(e.getBlock().getLocation());
		}else if(p.getName().equalsIgnoreCase(plugin.blueGoal) && plugin.blueGoals.contains(e.getBlock().getLocation())){
			plugin.blueGoals.remove(e.getBlock().getLocation());
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			Player p = e.getPlayer();
			SpoutItemStack citem = new SpoutItemStack(p.getItemInHand());
			if(citem.isCustomItem()){
				if(citem.getMaterial().getName().equals("Quaffle")){
					Item i = p.getWorld().dropItem(p.getLocation(), p.getItemInHand());
					p.setItemInHand(new ItemStack(Material.AIR, 1));
					i.setFallDistance(0.0F);
					i.setVelocity(p.getLocation().getDirection().normalize().add(new Vector(0,.5,0)));
					i.setFallDistance(0.0F);
					plugin.task.quaffle.setItem(i);
				}
			}else if(p.getItemInHand().getType() == Material.STICK && plugin.arenaSelect != null && p.getName().equalsIgnoreCase(plugin.arenaSelect)){
				if(plugin.arena.getA() == null && plugin.arena.getB() == null){
					plugin.arena.setA(e.getClickedBlock().getLocation());
					p.sendMessage(ChatColor.GREEN + "First position set!");
				}else if(plugin.arena.getA() != null && plugin.arena.getB() == null){
					plugin.arena.setB(e.getClickedBlock().getLocation());
					p.sendMessage(ChatColor.GREEN + "Second position set! Arena set.");
					plugin.arenaSelect = null;
				}
			}else if(p.getItemInHand().getType() == Material.STICK && plugin.keeperSelect != null && p.getName().equalsIgnoreCase(plugin.keeperSelect)){
				if(!plugin.greenKeeper.isReady()){
					if(plugin.greenKeeper.getA() == null && plugin.greenKeeper.getB() == null){
						plugin.greenKeeper.setA(e.getClickedBlock().getLocation());
						p.sendMessage(ChatColor.GREEN + "First position set!");
					}else if(plugin.greenKeeper.getA() != null && plugin.greenKeeper.getB() == null){
						plugin.greenKeeper.setB(e.getClickedBlock().getLocation());
						p.sendMessage(ChatColor.GREEN + "Second position set! Select second keeper cuboid.");
					}
				}else{
					if(plugin.blueKeeper.getA() == null && plugin.blueKeeper.getB() == null){
						plugin.blueKeeper.setA(e.getClickedBlock().getLocation());
						p.sendMessage(ChatColor.GREEN + "First position set!");
					}else if(plugin.blueKeeper.getA() != null && plugin.blueKeeper.getB() == null){
						plugin.blueKeeper.setB(e.getClickedBlock().getLocation());
						p.sendMessage(ChatColor.GREEN + "Second position set! Keeper cuboid's set.");
						plugin.keeperSelect = null;
					}	
				}
			}

		}
	}
	
}