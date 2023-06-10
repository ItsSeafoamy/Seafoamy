package me.seafoam.minecraft.seafoamy.paper.inventory.item;

import me.seafoam.minecraft.seafoamy.Seafoamy;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemInteractEvent implements Cancellable {

	private final OnlinePlayer player;
	private final ItemStack item;
	private InventoryClickEvent inventoryClickEvent;
	private PlayerInteractEvent playerInteractEvent;

	protected ItemInteractEvent(InventoryClickEvent event) {
		this.player = Seafoamy.getPlayerManager().getOnlinePlayer(event.getWhoClicked().getUniqueId());
		this.item = event.getCurrentItem();
		this.inventoryClickEvent = event;
	}

	protected ItemInteractEvent(PlayerInteractEvent event) {
		this.player = Seafoamy.getPlayerManager().getOnlinePlayer(event.getPlayer().getUniqueId());
		this.item = event.getItem();
		this.playerInteractEvent = event;
	}

	public OnlinePlayer getPlayer() {
		return this.player;
	}

	public ItemStack getItem() {
		return item;
	}

	public InventoryClickEvent getInventoryClickEvent() {
		return inventoryClickEvent;
	}

	public PlayerInteractEvent getPlayerInteractEvent() {
		return playerInteractEvent;
	}

	@Override
	public boolean isCancelled() {
		if (inventoryClickEvent != null) return inventoryClickEvent.isCancelled();
		return playerInteractEvent.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancelled) {
		if (inventoryClickEvent != null) inventoryClickEvent.setCancelled(cancelled);
		else playerInteractEvent.setCancelled(cancelled);
	}
}