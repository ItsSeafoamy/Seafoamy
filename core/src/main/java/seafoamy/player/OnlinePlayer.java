package me.seafoam.minecraft.seafoamy.player;

import java.util.concurrent.CompletableFuture;

import me.seafoam.minecraft.seafoamy.command.CommandExecutor;
import me.seafoam.minecraft.seafoamy.text.TextMessage;
import me.seafoam.minecraft.seafoamy.util.Location;
import org.jetbrains.annotations.NotNull;

public interface OnlinePlayer extends CommandExecutor, OfflinePlayer {

	@NotNull Object getHandle();
	void setChatName(@NotNull TextMessage chatName);

	void setTabHeaderFooter(TextMessage header, TextMessage footer);

	void teleport(@NotNull Location location);
	void teleport(@NotNull OnlinePlayer other);

	Location getLocation();
	CompletableFuture<Location> getLocationAsync();

	boolean triggerCooldown(String key, int cooldown);

	<T extends WrappedPlayer> T wrap(Class<T> clazz);
}