package me.seafoam.minecraft.seafoamy.paper.command;

import me.seafoam.minecraft.seafoamy.Seafoamy;
import me.seafoam.minecraft.seafoamy.command.CommandExecutor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandPaper extends Command {

	private final me.seafoam.minecraft.seafoamy.command.Command command;

	protected CommandPaper(me.seafoam.minecraft.seafoamy.command.Command command) {
		super(command.getName(), command.getName(), "", Arrays.asList(command.getAliases()));
		setPermission(command.getPermission());
		this.command = command;
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
		CommandExecutor executor = null;

		if (sender instanceof Player player) executor = Seafoamy.getPlayerManager().getOnlinePlayer(player.getUniqueId());
		else if (sender instanceof ConsoleCommandSender) executor = Seafoamy.getConsole();

		if (executor != null) {
			command.treeExecute(executor, alias, args);
		}

		return true;
	}

	@Override
	public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) {
		CommandExecutor executor = null;

		if (sender instanceof Player player) executor = Seafoamy.getPlayerManager().getOnlinePlayer(player.getUniqueId());
		else if (sender instanceof ConsoleCommandSender) executor = Seafoamy.getConsole();

		if (executor != null) {
			return command.treeTab(executor, alias, args);
		}

		return new ArrayList<>();
	}

	@Override
	public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args, Location loc) {
		return tabComplete(sender, alias, args);
	}
}