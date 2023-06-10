package me.seafoam.minecraft.seafoamy.player;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public interface OfflinePlayer {

	@NotNull UUID getUniqueId();
	@NotNull String getName();

	boolean isOnline();
	OnlinePlayer getOnlinePlayer();
}