package me.seafoam.minecraft.seafoamy.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class PlayerManager<T extends OnlinePlayer, P> {

	protected List<T> onlinePlayers = new ArrayList<>();

	public abstract T getOnlinePlayer(P player);

	public T getOnlinePlayer(UUID uuid) {
		for (T player : onlinePlayers) {
			if (player.getUniqueId().equals(uuid)) return player;
		}

		return null;
	}

	public T getOnlinePlayer(String name) {
		for (T player : onlinePlayers) {
			if (player.getName().equalsIgnoreCase(name)) return player;
		}

		return null;
	}

	public List<T> getAllPlayers() {
		return new ArrayList<>(onlinePlayers);
	}

	public void addPlayer(T player) {
		onlinePlayers.add(player);
	}

	public void removePlayer(T player) {
		onlinePlayers.remove(player);
	}

	public abstract T registerPlayer(P player);
	public abstract T unregisterPlayer(P player);
}