package me.seafoam.minecraft.seafoamy.paper.inventory.item;

import com.comphenix.packetwrapper.WrapperPlayServerSetSlot;
import com.comphenix.packetwrapper.WrapperPlayServerWindowItems;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import java.util.ArrayList;
import java.util.List;
import me.seafoam.minecraft.seafoamy.Seafoamy;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import me.seafoam.minecraft.seafoamy.text.TextMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class ItemPacketListener extends PacketAdapter {

	public ItemPacketListener(Plugin plugin) {
		super(plugin, PacketType.Play.Server.SET_SLOT, PacketType.Play.Server.WINDOW_ITEMS);
	}

	@Override
	public void onPacketSending(final PacketEvent event) {
		OnlinePlayer player = Seafoamy.getPlayerManager().getOnlinePlayer(event.getPlayer().getUniqueId());

		if (event.getPacketType() == PacketType.Play.Server.SET_SLOT) {
			WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot(event.getPacket());

			ItemStack item = packet.getSlotData();
			if (item != null) {
				ItemMeta meta = item.getItemMeta();

				if (meta != null) {
					Component componentName = meta.displayName();

					if (componentName != null) {
						String name = PlainTextComponentSerializer.plainText().serialize(componentName);

						if (name.startsWith("§{i.")) {
							int id = Integer.parseInt(name.split("\\.")[1].replace("}", ""));
							TextMessage message = ItemDictionary.get(id);

							if (message != null) {
								meta.displayName(message.build(player.getLanguage()));
								item.setItemMeta(meta);
							}
						}
					}
				}

				meta = item.getItemMeta();
				if (meta != null) {
					if (meta.lore() != null) {
						List<Component> loreList = new ArrayList<>(meta.lore().size());

						for (int i = 0; i < meta.lore().size(); i++) {
							Component componentLore = meta.lore().get(i);

							if (componentLore != null) {
								String lore = PlainTextComponentSerializer.plainText().serialize(componentLore);

								if (lore.startsWith("§{i.")) {
									int id = Integer.parseInt(lore.split("\\.")[1].replace("}", ""));
									TextMessage message = ItemDictionary.get(id);

									if (message != null) {
										loreList.add(i, message.build(player.getLanguage()));
										continue;
									}
								}
							}

							loreList.add(i, componentLore);
						}

						meta.lore(loreList);
						item.setItemMeta(meta);
					}
				}

				packet.setSlotData(item);
			}
		} else if (event.getPacketType() == PacketType.Play.Server.WINDOW_ITEMS) {
			WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems(event.getPacket());

			List<ItemStack> data = new ArrayList<>(packet.getSlotData().size());

			for (ItemStack item : packet.getSlotData()) {
				if (item != null) {
					ItemMeta meta = item.getItemMeta();

					if (meta != null) {
						Component componentName = meta.displayName();

						if (componentName != null) {
							String name = PlainTextComponentSerializer.plainText().serialize(componentName);

							if (name.startsWith("§{i.")) {
								int id = Integer.parseInt(name.split("\\.")[1].replace("}", ""));
								TextMessage message = ItemDictionary.get(id);

								if (message != null) {
									meta.displayName(message.build(player.getLanguage()));
									item.setItemMeta(meta);
								}
							}
						}
					}

					meta = item.getItemMeta();
					if (meta != null) {
						if (meta.lore() != null) {
							List<Component> loreList = new ArrayList<>(meta.lore().size());

							for (int i = 0; i < meta.lore().size(); i++) {
								Component componentLore = meta.lore().get(i);

								if (componentLore != null) {
									String lore = PlainTextComponentSerializer.plainText().serialize(componentLore);

									if (lore.startsWith("§{i.")) {
										int id = Integer.parseInt(lore.split("\\.")[1].replace("}", ""));
										TextMessage message = ItemDictionary.get(id);

										if (message != null) {
											loreList.add(i, message.build(player.getLanguage()));
											continue;
										}
									}
								}

								loreList.add(i, componentLore);
							}

							meta.lore(loreList);
							item.setItemMeta(meta);
						}
					}

					data.add(item);
				}
			}

			packet.setSlotData(data);
		}
	}
}
