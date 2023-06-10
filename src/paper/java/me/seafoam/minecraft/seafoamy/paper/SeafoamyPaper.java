package me.seafoam.minecraft.seafoamy.paper;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import me.seafoam.minecraft.seafoamy.Seafoamy;
import me.seafoam.minecraft.seafoamy.paper.command.CommandManagerPaper;
import me.seafoam.minecraft.seafoamy.paper.command.ConsolePaper;
import me.seafoam.minecraft.seafoamy.paper.inventory.item.ItemInteractListener;
import me.seafoam.minecraft.seafoamy.paper.inventory.item.ItemPacketListener;
import me.seafoam.minecraft.seafoamy.paper.player.PlayerManagerPaper;
import me.seafoam.minecraft.seafoamy.paper.event.PlayerConnectionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SeafoamyPaper extends JavaPlugin {

	@Getter private static SeafoamyPaper instance;

	@Override
	public void onEnable() {
		instance = this;

		// Load Languages
		Seafoamy.loadLanguages(this, "seafoamy");
		Seafoamy.setDefaultLanguage("en_GB");

		// Set Managers
		Seafoamy.setConsole(new ConsolePaper());
		Seafoamy.setPlayerManager(new PlayerManagerPaper());
		Seafoamy.setCommandManager(new CommandManagerPaper());

		// Register Events
		Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(), this);
		Bukkit.getPluginManager().registerEvents(new ItemInteractListener(), this);

		// Packet Magic
		ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
		protocolManager.addPacketListener(new ItemPacketListener(this));
	}
}
