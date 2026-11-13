package me.seafoam.minecraft.seafoamy.paper.player;

import me.seafoam.minecraft.seafoamy.player.PlayerManager;
import me.seafoam.minecraft.seafoamy.player.PlayerWrapper;
import me.seafoam.minecraft.seafoamy.player.WrappedPlayer;
import org.bukkit.entity.Player;

public class PlayerManagerPaper extends PlayerManager<OnlinePlayerPaper, Player> {

	@Override
	public OnlinePlayerPaper getOnlinePlayer(Player player) {
		return getOnlinePlayer(player.getUniqueId());
	}

	@Override
	public OnlinePlayerPaper registerPlayer(Player player) {
		OnlinePlayerPaper onlinePlayerPaper = new OnlinePlayerPaper(player);
		addPlayer(onlinePlayerPaper);

		for (Class<? extends WrappedPlayer> clazz : PlayerWrapper.getWrappers()) {
			onlinePlayerPaper.wrap(clazz);
		}

		return onlinePlayerPaper;
	}

	@Override
	public OnlinePlayerPaper unregisterPlayer(Player player) {
		OnlinePlayerPaper onlinePlayerPaper = getOnlinePlayer(player);
		removePlayer(onlinePlayerPaper);
		return onlinePlayerPaper;
	}
}