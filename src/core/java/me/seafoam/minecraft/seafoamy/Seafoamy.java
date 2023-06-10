package me.seafoam.minecraft.seafoamy;

import lombok.Getter;
import lombok.Setter;
import me.seafoam.minecraft.seafoamy.command.CommandManager;
import me.seafoam.minecraft.seafoamy.command.Console;
import me.seafoam.minecraft.seafoamy.player.OnlinePlayer;
import me.seafoam.minecraft.seafoamy.player.PlayerManager;
import me.seafoam.minecraft.seafoamy.text.Language;

public class Seafoamy {

	@Getter @Setter private static PlayerManager<? extends OnlinePlayer, ?> playerManager;
	@Getter @Setter private static CommandManager commandManager;
	@Getter @Setter private static Console console;

	public static void loadLanguages(Object plugin, String namespace) {
		Language.loadAllLanguages(plugin, namespace);
	}

	public static void setDefaultLanguage(String language) {
		Language.setDefaultLanguage(Language.getLanguage(language));
	}
}