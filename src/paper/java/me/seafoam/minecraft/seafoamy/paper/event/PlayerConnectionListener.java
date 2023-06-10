package me.seafoam.minecraft.seafoamy.paper.event;

import me.seafoam.minecraft.seafoamy.Seafoamy;
import me.seafoam.minecraft.seafoamy.paper.player.PlayerManagerPaper;
import me.seafoam.minecraft.seafoamy.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		PlayerManager<?, ?> playerManager = Seafoamy.getPlayerManager();

		if (playerManager instanceof PlayerManagerPaper pmp) {
			pmp.registerPlayer(event.getPlayer());
		}
	}
}