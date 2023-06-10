package me.seafoam.minecraft.seafoamy.paper.inventory.item;

import java.util.Objects;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
record ItemIdentifier(Material type, Component name, OnlinePlayer player) {

	@Override
	public boolean equals(Object other) {
		if (other instanceof ItemIdentifier o) {
			if (player == null && o.player != null) return false;
			else if (o.player == null) return type == o.type && Objects.equals(o.name, name);
			else {
				return type == o.type && Objects.equals(o.name, name) && player.getUniqueId().equals(o.player.getUniqueId());
			}
		}

		return false;
	}
}