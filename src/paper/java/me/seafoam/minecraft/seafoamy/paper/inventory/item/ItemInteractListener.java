package me.seafoam.minecraft.seafoamy.paper.inventory.item;

import me.seafoam.minecraft.seafoamy.Seafoamy;
import me.seafoam.minecraft.seafoamy.paper.SeafoamyPaper;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemInteractListener implements Listener {

	private final Map<ItemIdentifier, ItemInteractHandler> handlers = new HashMap<>();
	protected static ItemInteractListener instance;
	protected final List<ItemIdentifier> unlimited = new ArrayList<>();

	public ItemInteractListener() {
		instance = this;
	}

	protected void addHandler(ItemIdentifier item, ItemInteractHandler handler) {
		handlers.put(item, handler);
	}

	@EventHandler
	public void onItemInteract(PlayerInteractEvent event) {
		if (event.getItem() != null && event.getHand() == EquipmentSlot.HAND) {
			OnlinePlayer op = Seafoamy.getPlayerManager().getOnlinePlayer(event.getPlayer().getUniqueId());

			Material type = event.getItem().getType();
			Component name = event.getItem().getItemMeta() != null ? event.getItem().getItemMeta().displayName() : null;

			ItemIdentifier item = new ItemIdentifier(type, name, op);
			ItemInteractHandler handler = handlers.get(item); //This doesn't want to work and I don't understand why

			for (ItemIdentifier o : handlers.keySet()) { //So this does what I thought the last line should be doing lmao
				if (item.equals(o)) handler = handlers.get(o);
			}

			if (handler != null) {
				event.setCancelled(true);

				OnlinePlayer player = Seafoamy.getPlayerManager().getOnlinePlayer(event.getPlayer().getUniqueId());

				if (player.triggerCooldown("item.interact", 500)) {
					handler.onInteract(new ItemInteractEvent(event));
				}
			} else if (unlimited.contains(item)) {
				ItemStack clone = event.getItem().clone();

				Bukkit.getScheduler().scheduleSyncDelayedTask(SeafoamyPaper.getInstance(), () -> {
					event.getPlayer().getInventory().setItem(event.getHand(), clone);
				}, 1);
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getCurrentItem() != null && event.getWhoClicked() instanceof Player player) {
			OnlinePlayer op = Seafoamy.getPlayerManager().getOnlinePlayer(player.getUniqueId());

			Material type = event.getCurrentItem().getType();
			Component name = event.getCurrentItem().getItemMeta() != null ? event.getCurrentItem().getItemMeta().displayName() : null;

			ItemIdentifier item = new ItemIdentifier(type, name, op);
			ItemInteractHandler handler = handlers.get(item); //This doesn't want to work and I don't understand why

			for (ItemIdentifier o : handlers.keySet()) { //So this does what I thought the last line should be doing lmao
				if (item.equals(o)) handler = handlers.get(o);
			}

			if (handler != null) {
				event.setCancelled(true);
				handler.onInteract(new ItemInteractEvent(event));
			}
		}
	}
}