package me.seafoam.minecraft.seafoamy.paper.player;

import java.util.UUID;
import me.seafoam.minecraft.seafoamy.Seafoamy;
import me.seafoam.minecraft.seafoamy.player.OfflinePlayer;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import me.seafoam.minecraft.seafoamy.player.WrappedPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OfflinePlayerPaper implements OfflinePlayer {

	private final UUID uuid;
	private final String name;

	protected OfflinePlayerPaper(Player player) {
		this.uuid = player.getUniqueId();
		this.name = player.getName();
	}

	protected OfflinePlayerPaper(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}

	@Override
	public @NotNull UUID getUniqueId() {
		return uuid;
	}

	@Override
	public @NotNull String getName() {
		return name;
	}

	@Override
	public boolean isOnline() {
		return getOnlinePlayer() != null;
	}

	@Override
	public OnlinePlayer getOnlinePlayer() {
		return Seafoamy.getPlayerManager().getOnlinePlayer(uuid);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof OfflinePlayer other) return getUniqueId().equals(other.getUniqueId());
		else if (o instanceof WrappedPlayer other) return getUniqueId().equals(other.getOnlinePlayer().getUniqueId());
		else if (o instanceof Player other) return getUniqueId().equals(other.getUniqueId());
		else if (o instanceof UUID other) return getUniqueId().equals(other);
		else return false;
	}
}