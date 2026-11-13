package me.seafoam.minecraft.seafoamy.paper.command;

import me.seafoam.minecraft.seafoamy.command.Command;
import me.seafoam.minecraft.seafoamy.command.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandManagerPaper implements CommandManager {

	private SimpleCommandMap commandMap;

	public CommandManagerPaper() {
		SimplePluginManager pm = (SimplePluginManager) Bukkit.getPluginManager();
		Field f;

		try {
			f = SimplePluginManager.class.getDeclaredField("commandMap");
			f.setAccessible(true);
			this.commandMap = (SimpleCommandMap) f.get(pm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void registerCommand(String namespace, Command command) {
		commandMap.register(namespace, new CommandPaper(command));
	}

	@Override
	public void registerCommands(String namespace, Command... commands) {
		Arrays.stream(commands).forEach(command -> registerCommand(namespace, command));
	}
}