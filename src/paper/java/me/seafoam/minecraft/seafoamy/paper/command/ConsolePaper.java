package me.seafoam.minecraft.seafoamy.paper.command;

import me.seafoam.minecraft.seafoamy.command.Console;
import me.seafoam.minecraft.seafoamy.text.Language;
import me.seafoam.minecraft.seafoamy.text.LiteralMessage;
import me.seafoam.minecraft.seafoamy.text.TextMessage;
import me.seafoam.minecraft.seafoamy.util.Location;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class ConsolePaper implements Console {

	private Language language = Language.getDefaultLanguage();

	@Override
	public @NotNull String getName() {
		return "Console";
	}

	@Override
	public @NotNull TextMessage getChatName() {
		return new LiteralMessage(getName());
	}

	@Override
	public @NotNull Language getLanguage() {
		return language;
	}

	@Override
	public void setLanguage(@NotNull Language language) {
		this.language = language;
	}

	@Override
	public boolean hasPermission(String permission) {
		return true;
	}

	@Override
	public void sendMessage() {
		Bukkit.getConsoleSender().sendMessage(Component.text());
	}

	@Override
	public void sendMessage(TextMessage textMessage) {
		Bukkit.getConsoleSender().sendMessage(textMessage.build(getLanguage()));
	}

	@Override
	public void sendMessage(TextMessage... messages) {
		for (TextMessage message : messages) {
			sendMessage(message);
		}
	}

	@Override
	public void sendTitle(TextMessage title) {}

	@Override
	public void sendSubtitle(TextMessage subtitle) {}

	@Override
	public void sendTitleAndSubtitle(TextMessage title, TextMessage subtitle) {}

	@Override
	public void clearTitles() {}

	@Override
	public void sendActionBar(TextMessage actionBar) {}

	@Override
	public void playSound(Sound sound) {}

	@Override
	public void playSound(Sound sound, Location location) {}

	@Override
	public void playSound(Sound sound, Sound.Emitter emitter) {}
}