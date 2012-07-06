package me.hammale.hp;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Cuboid {
    private Location a, b;

    public Cuboid(Location a, Location b) {
        this.a = a;
        this.b = b;
    }

    public void setA(Location a) {
        this.a = a;
    }

    public void setB(Location b) {
        this.b = b;
    }

    public Location getA() {
        return this.a;
    }

    public Location getB() {
        return this.b;
    }

    public World getWorld() {
        return this.a.getWorld();
    }

    public int getMaxX() {
        return Math.max(a.getBlockX(), b.getBlockX());
    }

    public int getMinX() {
        return Math.min(a.getBlockX(), b.getBlockX());
    }

    public int getMaxY() {
        return Math.max(a.getBlockY(), b.getBlockY());
    }

    public int getMinY() {
        return Math.min(a.getBlockY(), b.getBlockY());
    }

    public int getMaxZ() {
        return Math.max(a.getBlockZ(), b.getBlockZ());
    }

    public int getMinZ() {
        return Math.min(a.getBlockZ(), b.getBlockZ());
    }

    public boolean isInCuboid(Location location) {
        return location.getWorld() == this.getWorld()
                && location.getBlockX() >= this.getMinX()
                && location.getBlockX() <= this.getMaxX()
                && location.getBlockY() >= this.getMinY()
                && location.getBlockY() <= this.getMaxY()
                && location.getBlockZ() >= this.getMinZ()
                && location.getBlockZ() <= this.getMaxZ();
    }

    public boolean isInCuboid(Player p) {
        return p.getWorld() == this.getWorld()
                && p.getLocation().getBlockX() >= this.getMinX()
                && p.getLocation().getBlockX() <= this.getMaxX()
                && p.getLocation().getBlockX() >= this.getMinY()
                && p.getLocation().getBlockX() <= this.getMaxY()
                && p.getLocation().getBlockX() >= this.getMinZ()
                && p.getLocation().getBlockX() <= this.getMaxZ();
    }

    public boolean isInCuboid(Entity e) {
        return e.getWorld() == this.getWorld()
                && e.getLocation().getBlockX() >= this.getMinX()
                && e.getLocation().getBlockX() <= this.getMaxX()
                && e.getLocation().getBlockX() >= this.getMinY()
                && e.getLocation().getBlockX() <= this.getMaxY()
                && e.getLocation().getBlockX() >= this.getMinZ()
                && e.getLocation().getBlockX() <= this.getMaxZ();
    }

    public boolean isInCuboid(Block b) {
        return b.getWorld() == this.getWorld()
                && b.getLocation().getBlockX() >= this.getMinX()
                && b.getLocation().getBlockX() <= this.getMaxX()
                && b.getLocation().getBlockX() >= this.getMinY()
                && b.getLocation().getBlockX() <= this.getMaxY()
                && b.getLocation().getBlockX() >= this.getMinZ()
                && b.getLocation().getBlockX() <= this.getMaxZ();
    }
}
