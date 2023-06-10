package me.seafoam.minecraft.seafoamy.paper.util;

import me.seafoam.minecraft.seafoamy.util.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class LocationUtils {

	public static Location fromBukkitLocation(org.bukkit.Location bukkit) {
		return new Location(bukkit.getWorld().getName(), bukkit.getX(), bukkit.getY(), bukkit.getZ(), bukkit.getYaw(), bukkit.getPitch());
	}

	public static org.bukkit.Location toBukkitLocation(Location location) {
		World world;

		if (location.getWorld() == null) {
			world = Bukkit.getWorlds().get(0);
		} else {
			world = Bukkit.getWorld(location.getWorld());
		}

		return new org.bukkit.Location(world, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}
}