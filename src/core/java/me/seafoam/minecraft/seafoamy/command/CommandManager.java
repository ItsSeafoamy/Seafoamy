package me.seafoam.minecraft.seafoamy.command;

public interface CommandManager {

	void registerCommand(String namespace, Command command);
	void registerCommands(String namespace, Command... commands);
}