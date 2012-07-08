package me.hammale.hp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.inventory.SpoutItemStack;

public class PlayerListener implements Listener {
	
	HarryPotter plugin;
	
	public PlayerListener(HarryPotter plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			Player p = e.getPlayer();
			SpoutItemStack citem = new SpoutItemStack(p.getItemInHand());
			if(citem.isCustomItem()){
				if(citem.getMaterial().getName().equals("Quaffle")){
					Item i = p.getWorld().dropItem(p.getLocation(), p.getItemInHand());
					p.setItemInHand(new ItemStack(Material.AIR, 1));
					i.setFallDistance(0.0F);
					i.setVelocity(p.getLocation().getDirection().normalize().add(new Vector(0,.5,0)));
					i.setFallDistance(0.0F);
				}
			}else if(p.getItemInHand().getType() == Material.STICK && plugin.arenaSelect != null && p.getName().equalsIgnoreCase(plugin.arenaSelect)){
				if(plugin.arena.getA() == null && plugin.arena.getB() == null){
					plugin.arena.setA(p.getLocation());
					p.sendMessage(ChatColor.GREEN + "First position set!");
				}else if(plugin.arena.getA() != null && plugin.arena.getB() == null){
					plugin.arena.setB(p.getLocation());
					p.sendMessage(ChatColor.GREEN + "Second position set! Arena set.");
					plugin.arenaSelect = null;
				}
			}else if(p.getItemInHand().getType() == Material.STICK && plugin.keeperSelect != null && p.getName().equalsIgnoreCase(plugin.keeperSelect)){
				if(!plugin.greenKeeper.isReady()){
					if(plugin.greenKeeper.getA() == null && plugin.greenKeeper.getB() == null){
						plugin.greenKeeper.setA(p.getLocation());
						p.sendMessage(ChatColor.GREEN + "First position set!");
					}else if(plugin.greenKeeper.getA() != null && plugin.greenKeeper.getB() == null){
						plugin.greenKeeper.setB(p.getLocation());
						p.sendMessage(ChatColor.GREEN + "Second position set! Select second keeper cuboid.");
					}
				}else{
					if(plugin.blueKeeper.getA() == null && plugin.blueKeeper.getB() == null){
						plugin.blueKeeper.setA(p.getLocation());
						p.sendMessage(ChatColor.GREEN + "First position set!");
					}else if(plugin.blueKeeper.getA() != null && plugin.blueKeeper.getB() == null){
						plugin.blueKeeper.setB(p.getLocation());
						p.sendMessage(ChatColor.GREEN + "Second position set! Keeper cuboid's set.");
						plugin.keeperSelect = null;
					}	
				}
			}

		}
	}
	
}