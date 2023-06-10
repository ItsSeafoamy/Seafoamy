package me.seafoam.minecraft.seafoamy.player;

import org.jetbrains.annotations.NotNull;

public interface WrappedPlayer {

	void create(OnlinePlayer player);
	@NotNull OnlinePlayer getOnlinePlayer();
}