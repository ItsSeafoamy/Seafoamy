package me.seafoam.minecraft.seafoamy.paper.player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import me.seafoam.minecraft.seafoamy.Seafoamy;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import me.seafoam.minecraft.seafoamy.player.WrappedPlayer;
import me.seafoam.minecraft.seafoamy.text.Language;
import me.seafoam.minecraft.seafoamy.text.LiteralMessage;
import me.seafoam.minecraft.seafoamy.text.TextMessage;
import me.seafoam.minecraft.seafoamy.util.Location;
import me.seafoam.minecraft.seafoamy.paper.util.LocationUtils;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class OnlinePlayerPaper extends OfflinePlayerPaper implements OnlinePlayer {

	private final Player player;
	private TextMessage chatName;
	private Language language = Language.getDefaultLanguage();
	private final Map<String, Long> cooldowns = new HashMap<>();

	private final HashMap<Class<? extends WrappedPlayer>, Object> wrappedPlayers = new HashMap<>();

	public OnlinePlayerPaper(Player player) {
		super(player);

		this.player = player;

		chatName = new LiteralMessage(player.getName());
	}

	@Override
	public @NotNull Player getHandle() {
		return player;
	}

	@Override
	public @NotNull String getName() {
		return player.getName();
	}

	@Override
	public @NotNull TextMessage getChatName() {
		return chatName;
	}

	@Override
	public void setChatName(@NotNull TextMessage chatName) {
		this.chatName = chatName;
	}

	@Override
	public boolean hasPermission(String permission) {
		return player.hasPermission(permission);
	}

	@Override
	public @NotNull Language getLanguage() {
		return language;
	}

	@Override
	public void setLanguage(@NotNull Language language) {
		this.language = language;
	}

	@Override
	public void sendMessage() {
		player.sendMessage(Component.empty());
	}

	@Override
	public void sendMessage(TextMessage message) {
		if (message == null) player.sendMessage(Component.empty());
		else player.sendMessage(message.build(getLanguage()));
	}

	@Override
	public void sendMessage(TextMessage... messages) {
		for (TextMessage message : messages) {
			if (message == null) player.sendMessage(Component.empty());
			else player.sendMessage(message.build(getLanguage()));
		}
	}

	@Override
	public @NotNull UUID getUniqueId() {
		return player.getUniqueId();
	}

	@Override
	public void setTabHeaderFooter(TextMessage header, TextMessage footer) {
		player.sendPlayerListHeaderAndFooter(header.build(getLanguage()), footer.build(getLanguage()));
	}

	@Override
	public void sendTitle(TextMessage title) {
		player.showTitle(Title.title(title.build(getLanguage()), Component.text("")));
	}

	@Override
	public void sendSubtitle(TextMessage subtitle) {
		player.showTitle(Title.title(Component.text(""), subtitle.build(getLanguage())));
	}

	@Override
	public void sendTitleAndSubtitle(TextMessage title, TextMessage subtitle) {
		player.showTitle(Title.title(title.build(getLanguage()), subtitle.build(getLanguage())));
	}

	@Override
	public void clearTitles() {
		player.clearTitle();
	}

	@Override
	public void sendActionBar(TextMessage actionBar) {
		player.sendActionBar(actionBar.build(getLanguage()));
	}

	@Override
	public void playSound(Sound sound) {
		player.playSound(sound);
	}

	@Override
	public void playSound(Sound sound, Location location) {
		player.playSound(sound, location.getX(), location.getY(), location.getZ());
	}

	@Override
	public void playSound(Sound sound, Sound.Emitter emitter) {
		player.playSound(sound, emitter);
	}

	@Override
	public void teleport(@NotNull Location location) {
		World world;

		if (location.getWorld() == null) {
			world = player.getWorld();
		} else {
			world = Bukkit.getWorld(location.getWorld());
		}

		player.teleport(new org.bukkit.Location(world, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch()));
	}

	@Override
	public void teleport(@NotNull OnlinePlayer other) {
		player.teleport(((OnlinePlayerPaper) other).getHandle());
	}

	@Override
	public Location getLocation() {
		org.bukkit.Location bukkitLoc = player.getLocation();

		return new Location(player.getWorld().getName(), bukkitLoc.getX(), bukkitLoc.getY(), bukkitLoc.getZ(), bukkitLoc.getYaw(), bukkitLoc.getPitch());
	}

	@Override
	public CompletableFuture<Location> getLocationAsync() {
		CompletableFuture<Location> callback = new CompletableFuture<>();

		callback.complete(getLocation());

		return callback;
	}

	public PlayerInventory getInventory() {
		return player.getInventory();
	}

	public OnlinePlayerPaper getClosestPlayer() {
		OnlinePlayer result = null;
		double lastDistance = Double.MAX_VALUE;
		for (OnlinePlayer x : Seafoamy.getPlayerManager().getAllPlayers()) {
			Player p = (Player) x.getHandle();
			if (player == p) {
				continue;
			}

			if (player.getWorld() != this.getHandle().getWorld()) {
				continue;
			}

			double distance = player.getLocation().distanceSquared(p.getLocation());
			if (distance < lastDistance) {
				lastDistance = distance;
				result = x;
			}
		}

		return (OnlinePlayerPaper) result;
	}

	public void setCompassTarget(Location location) {
		player.setCompassTarget(LocationUtils.toBukkitLocation(location));
	}

	@Override
	public boolean triggerCooldown(String key, int cooldown) {
		if (!cooldowns.containsKey(key)) {
			cooldowns.put(key, System.currentTimeMillis() + cooldown);
			return true;
		} else {
			long time = cooldowns.get(key);

			if (time < System.currentTimeMillis()) {
				cooldowns.put(key, System.currentTimeMillis() + cooldown);
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public <T extends WrappedPlayer> T wrap(Class<T> clazz) {
		Object obj = wrappedPlayers.computeIfAbsent(clazz, c -> {
			try {
				WrappedPlayer wrapped = c.getConstructor().newInstance();
				wrapped.create(this);

				return wrapped;
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}

			return null;
		});

		if (clazz.isInstance(obj)) {
			return clazz.cast(obj);
		} else {
			throw new ClassCastException("Object is not of type " + clazz.getName());
		}
	}
}