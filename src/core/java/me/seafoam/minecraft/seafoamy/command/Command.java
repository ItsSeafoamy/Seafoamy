package me.seafoam.minecraft.seafoamy.command;

import lombok.Getter;
import me.seafoam.minecraft.seafoamy.text.TranslatedMessage;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command {

	@Getter private final String name;
	@Getter private final String permission;
	@Getter private final String[] aliases;
	@Getter private final List<Command> subCommands = new ArrayList<>();

	public Command(String name, String permission, String... aliases) {
		this.name = name;
		this.permission = permission;
		this.aliases = aliases;
	}

	public void registerSubCommand(@NotNull Command subCommand) {
		subCommands.add(subCommand);
	}

	public void treeExecute(CommandExecutor sender, String label, String[] args) {
		if (args.length != 0) {
			String subCheck = args[0];

			for (Command sub : subCommands) {
				boolean isSub = sub.getName().equalsIgnoreCase(subCheck);

				if (!isSub) {
					for (String alias : aliases) {
						if (alias.equalsIgnoreCase(subCheck)) {
							isSub = true;
							break;
						}
					}
				}

				if (isSub) {
					String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

					sub.treeExecute(sender, label + " " + args[0], newArgs);
					return;
				}
			}
		}

		if (sender.hasPermission(getPermission())) {
			execute(sender, label, args);
		} else {
			sender.sendMessage(new TranslatedMessage("seafoamy:command.error.no_permission").colour(NamedTextColor.RED));
		}
	}

	public List<String> treeTab(CommandExecutor sender, String label, String[] args) {
		if (args.length != 0) {
			String subCheck = args[0];

			for (Command sub : subCommands) {
				boolean isSub = sub.getName().equalsIgnoreCase(subCheck);

				if (!isSub) {
					for (String alias : aliases) {
						if (alias.equalsIgnoreCase(subCheck)) {
							isSub = true;
							break;
						}
					}
				}

				if (isSub) {
					String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

					return sub.treeTab(sender, label + " " + args[0], newArgs);
				}
			}
		}

		if (sender.hasPermission(getPermission())) {
			List<String> results = onTabComplete(sender, label, args);

			for (Command sub : subCommands) {
				if (sender.hasPermission(sub.getPermission()) && sub.getName().toLowerCase().startsWith(args[0].toLowerCase())) results.add(sub.getName());
			}

			return results;
		} else {
			return new ArrayList<>();
		}
	}

	public abstract void execute(@NotNull CommandExecutor sender, @NotNull String label, @NotNull String[] args);
	public abstract @NotNull List<String> onTabComplete(@NotNull CommandExecutor sender, @NotNull String label, @NotNull String[] args);
}