package me.seafoam.minecraft.seafoamy.util;

import lombok.Getter;
import lombok.Setter;

public class Location {

	@Getter @Setter private String server, world;
	@Getter @Setter private double x, y, z;
	@Getter @Setter private float yaw, pitch;

	public Location(String server, String world, double x, double y, double z, float yaw, float pitch) {
		this.server = server;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public Location(String world, double x, double y, double z, float yaw, float pitch) {
		this(null, world, x, y, z, yaw, pitch);
	}

	public Location(double x, double y, double z, float yaw, float pitch) {
		this(null, null, x, y, z, yaw, pitch);
	}

	public Location(double x, double y, double z) {
		this(null, null, x, y, z, 0, 0);
	}

	public int getBlockX() {
		return (int) Math.floor(x);
	}

	public int getBlockY() {
		return (int) Math.floor(y);
	}

	public int getBlockZ() {
		return (int) Math.floor(z);
	}

	// From bergerkiller https://bukkit.org/threads/lookat-and-move-functions.26768/
	public Location lookAt(Location lookAt) {
		// Values of change in distance (make it relative)
		double dx = lookAt.getX() - getX();
		double dy = lookAt.getY() - getY();
		double dz = lookAt.getZ() - getZ();

		// Set yaw
		if (dx != 0) {
			// Set yaw start value based on dx
			if (dx < 0) {
				setYaw((float) (1.5 * Math.PI));
			} else {
				setYaw((float) (0.5 * Math.PI));
			}
			setYaw(getYaw() - (float) Math.atan(dz / dx));
		} else if (dz < 0) {
			setYaw((float) Math.PI);
		}

		// Get the distance from dx/dz
		double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

		// Set pitch
		setPitch((float) -Math.atan(dy / dxz));

		// Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
		setYaw(-getYaw() * 180f / (float) Math.PI);
		setPitch(getPitch() * 180f / (float) Math.PI);

		return this;
	}

	public Location clone() {
		return new Location(server, world, x, y, z, yaw, pitch);
	}
}